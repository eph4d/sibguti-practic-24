package com.example.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserInfoController.class)
public class UserInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getUserInfo() throws Exception{
        mockMvc.perform(get("/api/v1/userinfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(
                        Matchers.allOf(
                                Matchers.hasKey("fullName"),
                                Matchers.hasKey("birthDateInfo"),
                                Matchers.hasKey("phoneInfo"),
                                Matchers.hasKey("emailInfoDto")
                        )
                ));
    }

    @Test
    void editFirstNameAndSurnameAndBirthdate() throws Exception{
        mockMvc.perform(post("/api/v1/userinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"familyName\":\"Evgeny\",\"givenName\": \"Flat\",\"birthDate\": \"2003-04-06\"}")
                )
                .andExpect(status().isNoContent());

        mockMvc.perform(post("/api/v1/userinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"familyName\":\"Vladislav\",\"givenName\": \"Perchun\",\"birthDate\": \"14:02:2003\"}")
                )
                .andExpect(status().isBadRequest());
    }
}
