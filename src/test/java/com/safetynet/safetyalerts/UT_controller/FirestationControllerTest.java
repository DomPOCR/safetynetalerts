package com.safetynet.safetyalerts.UT_controller;

import java.util.Arrays;
import java.util.List;

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
import com.safetynet.safetyalerts.service.FirestationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@AutoConfigureMockMvc
class FirestationControllerTest {

	@Autowired
	MockMvc mockmvc;
	@MockBean
	FirestationService firestationService;

	String StationTest = "99";
	String AddressTest = "999 Paris St";

	// --------------- CREATION DE CASERNES -----------------

	@Test
	void createFirestationValid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN

		jsonFirestation.set("station", TextNode.valueOf(StationTest));
		jsonFirestation.set("address", TextNode.valueOf(AddressTest));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.post("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	// @Test
	void createFirestationInvalid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN

		jsonFirestation.set("station", TextNode.valueOf(""));
		jsonFirestation.set("address", TextNode.valueOf("98765"));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.post("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void createFirestationWhenFirestationAlreadyExist() throws Exception {

		// on mock firestationService et on lui dit de renvoyer l'exception
		// DataALreadExist
		// quand on lui demande de renvoyer une firestationService existante

		Mockito.doThrow(DataAlreadyExistException.class)
				.when(firestationService).createFirestation(Mockito.any());

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN

		jsonFirestation.set("station", TextNode.valueOf(StationTest));
		jsonFirestation.set("address", TextNode.valueOf(AddressTest));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.post("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isConflict());
	}

	// --------------- MAJ DE CASERNES -----------------

	@Test
	void updateFirestationValid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN

		jsonFirestation.set("station", TextNode.valueOf(StationTest));
		jsonFirestation.set("address", TextNode.valueOf(AddressTest));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.put("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	// @Test
	void updateFirestationInvalid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN

		// jsonFirestation.set("station", TextNode.valueOf(""));
		// jsonFirestation.set("address", TextNode.valueOf(""));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.put("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void updateFirestationWhenFirestationNotFound() throws Exception {

		Mockito.doThrow(DataNotFoundException.class).when(firestationService)
				.updateFirestation(Mockito.any());

		// GIVEN

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// WHEN
		// THEN

		jsonFirestation.set("station", TextNode.valueOf(StationTest));
		jsonFirestation.set("address", TextNode.valueOf(AddressTest));

		mockmvc.perform(MockMvcRequestBuilders.put("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	// --------------- SUPPRESSION DE CASERNES -----------------

	@Test
	void deleteFirestationValid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN

		jsonFirestation.set("station", TextNode.valueOf(StationTest));
		jsonFirestation.set("address", TextNode.valueOf(AddressTest));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.delete("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isResetContent());
	}

	@Test
	void deleteFirestationValidByAddress() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN

		jsonFirestation.set("station", TextNode.valueOf(""));
		jsonFirestation.set("address", TextNode.valueOf(AddressTest));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.delete("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isResetContent());
	}

	@Test
	void deleteFirestationValidByStation() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN

		jsonFirestation.set("station", TextNode.valueOf(StationTest));
		jsonFirestation.set("address", TextNode.valueOf(""));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.delete("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isResetContent());
	}
	// @Test
	void deleteFirestationInvalid() throws Exception {

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// GIVEN

		jsonFirestation.set("station", TextNode.valueOf(""));
		jsonFirestation.set("address", TextNode.valueOf(""));

		// WHEN
		// THEN
		mockmvc.perform(MockMvcRequestBuilders.delete("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void deleteFirestationWhenFirestationNotFound() throws Exception {

		Mockito.doThrow(DataNotFoundException.class).when(firestationService)
				.deleteFirestation(Mockito.any());

		// GIVEN

		ObjectMapper obm = new ObjectMapper();
		ObjectNode jsonFirestation = obm.createObjectNode();

		// WHEN
		// THEN

		jsonFirestation.set("station", TextNode.valueOf(StationTest));
		jsonFirestation.set("address", TextNode.valueOf(AddressTest));

		mockmvc.perform(MockMvcRequestBuilders.delete("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFirestation.toString()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

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

		// Test 3 : on envoie une requête GET avec en paramètre une adresse
		// non valide
		// + on vérifie que le retour est vide

		mockmvc.perform(MockMvcRequestBuilders.get("/fire").param("address",
				"999 Downing Ct"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));

	}

	@Test
	void getFireStationListPhone() throws Exception {

		// Test 1 : on envoie une requête GET avec en paramètre un n° de station
		// valide
		// + on vérifie que le statut de la réponse est 200

		mockmvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
				.param("firestation", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons
		// paramètres

		Mockito.verify(firestationService, Mockito.times(1))
				.getFireStationListPhone("1");

		// Test 3 : on envoie une requête GET avec en paramètre une station
		// qui n'existe pas
		// + on vérifie que le retour est vide

		mockmvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
				.param("firestation", "9"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));

	}

	@Test
	void getFireStationCoveragePerson() throws Exception {

		// Test 1 : on envoie une requête GET avec en paramètre un n° de station
		// valide
		// + on vérifie que le statut de la réponse est 200

		mockmvc.perform(MockMvcRequestBuilders.get("/firestation")
				.param("stationNumber", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons
		// paramètres

		Mockito.verify(firestationService, Mockito.times(1))
				.getFireStationCoveragePerson("1");

		// Test 3 : on envoie une requête GET avec en paramètre une station
		// qui n'existe pas
		// + on vérifie que le retour est vide

		mockmvc.perform(MockMvcRequestBuilders.get("/firestation")
				.param("stationNumber", "9"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void getFireStationPersonAtAddress() throws Exception {

		List<String> stations = Arrays.asList("1", "2");

		// Test 1 : on envoie une requête GET avec en paramètre des n° de
		// station
		// valide
		// + on vérifie que le statut de la réponse est 200

		mockmvc.perform(MockMvcRequestBuilders.get("/flood/stations")
				.param("stations", "1", "2"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Test 2 : on vérifie que le service a bien été appelé avec les bons
		// paramètres

		Mockito.verify(firestationService, Mockito.times(1))
				.getFireStationPersonAtAddress(stations);

		// Test 3 : on envoie une requête GET avec en paramètre une station
		// qui n'existe pas
		// + on vérifie que le retour est vide

		mockmvc.perform(MockMvcRequestBuilders.get("/flood/stations")
				.param("stations", "0"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	// Controleur "/firestationlist"

	@Test
	void getFirestation() throws Exception {

		// Etape 1 : on envoie une requête GET
		// + on vérifie que le statut de la réponse est 200

		mockmvc.perform(MockMvcRequestBuilders.get("/firestationlist"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Etape 2 : on vérifie que le service a bien été appelé avec les bons
		// paramètres

		Mockito.verify(firestationService, Mockito.times(1)).getFirestation();
	}

}
