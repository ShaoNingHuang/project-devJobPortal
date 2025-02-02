package com.huan1645.TWDevJob.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name= "job_seeker_profile")
public class JobSeekerProfile {

    @Id
    private int user_account_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    @MapsId
    private User userId;

    private String first_name;
    private String last_name;
    private String city;
    private String country;
    private String employment_type;
    private String state;
    private String work_authorization;
    @Column(nullable = true)
    private String profile_photo;
    @Column(nullable = true)
    private String resume;

    @OneToMany(targetEntity = Skills.class, cascade = CascadeType.ALL, mappedBy = "jobSeekerProfile")
    private List<Skills> skills;

    public JobSeekerProfile() {
    }

    public JobSeekerProfile(User userId) {
        this.userId = userId;
    }

    public JobSeekerProfile(int user_account_id, User userId, String first_name, String last_name, String city, String country, String employment_type, String state, String profile_photo, String resume, List<Skills> skills, String work_authorization) {
        this.user_account_id = user_account_id;
        this.userId = userId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.city = city;
        this.country = country;
        this.employment_type = employment_type;
        this.state = state;
        this.profile_photo = profile_photo;
        this.resume = resume;
        this.skills = skills;
        this.work_authorization = work_authorization;
    }

    public int getUser_account_id() {
        return user_account_id;
    }

    public void setUser_account_id(int user_account_id) {
        this.user_account_id = user_account_id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWork_authorization() {
        return work_authorization;
    }

    public void setWork_authorization(String work_authorization) {
        this.work_authorization = work_authorization;
    }

    @Transient
    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "JobSeekerProfile{" +
                "user_account_id=" + user_account_id +
                ", userId=" + userId +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", employment_type='" + employment_type + '\'' +
                ", state='" + state + '\'' +
                ", work_authorization='" + work_authorization + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", resume='" + resume + '\'' +
                '}';
    }
}
