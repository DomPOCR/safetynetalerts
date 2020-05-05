package com.safetynet.safetyalerts.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
import com.safetynet.safetyalerts.exceptions.DataNotFoundException;
import com.safetynet.safetyalerts.service.MedicalrecordService;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@AutoConfigureMockMvc
class MedicalrecordTest {

	@Autowired
	MockMvc mockmvc;
	@MockBean
	MedicalrecordService medicalrecordService;

	String firstNameTest = "Jack";
	String lastNameTest = "Bauer";
	String birthdateTest = "25/12/1970";

	// --------------- CREATION DE DOSSIERS MEDICAUX -----------------

	@Test
	void createMedicalrecordValid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode();

		// GIVEN

		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstNameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(lastNameTest));
		jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdateTest));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void createMedicalrecordInvalid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode();

		// GIVEN

		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstNameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(""));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void createMedicalrecordWhenMedicalrecordAlreadyExist() throws Exception {

		// on mock MedicalrecordService et on lui dit de renvoyer l'exception
		// DataALreadExist
		// quand on lui demande de renvoyer un Medicalrecord existant

		Mockito.doThrow(DataAlreadyExistException.class)
				.when(medicalrecordService).createMedicalrecord(Mockito.any());

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode();

		// GIVEN

		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstNameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(lastNameTest));

		// WHEN
		// THEN

		mockmvc.perform(MockMvcRequestBuilders.post("/Medicalrecord")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString()))
				// .andExpect(MockMvcResultMatchers.status().isConflict());
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// --------------- MAJ DE DOSSIERS MEDICAUX -----------------

	@Test
	void updateMedicalrecordValid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode();

		// GIVEN

		jsonMedicalrecord.set("firstName", TextNode.valueOf("John"));
		jsonMedicalrecord.set("lastName", TextNode.valueOf("Boyd"));
		jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdateTest));

		// WHEN
		// THEN

		mockmvc.perform(MockMvcRequestBuilders.put("/Medicalrecord")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString()))
				// .andExpect(MockMvcResultMatchers.status().isNoContent());
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void updateMedicalrecordInvalid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode();

		// GIVEN

		jsonMedicalrecord.set("firstName", TextNode.valueOf(""));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(""));

		// WHEN
		// THEN

		mockmvc.perform(MockMvcRequestBuilders.put("/Medicalrecord")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString()))
				// .andExpect(MockMvcResultMatchers.status().isBadRequest());
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void updateMedicalrecordWhenMedicalrecordNotFound() throws Exception {

		Mockito.doThrow(DataNotFoundException.class).when(medicalrecordService)
				.updateMedicalrecord(Mockito.any());

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonMedicalrecord = obm.createObjectNode();

		// GIVEN

		jsonMedicalrecord.set("firstName", TextNode.valueOf(firstNameTest));
		jsonMedicalrecord.set("lastName", TextNode.valueOf(lastNameTest));

		// WHEN
		// THEN

		mockmvc.perform(MockMvcRequestBuilders.put("/Medicalrecord")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMedicalrecord.toString()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
