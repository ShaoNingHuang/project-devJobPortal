package com.huan1645.TWDevJob.controller;

import com.huan1645.TWDevJob.entity.JobPostActivity;
import com.huan1645.TWDevJob.entity.RecruiterProfile;
import com.huan1645.TWDevJob.service.JobPostActivityService;
import com.huan1645.TWDevJob.service.S3Service;
import com.huan1645.TWDevJob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Duration;

@Controller
public class JobSeekerApplyController {
    private final JobPostActivityService jobPostActivityService;
    private final UserService userService;
    private final S3Service s3Service;

    @Value("${aws.s3.bucketName}")
    private String bucketname;


    @Autowired
    public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UserService userService, S3Service s3Service) {
        this.jobPostActivityService = jobPostActivityService;
        this.userService = userService;
        this.s3Service = s3Service;
    }

    @GetMapping("job-details-apply/{id}")
    public String disPlayJobDetail(@PathVariable("id") int id, Model model){
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        model.addAttribute("user", userService.getCurrentUserProfile());
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
        }
        return "job-details";
    }
}
