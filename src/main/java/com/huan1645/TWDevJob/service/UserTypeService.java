package com.huan1645.TWDevJob.service;

import com.huan1645.TWDevJob.entity.UserType;
import com.huan1645.TWDevJob.repository.UserTypeRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {
    private final UserTypeRepoInterface userTypeRepo;

    @Autowired
    public UserTypeService(UserTypeRepoInterface userTypeRepo) {
        this.userTypeRepo = userTypeRepo;
    }


    public List<UserType> getAll(){
        List<UserType> res = userTypeRepo.findAll();
        return userTypeRepo.findAll();
    }
}
