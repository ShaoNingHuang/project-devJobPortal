package com.huan1645.TWDevJob.controller;

import com.huan1645.TWDevJob.entity.*;
import com.huan1645.TWDevJob.service.*;
import jakarta.validation.constraints.AssertFalse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Controller
public class JobPostActivityController {

    private final UserService userService;
    private final S3Service s3Service;
    private final JobPostActivityService jobService;
    private final JobSeekerApplyService jobSeekerApplyService;
    private final JobSeekerSaveService jobSeekerSaveService;

    @Value("${aws.s3.bucketName}")
    private String bucketname;

    @Autowired
    public JobPostActivityController(UserService userService, S3Service s3Service, JobPostActivityService jobService, JobSeekerApplyService jobSeekerApplyService, JobSeekerSaveService jobSeekerSaveService) {
        this.userService = userService;
        this.s3Service = s3Service;
        this.jobService = jobService;
        this.jobSeekerApplyService = jobSeekerApplyService;
        this.jobSeekerSaveService = jobSeekerSaveService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model, @RequestParam(value="job", required = false) String job,
                             @RequestParam(value="location", required = false) String location,
                             @RequestParam(value="partTime", required = false) String partTime,
                             @RequestParam(value="fullTime", required = false) String fullTime,
                             @RequestParam(value="freelance", required = false) String freelance,
                             @RequestParam(value="remoteOnly", required = false) String remoteOnly,
                             @RequestParam(value="officeOnly", required = false) String officeOnly,
                             @RequestParam(value="partialRemote", required = false) String partialRemote,
                             @RequestParam(value="today", required = false) boolean today,
                             @RequestParam(value="days7", required = false) boolean days7,
                             @RequestParam(value="days30", required = false) boolean days30
                            ){
        model.addAttribute("partTime", Objects.equals(partTime, "Part-Time"));
        model.addAttribute("fullTime", Objects.equals(fullTime, "Full-Time"));
        model.addAttribute("freelance", Objects.equals(freelance, "Freelance"));

        model.addAttribute("remoteOnly", Objects.equals(remoteOnly, "Remote-Only"));
        model.addAttribute("officeOnly", Objects.equals(officeOnly, "Office-Only"));
        model.addAttribute("partialRemote", Objects.equals(partialRemote, "Partial-Remote"));

        model.addAttribute("today", today);
        model.addAttribute("days7", days7);
        model.addAttribute("days30", days30);

        model.addAttribute("job", job);
        model.addAttribute("location", location);

        LocalDate searchDate = null;
        List<JobPostActivity> jobPost = null;
        boolean dateSearchFlag = true;
        boolean remote = true;
        boolean type = true;

        if (days30){
            searchDate = LocalDate.now().minusDays(30);
        } else if (days7) {
            searchDate = LocalDate.now().minusDays(7);
        } else if(today) {
            searchDate = LocalDate.now();
        } else {
            dateSearchFlag = false;
        }
        if(partTime == null && fullTime == null && freelance == null ){
            partTime = "Part-Time";
            fullTime = "Full-Time";
            freelance = "Freelance";
            remote = false;
        }
        if (officeOnly == null && remoteOnly == null && partialRemote == null){
            officeOnly = "Office-Only";
            remoteOnly = "Remote-Only";
            partialRemote = "Partial-Remote";
            type = false;
        }

        if(!dateSearchFlag && !remote && !type && !StringUtils.hasText(job) && !StringUtils.hasText(location))
        {
            jobPost = jobService.getAll();
        } else {
            jobPost = jobService.search(job, location, Arrays.asList(partTime,
                    fullTime, freelance), Arrays.asList(remoteOnly, officeOnly, partialRemote), searchDate);

        }

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
        } else if (currentUserProfile instanceof JobSeekerProfile){
            if(((JobSeekerProfile) currentUserProfile).getProfile_photo() != null) {
                String presignedUrl = s3Service.generatePresignedUrl(
                        bucketname,
                        ((JobSeekerProfile) currentUserProfile).getProfile_photo(),
                        Duration.ofDays(7)
                );
                model.addAttribute("profile_photo", presignedUrl);
                List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getCandidateJobs((JobSeekerProfile) currentUserProfile);
                List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidateJobs((JobSeekerProfile)currentUserProfile);
                boolean exist;
                boolean saved;

                for(JobPostActivity jobActivity : jobPost) {
                    exist = false;
                    saved = false;
                    for (JobSeekerApply jobSeekerApply: jobSeekerApplyList){
                        if(Objects.equals(jobActivity.getJob_post_id(), jobSeekerApply.getJob().getJob_post_id())){
                            jobActivity.setIsActive(true);
                            exist=true;
                            break;
                        }
                    }
                    for(JobSeekerSave jobSeekerSave: jobSeekerSaveList){
                        if(Objects.equals(jobActivity.getJob_post_id(), jobSeekerSave.getJob().getJob_post_id())){
                            jobActivity.setIsSaved(true);
                            saved=true;
                            break;
                        }
                    }
                    if(!exist){
                        jobActivity.setIsActive(false);
                    }
                    if(!saved){
                        jobActivity.setIsSaved(false);
                    }
                    model.addAttribute("jobPost", jobPost);
                }
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

    @GetMapping("global-search/")
    public String globalSearch(Model model, @RequestParam(value="job", required = false) String job,
                               @RequestParam(value="location", required = false) String location,
                               @RequestParam(value="partTime", required = false) String partTime,
                               @RequestParam(value="fullTime", required = false) String fullTime,
                               @RequestParam(value="freelance", required = false) String freelance,
                               @RequestParam(value="remoteOnly", required = false) String remoteOnly,
                               @RequestParam(value="officeOnly", required = false) String officeOnly,
                               @RequestParam(value="partialRemote", required = false) String partialRemote,
                               @RequestParam(value="today", required = false) boolean today,
                               @RequestParam(value="days7", required = false) boolean days7,
                               @RequestParam(value="days30", required = false) boolean days30){
        model.addAttribute("partTime", Objects.equals(partTime, "Part-Time"));
        model.addAttribute("fullTime", Objects.equals(fullTime, "Full-Time"));
        model.addAttribute("freelance", Objects.equals(freelance, "Freelance"));

        model.addAttribute("remoteOnly", Objects.equals(remoteOnly, "Remote-Only"));
        model.addAttribute("officeOnly", Objects.equals(officeOnly, "Office-Only"));
        model.addAttribute("partialRemote", Objects.equals(partialRemote, "Partial-Remote"));

        model.addAttribute("today", today);
        model.addAttribute("days7", days7);
        model.addAttribute("days30", days30);

        model.addAttribute("job", job);
        model.addAttribute("location", location);

        LocalDate searchDate = null;
        List<JobPostActivity> jobPost = null;
        boolean dateSearchFlag = true;
        boolean remote = true;
        boolean type = true;

        if (days30){
            searchDate = LocalDate.now().minusDays(30);
        } else if (days7) {
            searchDate = LocalDate.now().minusDays(7);
        } else if(today) {
            searchDate = LocalDate.now();
        } else {
            dateSearchFlag = false;
        }
        if(partTime == null && fullTime == null && freelance == null ){
            partTime = "Part-Time";
            fullTime = "Full-Time";
            freelance = "Freelance";
            remote = false;
        }
        if (officeOnly == null && remoteOnly == null && partialRemote == null){
            officeOnly = "Office-Only";
            remoteOnly = "Remote-Only";
            partialRemote = "Partial-Remote";
            type = false;
        }

        if(!dateSearchFlag && !remote && !type && !StringUtils.hasText(job) && !StringUtils.hasText(location))
        {
            jobPost = jobService.getAll();
        } else {
            jobPost = jobService.search(job, location, Arrays.asList(partTime,
                    fullTime, freelance), Arrays.asList(remoteOnly, officeOnly, partialRemote), searchDate);

        }
        model.addAttribute("jobPost", jobPost);
        return "global-search";
    }


}
