package com.maids.Library_Management_System;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.Library_Management_System.Controllers.PatronController;
import com.maids.Library_Management_System.Models.Patron;
import com.maids.Library_Management_System.Repositories.RoleRepository;
import com.maids.Library_Management_System.Services.PatronService;

@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
@WebMvcTest(PatronController.class)
public class PatronControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PatronService patronService;

	@MockBean
	private RoleRepository roleRepository;

	@Test
	public void getAllPatrons_ReturnsPatronList() throws Exception {
		List<Patron> patrons = new ArrayList<>();
		patrons.add(new Patron(1L, "John Doe", "1234567890"));
		patrons.add(new Patron(2L, "Jane Doe", "0987654321"));

		given(patronService.findAllPatrons()).willReturn(patrons);

		mockMvc.perform(get("/user/api/patrons").contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("John Doe")).andExpect(jsonPath("$[1].name").value("Jane Doe"));
	}

	@Test
	public void getPatronById_Exists_ReturnsPatron() throws Exception {
		Patron patron = new Patron(1L, "John Doe", "1234567890");
		given(patronService.findPatronById(1L)).willReturn(Optional.of(patron));

		mockMvc.perform(get("/user/api/patrons/{id}", 1L).contentType(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("John Doe"));
	}

	@Test
	public void getPatronById_NotFound_ReturnsNotFound() throws Exception {
		given(patronService.findPatronById(1L)).willReturn(Optional.empty());

		mockMvc.perform(get("/user/api/patrons/{id}", 1L).contentType(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isNotFound());
	}

	@Test
	public void createPatron_SavesNewPatron_ReturnsSavedPatron() throws Exception {
		Patron patron = new Patron(null, "John Doe", "1234567890");
		Patron savedPatron = new Patron(1L, "John Doe", "1234567890");
		given(patronService.savePatron(any(Patron.class))).willReturn(savedPatron);

		mockMvc.perform(post("/user/api/patrons").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(patron)).with(csrf())).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("John Doe"));
	}

	@Test
	public void updatePatron_Exists_UpdatesAndReturnsPatron() throws Exception {
		Patron updatedDetails = new Patron(null, "Updated Name", "1234567890");
		Patron updatedPatron = new Patron(1L, "Updated Name", "1234567890");
		given(patronService.updatePatron(eq(1L), any(Patron.class))).willReturn(ResponseEntity.ok(updatedPatron));

		mockMvc.perform(put("/user/api/patrons/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedDetails)).with(csrf())).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Updated Name"));
	}

	@Test
	public void updatePatron_NotFound_ReturnsNotFound() throws Exception {
		Patron updatedDetails = new Patron(null, "Updated Name", "1234567890");
		given(patronService.updatePatron(eq(1L), any(Patron.class))).willReturn(ResponseEntity.notFound().build());

		mockMvc.perform(put("/user/api/patrons/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedDetails)).with(csrf())).andExpect(status().isNotFound());
	}

	@Test
	public void deletePatron_Exists_DeletesAndReturnsOk() throws Exception {
		Long patronId = 1L;
		given(patronService.deletePatron(patronId)).willReturn(ResponseEntity.ok().build());

		mockMvc.perform(delete("/user/api/patrons/{id}", patronId).contentType(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isOk());

		verify(patronService).deletePatron(patronId);
	}

	@Test
	public void deletePatron_NotFound_ReturnsNotFound() throws Exception {
		Long patronId = 2L;
		given(patronService.deletePatron(patronId)).willReturn(ResponseEntity.notFound().build());

		mockMvc.perform(delete("/user/api/patrons/{id}", patronId).contentType(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isNotFound());

		verify(patronService).deletePatron(patronId);
	}

}
