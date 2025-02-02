package com.huan1645.TWDevJob.controller;

import com.huan1645.TWDevJob.entity.*;
import com.huan1645.TWDevJob.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class JobSeekerApplyController {
    private final JobPostActivityService jobPostActivityService;
    private final UserService userService;
    private final S3Service s3Service;
    private final JobSeekerApplyService jobSeekerApplyService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final JobSeekerProfileService jobSeekerProfileService;


    @Value("${aws.s3.bucketName}")
    private String bucketname;


    @Autowired
    public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UserService userService, S3Service s3Service, JobSeekerApplyService jobSeekerApplyService, JobSeekerSaveService jobSeekerSaveService, JobSeekerProfileService jobSeekerProfileService) {
        this.jobPostActivityService = jobPostActivityService;
        this.userService = userService;
        this.s3Service = s3Service;
        this.jobSeekerApplyService = jobSeekerApplyService;
        this.jobSeekerSaveService = jobSeekerSaveService;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    @GetMapping("job-details-apply/{id}")
    public String disPlayJobDetail(@PathVariable("id") int id, Model model){
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        model.addAttribute("user", userService.getCurrentUserProfile());
        List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);


        model.addAttribute("jobDetails", jobDetails);

        Object currentUserProfile = userService.getCurrentUserProfile();
        if(currentUserProfile instanceof RecruiterProfile){
            String profilePhoto = ((RecruiterProfile) currentUserProfile).getProfile_photo();
            String photo_url = s3Service.generatePresignedUrl(
                    bucketname,
                    ((RecruiterProfile) currentUserProfile).getProfile_photo(),
                    Duration.ofDays(7)
            );
            model.addAttribute("photo_url", photo_url);
            model.addAttribute("applyList", jobSeekerApplyList);
        } else if (currentUserProfile instanceof JobSeekerProfile){
            String profilePhoto = ((JobSeekerProfile) currentUserProfile).getProfile_photo();
            String photo_url = s3Service.generatePresignedUrl(
                    bucketname,
                    ((JobSeekerProfile) currentUserProfile).getProfile_photo(),
                    Duration.ofDays(7)
            );
            model.addAttribute("photo_url", photo_url);
            boolean exist=false;
            boolean saved=false;
            for(JobSeekerApply jobSeekerApply : jobSeekerApplyList) {
                if(jobSeekerApply.getUser_id().getUser_account_id() == ((JobSeekerProfile) currentUserProfile).getUser_account_id()){
                    exist = true;
                    break;
                }
            }
            for (JobSeekerSave jobSeekerSave: jobSeekerSaveList){
                if(jobSeekerSave.getUser_id().getUser_account_id() == ((JobSeekerProfile) currentUserProfile).getUser_account_id()){
                    saved = true;
                    break;
                }
            }
            model.addAttribute("alreadyApplied", exist);
            model.addAttribute("alreadySaved", saved);
        }
        JobSeekerApply jobSeekerApply = new JobSeekerApply();
        model.addAttribute("applyJob", jobSeekerApply);
        return "job-details";
    }

    @PostMapping("job-details/apply/{id}")
    public String apply(@PathVariable("id") int id, JobSeekerApply jobSeekerApply) {
        Object currentUserProfile = userService.getCurrentUserProfile();
        Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getById(((JobSeekerProfile)currentUserProfile).getUser_account_id());
        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
        if(jobSeekerProfile.isPresent() && jobPostActivity != null){
            jobSeekerApply = new JobSeekerApply();
            jobSeekerApply.setUser_id(((JobSeekerProfile) currentUserProfile));
            jobSeekerApply.setJob(jobPostActivity);
            jobSeekerApply.setApply_date(new Date());
        } else {
            throw new UsernameNotFoundException("Cannot find User");
        }

        jobSeekerApplyService.addNew(jobSeekerApply);
        return "redirect:/dashboard/";
    }
}
