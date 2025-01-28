package com.huan1645.TWDevJob.service;

import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.exception.UserExistedException;
import com.huan1645.TWDevJob.repository.UserRepoInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class UserService {
    private final UserRepoInterface repo;

    @Autowired
    public UserService(UserRepoInterface repo) {
        this.repo = repo;
    }


    public User registerUser(User theUser){
        if(repo.findByEmail(theUser.getEmail()).isPresent()){
            throw new UserExistedException("User with the same email already existed");
        }
        theUser.setIs_active(true);
        theUser.setRegistration_date(new Date(System.currentTimeMillis()));
        return repo.save(theUser);
    }
}
