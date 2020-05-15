package com.safetynet.safetyalerts.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.safetynet.safetyalerts.dao.FirestationDao;
import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.repositories.DataRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FirestationIT {

	// on utilise cet objet pour envoyer des requetes a notre serveur Spring
	// Boot
	@Autowired
	TestRestTemplate clientRest;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	DataRepository dataRepository;
	@Autowired
	FirestationDao firestationDao;

	@BeforeEach
	void initDb() throws Exception {
		dataRepository.init();
		// Modification Db non autorisée
		dataRepository.setCommit(false);
	}

	@Test
	void createFirestation() throws Exception {

		// On lit le body du json
		JsonNode newFirestation = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("newFirestation999.json"));

		// POST
		ResponseEntity<String> response = clientRest
				.postForEntity("/firestation", newFirestation, String.class);

		// On vérifie le status de la réponse et le retour
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		ResponseEntity<String> responseGet = clientRest
				.getForEntity("/firestation?stationNumber=999", String.class);

		JsonNode json = objectMapper.readTree(responseGet.getBody());
		// on vérifie que le json est un tableau
		assertTrue(json.isArray());
		// on vérifie que le tableau est de taille 1
		assertEquals(1, json.size());
		// on recupere les infos de la personne
		JsonNode info = json.get(0);
		assertEquals("999", info.get("station").asText());

	}

	@Test
	void updateFirestation() throws Exception {

		// on lit le body du json
		JsonNode updateFirestation = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("updateFirestation.json"));
		// PUT
		HttpEntity<JsonNode> objEntity = new HttpEntity<JsonNode>(
				updateFirestation);
		ResponseEntity<String> response = clientRest.exchange("/firestation",
				HttpMethod.PUT, objEntity, String.class);

		// on vérifie le status de la réponse
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		Firestation firestation = firestationDao
				.fireStationAtAddress("1509 Culver St");

		// on vérifie le contenu
		assertNotNull(firestation);

		String json = objectMapper.writeValueAsString(firestation);
		assertEquals(updateFirestation, objectMapper.readTree(json));
	}

	@Test
	void deleteFirestation() throws Exception {

		// on crée le body
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.set("station", TextNode.valueOf("1"));

		// DELETE
		HttpEntity<ObjectNode> objEntity = new HttpEntity<ObjectNode>(
				objectNode);
		ResponseEntity<String> response = clientRest.exchange("/firestation",
				HttpMethod.DELETE, objEntity, String.class);

		// on vérifie le status de la réponse
		assertEquals(HttpStatus.RESET_CONTENT, response.getStatusCode());

		// on récupere les infos firestation normalement vide
		ResponseEntity<String> responseGet = clientRest
				.getForEntity("/firestation?stationNumber=1", String.class);

		// on lit les infos de la firestation (response)
		assertEquals("[]", responseGet.getBody());

	}

	@Test
	void getFireStationListPerson() throws Exception {

		// On envoie une requête GET avec en paramètre le n° de station
		// + on vérifie que le statut de la réponse est 200
		ResponseEntity<String> response = clientRest
				.getForEntity("/firestation?stationNumber=1", String.class);

		// on vérifie le code retour 200
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// renvoie un jsonnode qu'on attend
		JsonNode expectedJson = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("station1ListPerson.json"));

		// on vérifie le contenu
		assertEquals(expectedJson, objectMapper.readTree(response.getBody()));
	}

	@Test
	void getFireStationListPersonInfo400() throws Exception {

		// On envoie une requête GET sans paramètre

		ResponseEntity<String> response = clientRest
				.getForEntity("/firestation?stationNumber=", String.class);
		// on vérifie le code retour 400
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		// renvoie un jsonnode qu'on attend

		JsonNode json = objectMapper.readTree(response.getBody());
		assertEquals("Le numéro de station ne peut être vide !!",
				json.get("message").asText());
	}

	@Test
	void getFireStationListPhone() throws Exception {

		// On envoie une requête GET avec en paramètre le n° de station
		// + on vérifie que le statut de la réponse est 200
		ResponseEntity<String> response = clientRest
				.getForEntity("/phoneAlert?firestation=1", String.class);

		// on vérifie le code retour 200
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// renvoie un jsonnode qu'on attend
		JsonNode expectedJson = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("station1phoneAlert.json"));

		// on vérifie le contenu
		assertEquals(expectedJson, objectMapper.readTree(response.getBody()));
	}

	@Test
	void getFireStationListPhoneInfo400() throws Exception {

		// On envoie une requête GET sans paramètre

		ResponseEntity<String> response = clientRest
				.getForEntity("/phoneAlert?firestation=", String.class);

		// on vérifie le code retour 400
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		// renvoie un jsonnode qu'on attend

		JsonNode json = objectMapper.readTree(response.getBody());
		assertEquals("Le numéro de station ne peut être vide !!",
				json.get("message").asText());
	}

	@Test
	void getFireStationCoveragePerson() throws Exception {

		// On envoie une requête GET avec en paramètre le n° de station
		// + on vérifie que le statut de la réponse est 200
		ResponseEntity<String> response = clientRest
				.getForEntity("/firestation?stationNumber=1", String.class);

		// on vérifie le code retour 200
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// renvoie un jsonnode qu'on attend
		JsonNode expectedJson = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("station1CoveragePerson.json"));

		// on vérifie le contenu
		assertEquals(expectedJson, objectMapper.readTree(response.getBody()));
	}

	@Test
	void getFireStationCoveragePersonInfo400() throws Exception {

		// On envoie une requête GET sans paramètre

		ResponseEntity<String> response = clientRest
				.getForEntity("/firestation?stationNumber=", String.class);

		// on vérifie le code retour 400
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		// renvoie un jsonnode qu'on attend

		JsonNode json = objectMapper.readTree(response.getBody());
		assertEquals("Le numéro de station ne peut être vide !!",
				json.get("message").asText());
	}

	@Test
	void getFireStationPersonAtAddress() throws Exception {

		// On envoie une requête GET avec en paramètre le n° de station
		// + on vérifie que le statut de la réponse est 200
		ResponseEntity<String> response = clientRest
				.getForEntity("/flood/stations?stations=1,2", String.class);

		// on vérifie le code retour 200
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// renvoie un jsonnode qu'on attend
		JsonNode expectedJson = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("station1&2PersonAtAddress.json"));

		// on vérifie le contenu
		assertEquals(expectedJson, objectMapper.readTree(response.getBody()));
	}

	@Test
	void getFireStationPersonAtAddressInfo400() throws Exception {

		// On envoie une requête GET sans paramètre

		ResponseEntity<String> response = clientRest
				.getForEntity("/flood/stations?stations=", String.class);

		// on vérifie le code retour 400
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		// renvoie un jsonnode qu'on attend

		JsonNode json = objectMapper.readTree(response.getBody());
		assertEquals("Le numéro de station ne peut être vide !!",
				json.get("message").asText());
	}
}
