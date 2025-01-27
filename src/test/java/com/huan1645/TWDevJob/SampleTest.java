package com.huan1645.TWDevJob;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class SampleTest {
    @Test
    public void sampleTest(){
        int sampledata = 1;
        assertEquals(sampledata, 1);

    }
}
