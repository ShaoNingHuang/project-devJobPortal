package com.huan1645.TWDevJob.repository;

import com.huan1645.TWDevJob.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepoInterface extends JpaRepository<User, Integer>, CustomUserRepoInterface {

}
