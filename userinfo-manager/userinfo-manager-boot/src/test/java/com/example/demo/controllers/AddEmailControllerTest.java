package com.example.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddEmailController.class)
class AddEmailControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addOtpSend() throws Exception {
        mockMvc.perform(post("/api/v1/userinfo/email/add/otp/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"vlad.perchun.03@gmail.com\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(
                                Matchers.allOf(
                                        Matchers.hasKey("uuid"),
                                        Matchers.hasKey("expired")
                                )
                        )
                );

        mockMvc.perform(post("/api/v1/userinfo/email/add/otp/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"vlad.perchun.03gmailom\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("OPERATION_BLOCKED"))
                .andExpect(jsonPath("$.params").value(
                                Matchers.allOf(
                                        Matchers.hasKey("unlockTime")
                                )
                        )
                );
    }

    @Test
    void addOtpConfirm() throws Exception {
        mockMvc.perform(post("/api/v1/userinfo/email/add/otp/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"vlad.perchun.03@gmail.com\",\"code\": \"12934\"}")
                )
                .andExpect(status().isNoContent());

        mockMvc.perform(post("/api/v1/userinfo/email/add/otp/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"vlad.percgmail.com\",\"code\": \"34\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("INVALID_DATA"))
                .andExpect(jsonPath("$.params").value(
                                Matchers.allOf(
                                        Matchers.hasKey("attemptsLeft")
                                )
                        )
                );
    }
}
