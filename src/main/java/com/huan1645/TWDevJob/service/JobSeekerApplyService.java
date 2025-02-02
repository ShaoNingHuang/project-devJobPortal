package com.huan1645.TWDevJob.service;

import com.huan1645.TWDevJob.entity.JobPostActivity;
import com.huan1645.TWDevJob.entity.JobSeekerApply;
import com.huan1645.TWDevJob.entity.JobSeekerProfile;
import com.huan1645.TWDevJob.repository.JobSeekerApplyRepoInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class JobSeekerApplyService {
    private final JobSeekerApplyRepoInterface jobSeekerApplyRepo;

    @Autowired
    public JobSeekerApplyService(JobSeekerApplyRepoInterface jobSeekerApplyRepo) {
        this.jobSeekerApplyRepo = jobSeekerApplyRepo;
    }

    public List<JobSeekerApply> getCandidateJobs(JobSeekerProfile profile){
        return jobSeekerApplyRepo.findByUserId(profile);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity job){
        return jobSeekerApplyRepo.findByJob(job);
    }

    public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepo.save(jobSeekerApply);
    }
}
