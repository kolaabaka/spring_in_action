package com.fx.controller;

import com.fx.SpringInActionApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest(classes = SpringInActionApplication.class) //use full context
//@AutoConfigureMockMvc

@WebMvcTest(DesignController.class) //use only components required in test
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void homePageDefaultTest() throws Exception {
        mockMvc.perform(get("/design"))
            .andExpect(status().isOk())
            .andExpect(view().name("designForm"))
            .andExpect(content().string(containsString("Submit your")));
    }
}
