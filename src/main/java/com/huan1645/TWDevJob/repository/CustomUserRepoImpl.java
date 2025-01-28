package com.huan1645.TWDevJob.repository;

import com.huan1645.TWDevJob.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomUserRepoImpl implements CustomUserRepoInterface{

    private final EntityManager em;
    @Autowired
    public CustomUserRepoImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email= :email", User.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }
}
