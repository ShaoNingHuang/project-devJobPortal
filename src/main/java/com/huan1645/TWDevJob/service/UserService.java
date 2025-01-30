package com.huan1645.TWDevJob.service;

import com.huan1645.TWDevJob.entity.JobSeekerProfile;
import com.huan1645.TWDevJob.entity.RecruiterProfile;
import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.exception.EmptyPasswordException;
import com.huan1645.TWDevJob.exception.UserExistedException;
import com.huan1645.TWDevJob.repository.JobSeekerProfileRepoInterface;
import com.huan1645.TWDevJob.repository.RecruiterProfileRepoInterface;
import com.huan1645.TWDevJob.repository.UserRepoInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class UserService {
    private final UserRepoInterface userRepo;
    private final RecruiterProfileRepoInterface recruiterRepo;
    private final JobSeekerProfileRepoInterface jobseekerRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepoInterface userrepo, RecruiterProfileRepoInterface recruiterrepo, JobSeekerProfileRepoInterface jobseekerrepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userrepo;
        this.recruiterRepo = recruiterrepo;
        this.jobseekerRepo = jobseekerrepo;
        this.passwordEncoder = passwordEncoder;
    }


    public User registerUser(User theUser){
        if(userRepo.findByEmail(theUser.getEmail()).isPresent()){
            throw new UserExistedException("User with the same email already existed");
        }
        if(theUser.getPassword() == null || theUser.getPassword().trim().isEmpty()){
            throw new EmptyPasswordException("Invalid Password. Please enter a valid password");
        }
        theUser.setIs_active(true);
        theUser.setRegistration_date(new Date(System.currentTimeMillis()));
        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
        User savedUser = userRepo.save(theUser);
        int type_id = savedUser.getUser_type_id().getUser_type_id();
        if (type_id == 1){
            recruiterRepo.save(new RecruiterProfile(savedUser));
        } else {
            jobseekerRepo.save(new JobSeekerProfile(savedUser));
        }
        return savedUser;
    }



    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User does not found"));
            int userId = user.getUser_id();
             if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                 RecruiterProfile recruiterProfile = recruiterRepo.findById(userId).orElse(new RecruiterProfile());
                 return recruiterProfile;
             } else {
                 JobSeekerProfile jobSeekerProfile = jobseekerRepo.findById(userId).orElse(new JobSeekerProfile());
                 return jobSeekerProfile;
             }
        }
        return null;
    }
}
