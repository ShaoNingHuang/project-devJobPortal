package com.huan1645.TWDevJob;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import com.huan1645.TWDevJob.security.NoSecurityTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;


@SpringBootTest
@TestPropertySource("/application-test.properties")
@ContextConfiguration(classes = NoSecurityTestConfig.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbc;

    @BeforeEach()
    void setUp(){
        jdbc.execute("DELETE FROM recruiter_profile;");
        jdbc.execute("DELETE FROM job_seeker_profile;");
        jdbc.execute("DELETE FROM users;");
        jdbc.execute("DELETE FROM users_type;");
        jdbc.execute("INSERT INTO users_type(user_type_id, user_type_name) VALUES(1, 'Recruiter');");
        jdbc.execute("INSERT INTO users_type(user_type_id, user_type_name) VALUES(2, 'Job Seeker');");
    }

    @Test
    public void getRegistrationTest() throws Exception {
        MvcResult mvcresult = mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcresult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "register");
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "userTypes");
    }

    @Test
    public void registerUserEndpointTest() throws Exception {
        MvcResult mvcresult = mockMvc.perform(MockMvcRequestBuilders.post("/register/new")
                    .param("email", "test1@example.com")
                    .param("password", "securePassword123")
                    .param("is_active", "false")
                    .param("user_type_id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/"))
                .andReturn();

    }
    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser // Simulate an authenticated user
    public void testLogout() throws Exception {
        mockMvc.perform(get("/logout")) // Perform GET request to /logout
                .andExpect(status().is3xxRedirection()) // Expect HTTP 302 (redirect)
                .andExpect(redirectedUrl("/")); // Expect redirect to "/"
    }

}