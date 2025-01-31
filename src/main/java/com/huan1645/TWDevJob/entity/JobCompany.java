package com.huan1645.TWDevJob.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "job_company")
public class JobCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Nullable
    private String logo;

    public JobCompany() {

    }

    public JobCompany(int id, String name, @Nullable String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getLogo() {
        return logo;
    }

    public void setLogo(@Nullable String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "JobCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
