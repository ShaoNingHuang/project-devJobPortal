package com.huan1645.TWDevJob.controller;

import com.huan1645.TWDevJob.entity.JobPostActivity;
import com.huan1645.TWDevJob.entity.RecruiterJobsDto;
import com.huan1645.TWDevJob.entity.RecruiterProfile;
import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.service.JobPostActivityService;
import com.huan1645.TWDevJob.service.S3Service;
import com.huan1645.TWDevJob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;
import java.util.Date;
import java.util.List;


@Controller
public class JobPostActivityController {

    private final UserService userService;
    private final S3Service s3Service;
    private final JobPostActivityService jobService;

    @Value("${aws.s3.bucketName}")
    private String bucketname;

    @Autowired
    public JobPostActivityController(UserService userService, S3Service s3Service, JobPostActivityService jobService) {
        this.userService = userService;
        this.s3Service = s3Service;
        this.jobService = jobService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model){
        Object currentUserProfile = userService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            model.addAttribute("currentUserName", currentUsername);
        }
        if(currentUserProfile instanceof RecruiterProfile){
            if(((RecruiterProfile) currentUserProfile).getProfile_photo() != null) {
                String presignedUrl = s3Service.generatePresignedUrl(
                        bucketname,
                        ((RecruiterProfile) currentUserProfile).getProfile_photo(),
                        Duration.ofDays(7)
                );
                model.addAttribute("profile_photo", presignedUrl);
                List<RecruiterJobsDto> recruiterJobs = jobService.getRecruiterJobs(((RecruiterProfile) currentUserProfile).getUser_account_id());
                model.addAttribute("jobPost", recruiterJobs);
            }
        }
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model){
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String AddNewJob(@ModelAttribute("jobPostActivity") JobPostActivity job, Model model){
        User user = userService.getCurrentUser();
        if(user != null){
            job.setPosted_by_id(user);
        }
        job.setPosted_date(new Date());
        jobService.addNew(job);
        model.addAttribute("jobPostActivity", job);
        return "redirect:/dashboard/";
    }

    @GetMapping("dashboard/edit/{id}")
    public String editJob(@PathVariable("id") int id, Model model){
        JobPostActivity job = jobService.getOne(id);
        model.addAttribute("jobPostActivity", job);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-jobs";
    }

}
