package com.eugene.SessionManager;

import com.eugene.SessionManager.controllers.SessionController;
import com.eugene.SessionManager.dto.SessionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionController.class)
@AutoConfigureMockMvc
public class SessionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SessionController sessionController;

	@Test
	public void testCreateSession() throws Exception {
		SessionDto sessionDto = new SessionDto("f6567dd8-e069-418e-8893-7d22fcf12455", 123456, 123456, 600, 0, "eugene", new HashMap<>());

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/sessions/{sessionId}", "f6567dd8-e069-418e-8893-7d22fcf12455")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(sessionDto))
				)
				.andExpect(status().isCreated());
	}

	@Test
	public void testUpdateSession() throws Exception {
		SessionDto sessionDto = new SessionDto("1", 123456, 123456, 600, 0, "user1", new HashMap<>());

		mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/v1/sessions/{sessionId}", "f6567dd8-e069-418e-8893-7d22fcf12455")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(sessionDto)))
				.andExpect(status().isNotFound());
	}


	@Test
	public void testGetSession() throws Exception {
		SessionDto sessionDto = new SessionDto("1", 123456, 123456, 600, 0, "user1", new HashMap<>());

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/sessions/{sessionId}", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(sessionDto))
		);


		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/sessions/{sessionId}", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sessionId").value("1"))
				.andExpect(jsonPath("$.principalName").value("user1"));
	}



	@Test
	public void testDeleteSession() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/sessions/{sessionId}", "1"))
				.andExpect(status().isNoContent());


		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sessions/{sessionId}", "1"))
				.andExpect(status().isNotFound());
	}



}
