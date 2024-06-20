package com.microsoft.hackathon.copilotdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(DemoController.class)
public class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to the Copilot Demo!"));
    }

    @Test
    public void testGetDateTimeWithTimezone() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/datetime"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // Add more assertions for the expected response content if needed
    }
}