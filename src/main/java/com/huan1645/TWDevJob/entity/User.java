package com.huan1645.TWDevJob.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int user_id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "registration_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registration_date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_type_id", referencedColumnName = "user_type_id")
    private UserType user_type_id;

    public User() {
    }

    public User(int user_id, String email, String password, boolean is_active, Date registration_date, UserType user_type_id) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.is_active = is_active;
        this.registration_date = registration_date;
        this.user_type_id = user_type_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public UserType getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(UserType user_type_id) {
        this.user_type_id = user_type_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", is_active=" + is_active +
                ", password='" + password + '\'' +
                ", registration_date=" + registration_date +
                ", user_type_id=" + user_type_id +
                '}';
    }
}
