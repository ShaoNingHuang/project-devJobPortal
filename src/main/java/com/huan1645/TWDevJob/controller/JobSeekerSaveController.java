package com.huan1645.TWDevJob.controller;

import com.huan1645.TWDevJob.entity.*;
import com.huan1645.TWDevJob.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerSaveController {
    private final UserService userService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final S3Service s3Service;
    @Value("${aws.s3.bucketName}")
    private String bucketname;

    @Autowired
    public JobSeekerSaveController(UserService userService, JobSeekerProfileService jobSeekerProfileService, JobPostActivityService jobPostActivityService, JobSeekerSaveService jobSeekerSaveService, S3Service s3Service) {
        this.userService = userService;
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerSaveService = jobSeekerSaveService;
        this.s3Service = s3Service;
    }

    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id") int id, JobSeekerSave jobSeekerSave){
        Object currentUserProfile = userService.getCurrentUserProfile();
        Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getById(((JobSeekerProfile)currentUserProfile).getUser_account_id());
        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
        if(jobSeekerProfile.isPresent() && jobPostActivity != null){
            jobSeekerSave = new JobSeekerSave();
            jobSeekerSave.setUser_id(((JobSeekerProfile) currentUserProfile));
            jobSeekerSave.setJob(jobPostActivity);
        } else {
            throw new UsernameNotFoundException("Cannot find User");
        }

        jobSeekerSaveService.addNew(jobSeekerSave);
        return "redirect:/dashboard/";
    }

    @GetMapping("saved-jobs/")
    public String listAllSaved(Model model){
        List<JobPostActivity> listofJobs = new ArrayList<>();
        Object currentUserProfile = userService.getCurrentUserProfile();
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidateJobs((JobSeekerProfile)currentUserProfile);
        for(JobSeekerSave jobSeekerSave: jobSeekerSaveList){
            listofJobs.add(jobSeekerSave.getJob());
        }
        if(((JobSeekerProfile) currentUserProfile).getProfile_photo() != null){
            String presignedUrl = s3Service.generatePresignedUrl(
                    bucketname,
                    ((JobSeekerProfile) currentUserProfile).getProfile_photo(),
                    Duration.ofDays(7)
            );
            model.addAttribute("photo_url", presignedUrl);
        }

        model.addAttribute("jobPost", listofJobs);
        model.addAttribute("user", currentUserProfile);
        return "saved-jobs";
    }
}
