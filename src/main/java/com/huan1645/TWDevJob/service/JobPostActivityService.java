package com.huan1645.TWDevJob.service;

import com.huan1645.TWDevJob.entity.*;
import com.huan1645.TWDevJob.repository.JobPostActivityRepoInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class JobPostActivityService {

    private final JobPostActivityRepoInterface jobPostActivityRepo;

    @Autowired
    public JobPostActivityService(JobPostActivityRepoInterface jobPostActivityRepo) {
        this.jobPostActivityRepo = jobPostActivityRepo;
    }

    public JobPostActivity addNew(JobPostActivity job){
        return jobPostActivityRepo.save(job);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiterid){
        List<IRecruiterJobs> recruiterJobDtos = jobPostActivityRepo.getRecruiterJobs(recruiterid);
        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
        for(IRecruiterJobs rec: recruiterJobDtos){
            JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
            JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
            recruiterJobsDtoList.add(new RecruiterJobsDto((rec.getTotalCandidates()), rec.getJob_post_id(), rec.getJob_title(), loc, comp));
        }
        return recruiterJobsDtoList;
    }

    public JobPostActivity getOne(int id) {
        return jobPostActivityRepo.findById(id).orElseThrow(()-> new RuntimeException("Job not found !"));
    }

    public List<JobPostActivity> getAll() {
        return jobPostActivityRepo.findAll();
    }

    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {
        return Objects.isNull(searchDate)?jobPostActivityRepo.searchWithoutDate(job, location, remote, type):jobPostActivityRepo.search(job, location, remote, type, searchDate);
    }
}



