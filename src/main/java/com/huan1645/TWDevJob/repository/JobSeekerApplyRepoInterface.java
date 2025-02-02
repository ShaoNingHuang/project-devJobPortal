package com.huan1645.TWDevJob.repository;


import com.huan1645.TWDevJob.entity.JobPostActivity;
import com.huan1645.TWDevJob.entity.JobSeekerApply;
import com.huan1645.TWDevJob.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepoInterface extends JpaRepository<JobSeekerApply, Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile profile);
    List<JobSeekerApply> findByJob(JobPostActivity job);

}
