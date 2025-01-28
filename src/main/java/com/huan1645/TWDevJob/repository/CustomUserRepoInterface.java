package com.huan1645.TWDevJob.repository;

import com.huan1645.TWDevJob.entity.User;

import java.util.Optional;

public interface CustomUserRepoInterface {
    public Optional<User> findByEmail(String email);
}
