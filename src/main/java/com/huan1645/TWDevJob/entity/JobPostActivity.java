package com.huan1645.TWDevJob.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="job_post_activity")
public class JobPostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_post_id")
    private int job_post_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posted_by_id", referencedColumnName = "user_id")
    private User posted_by_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_location_id", referencedColumnName = "id")
    private JobLocation job_location_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "job_company_id", referencedColumnName = "id")
    private JobCompany job_company_id;

    @Transient
    private boolean isActive;

    @Transient
    private boolean isSaved;

    @Length(max = 10000)
    private String description_of_job;

    private String job_type;

    private String salary;

    private String remote;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date posted_date;

    private String job_title;

    public JobPostActivity() {
    }


    public JobPostActivity(int job_post_id, User posted_by_id, JobLocation job_location_id, JobCompany job_company_id, boolean isActive, boolean isSaved, String description_of_job, String job_type, String salary, String remote, Date posted_date, String job_title) {
        this.job_post_id = job_post_id;
        this.posted_by_id = posted_by_id;
        this.job_location_id = job_location_id;
        this.job_company_id = job_company_id;
        this.isActive = isActive;
        this.isSaved = isSaved;
        this.description_of_job = description_of_job;
        this.job_type = job_type;
        this.salary = salary;
        this.remote = remote;
        this.posted_date = posted_date;
        this.job_title = job_title;
    }

    public int getJob_post_id() {
        return job_post_id;
    }

    public void setJob_post_id(int job_post_id) {
        this.job_post_id = job_post_id;
    }

    public User getPosted_by_id() {
        return posted_by_id;
    }

    public void setPosted_by_id(User posted_by_id) {
        this.posted_by_id = posted_by_id;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public boolean getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean saved) {
        isSaved = saved;
    }

    public @Length(max = 10000) String getDescription_of_job() {
        return description_of_job;
    }

    public void setDescription_of_job(@Length(max = 10000) String description_of_job) {
        this.description_of_job = description_of_job;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public Date getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(Date posted_date) {
        this.posted_date = posted_date;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    @Override
    public String toString() {
        return "JobPostActivity{" +
                "job_post_id=" + job_post_id +
                ", posted_by_id=" + posted_by_id +
                ", job_location_id=" + job_location_id +
                ", job_company_id=" + job_company_id +
                ", isActive=" + isActive +
                ", isSaved=" + isSaved +
                ", description_of_job='" + description_of_job + '\'' +
                ", job_type='" + job_type + '\'' +
                ", salary='" + salary + '\'' +
                ", remote='" + remote + '\'' +
                ", posted_date='" + posted_date + '\'' +
                ", job_title='" + job_title + '\'' +
                '}';
    }
}
