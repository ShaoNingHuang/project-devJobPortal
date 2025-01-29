package com.huan1645.TWDevJob.service;

import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.repository.UserRepoInterface;
import com.huan1645.TWDevJob.util.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepoInterface userRepo;

    @Autowired
    public CustomUserDetailService(UserRepoInterface userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could Not Found User"));
        return new CustomUserDetail(user);
    }
}
