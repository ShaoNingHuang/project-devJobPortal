package com.huan1645.TWDevJob.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter_profile")
public class RecruiterProfile {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    @MapsId
    private User userId;

    @Id
    private int user_account_id;

    private String city;
    private String company;
    private String country;
    private String first_name;
    private String last_name;
    private String state;
    @Column(name = "profile_photo", nullable = true, length = 64)
    private String profile_photo;


    public RecruiterProfile(User userId) {
        this.userId = userId;
    }

    public RecruiterProfile(User userId, int user_account_id, String city, String company, String country, String last_name, String first_name, String state, String profile_photo) {
        this.userId = userId;
        this.user_account_id = user_account_id;
        this.city = city;
        this.company = company;
        this.country = country;
        this.last_name = last_name;
        this.first_name = first_name;
        this.state = state;
        this.profile_photo = profile_photo;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getUser_account_id() {
        return user_account_id;
    }

    public void setUser_account_id(int user_account_id) {
        this.user_account_id = user_account_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    @Override
    public String toString() {
        return "RecruiterProfile{" +
                "userId=" + userId +
                ", user_account_id=" + user_account_id +
                ", city='" + city + '\'' +
                ", company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", state='" + state + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                '}';
    }
}
