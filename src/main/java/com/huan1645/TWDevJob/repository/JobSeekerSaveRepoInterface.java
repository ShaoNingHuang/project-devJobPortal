package com.huan1645.TWDevJob.repository;

import com.huan1645.TWDevJob.entity.JobPostActivity;
import com.huan1645.TWDevJob.entity.JobSeekerProfile;
import com.huan1645.TWDevJob.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepoInterface extends JpaRepository<JobSeekerSave, Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile profile);
    List<JobSeekerSave> findByJob(JobPostActivity job);
}
