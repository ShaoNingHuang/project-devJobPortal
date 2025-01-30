package com.huan1645.TWDevJob.controller;

import com.huan1645.TWDevJob.entity.RecruiterProfile;
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

import java.time.Duration;


@Controller
public class JobPostActivityController {

    private final UserService userService;
    private final S3Service s3Service;

    @Value("${aws.s3.bucketName}")
    private String bucketname;

    @Autowired
    public JobPostActivityController(UserService userService, S3Service s3Service) {
        this.userService = userService;
        this.s3Service = s3Service;
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
                ((RecruiterProfile) currentUserProfile).setProfile_photo(presignedUrl);
            }
        }
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }
}
