package com.huan1645.TWDevJob.entity;


public class RecruiterJobsDto {

    private Long totalCandidates;
    private Integer jobsPostId;
    private String jobTitle;
    private JobLocation jobLocation;
    private JobCompany jobCompany;

    public RecruiterJobsDto(Long totalCandidates, Integer jobsPostId, String jobTitle, JobLocation jobLocation, JobCompany jobCompany) {
        this.totalCandidates = totalCandidates;
        this.jobsPostId = jobsPostId;
        this.jobTitle = jobTitle;
        this.jobLocation = jobLocation;
        this.jobCompany = jobCompany;
    }

    public Long getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(Long totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public Integer getJobsPostId() {
        return jobsPostId;
    }

    public void setJobsPostId(Integer jobsPostId) {
        this.jobsPostId = jobsPostId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    public JobCompany getJobCompany() {
        return jobCompany;
    }

    public void setJobCompany(JobCompany jobCompany) {
        this.jobCompany = jobCompany;
    }
}
