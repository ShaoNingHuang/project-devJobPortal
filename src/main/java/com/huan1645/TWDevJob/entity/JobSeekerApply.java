package com.huan1645.TWDevJob.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "job_seeker_apply", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "job"})
})
public class JobSeekerApply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_account_id")
    private JobSeekerProfile userId;

    @ManyToOne()
    @JoinColumn(name = "job", referencedColumnName = "job_post_id")
    private JobPostActivity job;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date apply_date;

    private String cover_letter;

    public JobSeekerApply() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JobSeekerProfile getUser_id() {
        return userId;
    }

    public void setUser_id(JobSeekerProfile user_id) {
        this.userId = user_id;
    }

    public JobPostActivity getJob() {
        return job;
    }

    public void setJob(JobPostActivity job) {
        this.job = job;
    }

    public Date getApply_date() {
        return apply_date;
    }

    public void setApply_date(Date apply_date) {
        this.apply_date = apply_date;
    }

    public String getCover_letter() {
        return cover_letter;
    }

    public void setCover_letter(String cover_letter) {
        this.cover_letter = cover_letter;
    }

    @Override
    public String toString() {
        return "JobSeekerApply{" +
                "id=" + id +
                ", user_id=" + userId +
                ", job=" + job +
                ", apply_date=" + apply_date +
                ", cover_letter='" + cover_letter + '\'' +
                '}';
    }
}
