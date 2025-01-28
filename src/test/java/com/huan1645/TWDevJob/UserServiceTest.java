package com.huan1645.TWDevJob;

import static org.mockito.Mockito.*;

import com.huan1645.TWDevJob.entity.JobSeekerProfile;
import com.huan1645.TWDevJob.entity.RecruiterProfile;
import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.entity.UserType;
import com.huan1645.TWDevJob.exception.EmptyPasswordException;
import com.huan1645.TWDevJob.exception.UserExistedException;
import com.huan1645.TWDevJob.repository.JobSeekerProfileRepoInterface;
import com.huan1645.TWDevJob.repository.RecruiterProfileRepoInterface;
import com.huan1645.TWDevJob.repository.UserRepoInterface;
import com.huan1645.TWDevJob.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UserServiceTest {


    @Mock
    private UserRepoInterface repo;

    @Mock
    private RecruiterProfileRepoInterface recruiterRepo;

    @Mock
    private JobSeekerProfileRepoInterface jobSeekerRepo;

    @InjectMocks
    private UserService service;

    @Autowired
    JdbcTemplate jdbc;

    @BeforeEach
    void setUp(){
        jdbc.execute("DELETE FROM users;");
    }

    @Test
    void registerUserTestSuccessRecruiter(){
        User testUser = new User();
        testUser.setIs_active(false);
        testUser.setEmail("test1@example.com");
        testUser.setPassword("password123");
        UserType recruiterType = new UserType();
        recruiterType.setUser_type_id(1); // Recruiter type ID
        testUser.setUser_type_id(recruiterType);


        when(repo.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(repo.save(testUser)).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setUser_id(1); // Simulate auto-generated ID
            return savedUser;
        });

        User registeredUser = service.registerUser(testUser);
        assertNotNull(registeredUser, "The registered user should not be null.");
        assertTrue(registeredUser.isIs_active(), "The user should be active after registration.");
        assertNotNull(registeredUser.getRegistration_date(), "The registration date should be set.");
        assertEquals(1, registeredUser.getUser_id(), "The user ID should be auto-generated and set correctly.");

        verify(repo, times(1)).findByEmail(testUser.getEmail());
        verify(repo, times(1)).save(testUser);
        verify(recruiterRepo, times(1)).save(any(RecruiterProfile.class));
    }
    @Test
    void registerUserTestSuccessJobSeeker(){
        User testUser = new User();
        testUser.setIs_active(false);
        testUser.setEmail("test1@example.com");
        testUser.setPassword("password123");
        UserType jobSeekerType = new UserType();
        jobSeekerType.setUser_type_id(2); // Recruiter type ID
        testUser.setUser_type_id(jobSeekerType);


        when(repo.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(repo.save(testUser)).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setUser_id(1); // Simulate auto-generated ID
            return savedUser;
        });

        User registeredUser = service.registerUser(testUser);
        assertNotNull(registeredUser, "The registered user should not be null.");
        assertTrue(registeredUser.isIs_active(), "The user should be active after registration.");
        assertNotNull(registeredUser.getRegistration_date(), "The registration date should be set.");
        assertEquals(1, registeredUser.getUser_id(), "The user ID should be auto-generated and set correctly.");

        verify(repo, times(1)).findByEmail(testUser.getEmail());
        verify(repo, times(1)).save(testUser);
        verify(jobSeekerRepo, times(1)).save(any(JobSeekerProfile.class));
    }




    @Test
    void registerUserException(){
          User testUser = new User();
          testUser.setEmail("test@example.com");
          when(repo.findByEmail(testUser.getEmail())).thenThrow(new UserExistedException("User already Existed"));
          assertThrows(UserExistedException.class, () -> {service.registerUser(testUser);}, "UserExistedException should be thrown");
          verify(repo, times(0)).save(testUser);
    }

    @Test
    void registerEmptyPasswordException(){
        User testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("    ");
        assertThrows(EmptyPasswordException.class, () -> {service.registerUser(testUser);}, "EmptyPasswordException should be thrown");
        verify(repo, times(0)).save(testUser);
    }

    @AfterEach
    void cleanUp(){
        jdbc.execute("DELETE FROM users;");
    }


}
