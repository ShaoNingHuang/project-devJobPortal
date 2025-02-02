package com.huan1645.TWDevJob.entity;

import jakarta.persistence.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.Serializable;

@Entity
@Table(name = "job_seeker_save", uniqueConstraints =
        { @UniqueConstraint( columnNames = {"user_id", "job"}
)})
public class JobSeekerSave implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_account_id")
    private JobSeekerProfile userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "job_post_id")
    private JobPostActivity job;


    public JobSeekerSave(Integer id, JobSeekerProfile user_id, JobPostActivity job) {
        this.id = id;
        this.userId = user_id;
        this.job = job;
    }

    public JobSeekerSave() {
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

    @Override
    public String toString() {
        return "JobSeekerSave{" +
                "id=" + id +
                ", user_id=" + userId +
                ", job=" + job +
                '}';
    }
}
