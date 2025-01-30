package com.huan1645.TWDevJob.service;

import com.huan1645.TWDevJob.entity.RecruiterProfile;
import com.huan1645.TWDevJob.repository.RecruiterProfileRepoInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class RecruiterProfileService {
    private final RecruiterProfileRepoInterface recruiterRepo;

    public RecruiterProfileService(RecruiterProfileRepoInterface recruiterRepo) {
        this.recruiterRepo = recruiterRepo;
    }

    public Optional<RecruiterProfile> getProfileById(int id){
        return recruiterRepo.findById(id);
    }

    public RecruiterProfile addNew(RecruiterProfile profile) {
        return recruiterRepo.save(profile);
    }
}
