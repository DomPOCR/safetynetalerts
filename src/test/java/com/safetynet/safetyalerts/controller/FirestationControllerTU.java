package com.safetynet.safetyalerts.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.safetynet.safetyalerts.service.FirestationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@AutoConfigureMockMvc
class FirestationControllerTU {

	@Autowired
	MockMvc mockmvc;
	@MockBean
	FirestationService firestationService;

	@Test
	void FirestationController() throws Exception {

		// Test 1 : on envoie une requête GET avec en paramètre une adresse
		// valide
		// + on vérifie que le statut de la réponse est 200

		mockmvc.perform(MockMvcRequestBuilders.get("/fire").param("address",
				"892 Downing Ct"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons
		// paramètres

		Mockito.verify(firestationService, Mockito.times(1))
				.getFireStationListPerson("892 Downing Ct");

		// Test 1 : on envoie une requête GET avec en paramètre une adresse
		// non valide
		// + on vérifie que le retour est vide

		mockmvc.perform(MockMvcRequestBuilders.get("/fire").param("address",
				"999 Downing Ct"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));

	}

}
