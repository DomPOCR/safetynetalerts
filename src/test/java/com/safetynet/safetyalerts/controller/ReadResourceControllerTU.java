package com.safetynet.safetyalerts.controller;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.safetynet.safetyalerts.service.PersonService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ReadResourceController.class)
@AutoConfigureMockMvc
public class ReadResourceControllerTU {

	@Autowired
	MockMvc mockmvc;
	@MockBean
	PersonService personService;

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

}
