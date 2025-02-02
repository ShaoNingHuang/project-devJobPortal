package com.huan1645.TWDevJob.service;

import com.huan1645.TWDevJob.entity.JobSeekerProfile;
import com.huan1645.TWDevJob.repository.JobSeekerProfileRepoInterface;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class JobSeekerProfileService {

    private final JobSeekerProfileRepoInterface jobSeekerProfileRepo;

    @Autowired
    public JobSeekerProfileService(JobSeekerProfileRepoInterface jobSeekerProfileRepo) {
        this.jobSeekerProfileRepo = jobSeekerProfileRepo;
    }

    public Optional<JobSeekerProfile> getById(int id){
        return jobSeekerProfileRepo.findById(id);
    }

    public JobSeekerProfile addNew(JobSeekerProfile profile){
        return jobSeekerProfileRepo.save(profile);
    }

}
