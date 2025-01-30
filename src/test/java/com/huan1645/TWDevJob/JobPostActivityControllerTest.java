package com.huan1645.TWDevJob;


import com.huan1645.TWDevJob.security.NoSecurityTestConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@ContextConfiguration(classes = NoSecurityTestConfig.class)
@AutoConfigureMockMvc
public class JobPostActivityControllerTest {

}
