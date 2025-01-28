package com.huan1645.TWDevJob.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "users_type")
public class UserType {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_type_id;

    @Column(name = "user_type_name")
    private String user_type_name;

    @OneToMany(mappedBy = "user_type_id", targetEntity = User.class, cascade = CascadeType.ALL)
    List<User> Users;

    public UserType() {
    }

    public UserType(String user_type_name, List<User> users, int user_type_id) {
        this.user_type_name = user_type_name;
        Users = users;
        this.user_type_id = user_type_id;
    }

    public int getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(int user_type_id) {
        this.user_type_id = user_type_id;
    }

    public String getUser_type_name() {
        return user_type_name;
    }

    public void setUser_type_name(String user_type_name) {
        this.user_type_name = user_type_name;
    }

    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> users) {
        Users = users;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "user_type_id=" + user_type_id +
                ", user_type_name='" + user_type_name + '\'' +
                '}';
    }
}
