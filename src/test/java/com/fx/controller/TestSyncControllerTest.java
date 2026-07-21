package com.fx.controller;

import com.fx.SpringInActionApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringInActionApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //use full context
@AutoConfigureMockMvc

//@WebMvcTest(TestController.class) //use only components required in test, doesn`t work
public class TestSyncControllerTest {

    @Autowired
    private MockMvc mockMvc; //sync

    @Test
    public void testPageSyncTest() throws Exception {
        mockMvc.perform(get("/test/test_page_button"))
            .andExpect(status().isOk());
    }

}
