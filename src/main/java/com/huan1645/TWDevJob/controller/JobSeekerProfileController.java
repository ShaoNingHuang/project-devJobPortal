package com.huan1645.TWDevJob.controller;

import com.huan1645.TWDevJob.entity.JobSeekerProfile;
import com.huan1645.TWDevJob.entity.Skills;
import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.exception.profilePicUploadException;
import com.huan1645.TWDevJob.repository.UserRepoInterface;
import com.huan1645.TWDevJob.service.JobSeekerProfileService;
import com.huan1645.TWDevJob.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {
    private final JobSeekerProfileService jobSeekerProfileService;
    private final UserRepoInterface userRepo;
    private final S3Service s3Service;

    @Value("${aws.s3.bucketName}")
    private String bucketname;
    @Autowired
    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService, UserRepoInterface userRepo, S3Service s3Service) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.userRepo = userRepo;
        this.s3Service = s3Service;
    }

    @GetMapping("/")
    public String JobSeekerProfile(Model model){
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            User user = userRepo.findByEmail(auth.getName()).orElseThrow(()-> new UsernameNotFoundException("Cannot find user"));
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getById(user.getUser_id());

            if(seekerProfile.isPresent()){
                jobSeekerProfile = seekerProfile.get();
                if(jobSeekerProfile.getSkills().isEmpty()){
                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }
            }
            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }
        return "job-seeker-profile";
    }

    @PostMapping("/addNew")
    public String addNewCandidate(Model model,
                                  @ModelAttribute("profile") JobSeekerProfile profile,
                                  @RequestParam("image")
                                  MultipartFile image,
                                  @RequestParam("pdf") MultipartFile resume) {
        //will need to upload the resume and the profile pic to the S3 bucket
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String folderName = "photos-candidate";
        String resumeFolderName = "resume-candidate";
        if(!(auth instanceof AnonymousAuthenticationToken)){
            String username = auth.getName();
            User user = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Cannot find the user"));
            profile.setUserId(user);
            profile.setUser_account_id(user.getUser_id());
        }
        try {
            String image_name = image.getOriginalFilename();
            String resume_name = resume.getOriginalFilename();
            if(!(image.getOriginalFilename().isEmpty())){
                image_name = UUID.randomUUID() + "_";
                String fileUrl = s3Service.uploadProfilePicture(folderName, image_name, image.getBytes());
                profile.setProfile_photo(fileUrl);
            }

            if(!(resume.getOriginalFilename().isEmpty())){
                resume_name = UUID.randomUUID() + ".pdf";
                String fileUrl = s3Service.uploadProfilePicture(folderName, image_name, image.getBytes());
                String resumeUrl = s3Service.uploadProfilePicture(resumeFolderName, resume_name, resume.getBytes());

                profile.setResume(resumeUrl);
            }
        } catch (Exception exec) {
            throw new profilePicUploadException("Error When uploading file");
        }
        List<Skills> skillsList = new ArrayList<>();
        model.addAttribute("profile", profile);
        model.addAttribute("skills", skillsList);
        for (Skills skills : profile.getSkills()){
            skills.setJobSeekerProfile(profile);
        }
        JobSeekerProfile savedProfile = jobSeekerProfileService.addNew(profile);
        return "redirect:/dashboard/";
    }


    @GetMapping("/{id}")
    public String candidateProfile(@PathVariable("id") int id, Model model){
        Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getById(id);
        if(seekerProfile.get().getResume() != null){
            String presignedUrl = s3Service.generatePresignedUrl(
                    bucketname,
                    seekerProfile.get().getResume(),
                    Duration.ofDays(7)
            );
            model.addAttribute("resume_url", presignedUrl);
        }
        model.addAttribute("profile", seekerProfile.get());
        return "job-seeker-profile";
    }
}



