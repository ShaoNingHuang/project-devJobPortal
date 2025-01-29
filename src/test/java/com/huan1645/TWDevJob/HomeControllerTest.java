package com.huan1645.TWDevJob;

import com.huan1645.TWDevJob.security.NoSecurityTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@ContextConfiguration(classes = NoSecurityTestConfig.class)
@AutoConfigureMockMvc
public class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getHomepageTest() throws Exception {
        MvcResult mvcresult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcresult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");
    }
}
