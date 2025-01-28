package com.huan1645.TWDevJob;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbc;

    @BeforeEach()
    void setUp(){
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
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcresult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "dashboard");
    }

}