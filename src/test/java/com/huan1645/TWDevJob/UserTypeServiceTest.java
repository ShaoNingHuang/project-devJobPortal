package com.huan1645.TWDevJob;

import com.huan1645.TWDevJob.entity.UserType;
import com.huan1645.TWDevJob.repository.UserTypeRepoInterface;
import com.huan1645.TWDevJob.service.UserTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UserTypeServiceTest {

    @Mock
    private UserTypeRepoInterface repo;

    @InjectMocks
    private UserTypeService service;

    @Test
    void getAllServiceTest(){
        List<UserType> mockUserTypes = Arrays.asList(
                new UserType("Admin", null, 1),
                new UserType("User", null, 2)
        );
        when(repo.findAll()).thenReturn(mockUserTypes);
        List<UserType> res = service.getAll();
        assertEquals(2, res.size());
    }
}


