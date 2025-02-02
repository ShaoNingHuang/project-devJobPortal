package com.huan1645.TWDevJob.service;

import com.huan1645.TWDevJob.entity.JobPostActivity;
import com.huan1645.TWDevJob.entity.JobSeekerProfile;
import com.huan1645.TWDevJob.entity.JobSeekerSave;
import com.huan1645.TWDevJob.repository.JobSeekerSaveRepoInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class JobSeekerSaveService {
    private final JobSeekerSaveRepoInterface jobSeekerSaveRepo;

    @Autowired
    public JobSeekerSaveService(JobSeekerSaveRepoInterface jobSeekerSaveRepoInterface) {
        this.jobSeekerSaveRepo = jobSeekerSaveRepoInterface;
    }


    public List<JobSeekerSave> getCandidateJobs(JobSeekerProfile profile){
        return jobSeekerSaveRepo.findByUserId(profile);
    }
    public List<JobSeekerSave> getJobCandidates(JobPostActivity job){
        return jobSeekerSaveRepo.findByJob(job);
    }


    public void addNew(JobSeekerSave jobSeekerSave) {
        jobSeekerSaveRepo.save(jobSeekerSave);
    }
}
