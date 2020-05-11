package com.safetynet.safetyalerts.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	@BeforeEach
	void initDb() throws Exception {
		dataRepository.init();
		// Modification Db non autorisée
		dataRepository.setCommit(false);
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
