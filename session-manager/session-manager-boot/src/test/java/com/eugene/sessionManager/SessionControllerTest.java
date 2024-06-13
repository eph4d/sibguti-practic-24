package com.eugene.sessionManager;

import com.eugene.sessionManager.controller.SessionController;
import com.eugene.sessionManager.entity.Session;
import com.eugene.sessionManager.entity.SessionAttributeBytesDtoV1;
import com.eugene.sessionManager.service.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {
    @Mock
    private SessionService sessionService;
    @InjectMocks
    private SessionController sessionController;
    private MockMvc mockMvc;
    private Session session;
    private String jsonSession;

    @BeforeEach
    void setUp() throws JsonProcessingException
    {
        mockMvc = MockMvcBuilders.standaloneSetup(sessionController).build();
        session = new Session("12345",
                123,
                123,
                123,
                123,
                "testUser",
                Map.of("testAttribute1", "testValue1")
        );
        ObjectMapper objWrapper = new ObjectMapper();
        jsonSession = objWrapper.writeValueAsString(session);
    }

    @Test
    void createSession() throws Exception
    {
        mockMvc.perform(post("/api/v1/sessions/{sessionId}", "12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSession))
                .andExpect(status().isNoContent());
        verify(sessionService, times(1)).createSession(session);
    }

    @Test
    void updateSession() throws Exception {
        Mockito.when(sessionService.getSession("12345")).thenReturn(Optional.of(session));

        mockMvc.perform(put("/api/v1/sessions/{sessionId}", "12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSession))
                .andExpect(status().isNoContent());
        verify(sessionService, times(1)).updateSession(session);
    }

    @Test
    public void findSession() throws Exception {
        when(sessionService.getSession("12345")).thenReturn(Optional.of(session));

        mockMvc.perform(get("/api/v1/sessions/12345"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sessionId").value(session.getSessionId()))
                .andExpect(jsonPath("$.creationTime").value(session.getCreationTime()))
                .andExpect(jsonPath("$.lastAccessTime").value(session.getLastAccessTime()))
                .andExpect(jsonPath("$.maxInactiveInterval").value(session.getMaxInactiveInterval()))
                .andExpect(jsonPath("$.expiryTime").value(session.getExpiryTime()))
                .andExpect(jsonPath("$.principalName").value(session.getPrincipalName()))
                .andExpect(status().isOk());
    }

    @Test
    void findByPrincipalName() throws Exception {
        List<Session> sessionList = List.of(session);
        when(sessionService.findByPrincipalName("testUser")).thenReturn(sessionList);

        mockMvc.perform(get("/api/v1/sessions/findByPrincipalName").param("principalName", "testUser"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].sessionId").value(sessionList.get(0).getSessionId()))
                .andExpect(status().isOk());
        verify(sessionService, times(1)).findByPrincipalName("testUser");
    }

}
