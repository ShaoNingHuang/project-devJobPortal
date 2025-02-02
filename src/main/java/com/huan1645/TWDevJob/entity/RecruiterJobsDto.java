package com.huan1645.TWDevJob.entity;


public class RecruiterJobsDto {

    private Long totalCandidates;
    private Integer job_post_id;
    private String job_title;
    private JobLocation job_location_id;
    private JobCompany job_company_id;

    public RecruiterJobsDto(Long totalCandidates, Integer jobsPostId, String jobTitle, JobLocation jobLocation, JobCompany jobCompany) {
        this.totalCandidates = totalCandidates;
        this.job_post_id = jobsPostId;
        this.job_title = jobTitle;
        this.job_location_id = jobLocation;
        this.job_company_id = jobCompany;
    }

    public Long getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(Long totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public JobLocation getJob_location_id() {
        return job_location_id;
    }

    public void setJob_location_id(JobLocation job_location_id) {
        this.job_location_id = job_location_id;
    }

    public JobCompany getJob_company_id() {
        return job_company_id;
    }

    public void setJob_company_id(JobCompany job_company_id) {
        this.job_company_id = job_company_id;
    }

    public Integer getJob_post_id() {
        return job_post_id;
    }

    public void setJob_post_id(Integer job_post_id) {
        this.job_post_id = job_post_id;
    }
}
