package com.huan1645.TWDevJob.controller;

import com.huan1645.TWDevJob.entity.RecruiterProfile;
import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.exception.profilePicUploadException;
import com.huan1645.TWDevJob.repository.UserRepoInterface;
import com.huan1645.TWDevJob.service.RecruiterProfileService;
import com.huan1645.TWDevJob.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UserRepoInterface userRepo;
    private final RecruiterProfileService recruiterProfileService;
    private final S3Service s3Service;

    @Autowired
    public RecruiterProfileController(UserRepoInterface userRepo, RecruiterProfileService recruiterProfileService, S3Service s3Service) {
        this.userRepo = userRepo;
        this.recruiterProfileService = recruiterProfileService;
        this.s3Service = s3Service;
    }


    @GetMapping("/")
    public String profileEditpage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            User user = userRepo.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("Cannot find the user"));
            Optional<RecruiterProfile> profile = recruiterProfileService.getProfileById(user.getUser_id());
            if(profile.isPresent()){
                model.addAttribute("profile", profile.get());
            }
        }
        return "recruiter_profile";
    }
    @PostMapping("/addNew")
    public String addProfile(RecruiterProfile profile, @RequestParam("image")MultipartFile file, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            String username = auth.getName();
            User user = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Cannot find the user"));
            profile.setUserId(user);
            profile.setUser_account_id(user.getUser_id());
        }
        try {
            String fileName = file.getOriginalFilename();
            if(!(file.getOriginalFilename().isEmpty())){
                fileName = UUID.randomUUID() + "_";
                // Specify the folder name (e.g., "recruiter-profiles")
                String folderName = "photos-recruiter";

                // Upload the file to S3
                String fileUrl = s3Service.uploadProfilePicture(folderName, fileName, file.getBytes());

                // Save the file URL in the profile
                profile.setProfile_photo(fileUrl);
            }
        } catch (IOException e) {
            throw new profilePicUploadException("Error when uploading the profile page");
        }
        model.addAttribute("profile", profile);
        RecruiterProfile savedUser = recruiterProfileService.addNew(profile);
        return "redirect:/dashboard/";
    }
}
