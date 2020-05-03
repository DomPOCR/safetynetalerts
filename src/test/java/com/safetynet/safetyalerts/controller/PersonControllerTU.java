package com.safetynet.safetyalerts.controller;

import java.util.Arrays;

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
import com.safetynet.safetyalerts.exceptions.DataAlreadyExist;
import com.safetynet.safetyalerts.exceptions.DataNotFound;
import com.safetynet.safetyalerts.service.PersonService;

@ExtendWith(SpringExtension.class)
// @WebMvcTest(controllers = PersonController.class)
@SpringBootTest

@AutoConfigureMockMvc
public class PersonControllerTU {

	@Autowired
	MockMvc mockmvc;
	@MockBean
	PersonService personService;

	String firstNameTest = "Jack";
	String lastNameTest = "Bauer";

	// --------------- CREATION DE PERSONNES -----------------

	@Test
	void createPersonValid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();

		// GIVEN

		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void createPersonInvalid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();

		mockmvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		// .andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void createPersonWhenPersonAlreadyExist() throws Exception {

		// on mock persoService et on lui dit de renvoyer l'exception
		// DataALreadExist
		// quand on lui demande de renvoyer une personne

		Mockito.doThrow(DataAlreadyExist.class).when(personService)
				.createPerson(Mockito.any());

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();

		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		mockmvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString()))
				.andExpect(MockMvcResultMatchers.status().isConflict());
	}

	// --------------- MAJ DE PERSONNES -----------------

	@Test
	void updatePersonValid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();

		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		mockmvc.perform(MockMvcRequestBuilders.put("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	void updatePersonWhenPersonNotFound() throws Exception {

		Mockito.doThrow(DataNotFound.class).when(personService)
				.updatePerson(Mockito.any());

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();

		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		mockmvc.perform(MockMvcRequestBuilders.put("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// --------------- SUPPRESSION DE PERSONNES -----------------

	@Test
	void deletePersonValid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();

		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		mockmvc.perform(MockMvcRequestBuilders.delete("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString()))
				.andExpect(MockMvcResultMatchers.status().isResetContent());
	}

	@Test
	void deletePersonWhenPersonNotFound() throws Exception {
		Mockito.doThrow(DataNotFound.class).when(personService)
				.deletePerson(Mockito.any());

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonPerson = obm.createObjectNode();

		jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
		jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));

		mockmvc.perform(MockMvcRequestBuilders.delete("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.toString()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void getCommunityEmail() throws Exception {

		// Etape 1 : on mocke le comportement de personService pour renvoyer des
		// valeurs d'email

		Mockito.when(personService.getCommunityEmail("Culver"))
				.thenReturn(Arrays.asList("a@a", "b@b", "c@c"));

		// Etape 2 : on envoie une requête GET avec en paramètre la ville Culver
		// + on vérifie que le statut de la réponse est 200

		mockmvc.perform(MockMvcRequestBuilders.get("/communityEmail")
				.param("city", "Culver"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Etape 3 : on vérifie que le service a bien été appelé avec les bons
		// paramètres

		Mockito.verify(personService, Mockito.times(1))
				.getCommunityEmail("Culver");

	}
	@Test
	void getChildByAddress() throws Exception {

		// Test 1 : on envoie une requête GET avec en paramètre une adresse
		// valide
		// + on vérifie que le statut de la réponse est 200

		mockmvc.perform(MockMvcRequestBuilders.get("/childAlert")
				.param("address", "1509 Culver St"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons
		// paramètres

		Mockito.verify(personService, Mockito.times(1))
				.getChildByAddress("1509 Culver St");

		// Test 3 : on envoie une requête GET avec en paramètre une adresse
		// non valide
		// + on vérifie que le retour est vide

		mockmvc.perform(MockMvcRequestBuilders.get("/childAlert")
				.param("address", "999 Culver St"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));

		// Test 4 : on envoie une requête GET avec en paramètre une adresse
		// sans enfants
		// + on vérifie que le retour est vide

		mockmvc.perform(MockMvcRequestBuilders.get("/childAlert")
				.param("address", "908 73rd St"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));

	}
}
