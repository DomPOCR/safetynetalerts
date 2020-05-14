package com.safetynet.safetyalerts.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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
import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.repositories.DataRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonIT {

	// on utilise cet objet pour envoyer des requetes a notre serveur Spring
	// Boot
	@Autowired
	TestRestTemplate clientRest;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	DataRepository dataRepository;
	@Autowired
	PersonDao personDao;

	@BeforeEach
	void initDb() throws Exception {
		dataRepository.init();
		// Modification Db non autorisée
		dataRepository.setCommit(false);
	}

	@Test
	void createPerson() throws Exception {

		// On lit le body du json
		JsonNode newPerson = objectMapper.readTree(
				ClassLoader.getSystemResourceAsStream("newPerson.json"));

		// POST
		ResponseEntity<String> response = clientRest.postForEntity("/person",
				newPerson, String.class);

		// On vérifie le status de la réponse et le retour
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		ResponseEntity<String> responseGet = clientRest.getForEntity(
				"/personInfo?lastname=Boyd&firstname=TestIntegration",
				String.class);

		JsonNode json = objectMapper.readTree(responseGet.getBody());
		// on vérifie que le json est un tableau
		assertTrue(json.isArray());
		// on vérifie que le tableau est de taille 1
		assertEquals(1, json.size());
		// on recupere les infos de la personne
		JsonNode info = json.get(0);
		assertEquals("TestIntegration", info.get("firstName").asText());

	}
	@Test
	void updatePerson() throws Exception {

		// On lit le body du json
		JsonNode unePerson = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("updateInfoJohnBoyd.json"));
		HttpEntity<JsonNode> objEntity = new HttpEntity<JsonNode>(unePerson);

		// PUT
		ResponseEntity<String> response = clientRest.exchange("/person",
				HttpMethod.PUT, objEntity, String.class);

		// On vérifie le status de la réponse et le retour
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		List<Person> listPerson = personDao.listPersonInfo("Boyd", "John");
		assertEquals(1, listPerson.size());

		String json = objectMapper.writeValueAsString(listPerson.get(0));
		assertEquals(unePerson, objectMapper.readTree(json));

	}

	@Test
	void deletePerson() throws Exception {

		// on crée le body
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.set("firstName", TextNode.valueOf("Jacob"));
		objectNode.set("lastName", TextNode.valueOf("Boyd"));

		// on execute le delete avec la person
		HttpEntity<ObjectNode> objEntity = new HttpEntity<ObjectNode>(
				objectNode);
		ResponseEntity<String> response = clientRest.exchange("/person",
				HttpMethod.DELETE, objEntity, String.class);

		// on vérifie le status de la réponse
		assertEquals(HttpStatus.RESET_CONTENT, response.getStatusCode());

		// on récupere les infos person normalement vide
		ResponseEntity<String> responseGet = clientRest.getForEntity(
				"/personInfo?lastname=Boyd&firstname=Jacob", String.class);

		// on lit les infos de la person (response)
		assertEquals("[]", responseGet.getBody());

	}

	@Test
	void getCommunityEmail() throws Exception {

		// On envoie une requête GET avec en paramètre la ville Culver
		// + on vérifie que le statut de la réponse est 200
		ResponseEntity<String> response = clientRest
				.getForEntity("/communityEmail?city=Culver", String.class);
		// on vérifie le code retour 200
		assertEquals(HttpStatus.OK, response.getStatusCode());
		// renvoie un jsonnode qu'on attend
		JsonNode expectedJson = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("culvercommunityEmail.json"));
		// on vérifie le contenu
		assertEquals(expectedJson, objectMapper.readTree(response.getBody()));
	}

	@Test
	void getCommunityEmailInfo400() throws Exception {

		// On envoie une requête GET sans paramètre
		ResponseEntity<String> response = clientRest
				.getForEntity("/communityEmail?city=", String.class);
		// on vérifie le code retour 400
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		// renvoie un jsonnode qu'on attend

		JsonNode json = objectMapper.readTree(response.getBody());
		assertEquals("La ville ne peut être vide !!",
				json.get("message").asText());
	}

	@Test
	void getPersonInfoWithBoyd() throws Exception {

		ResponseEntity<String> response = clientRest
				.getForEntity("/personInfo?lastname=Boyd", String.class);

		// on vérifie le code retour 200
		assertEquals(HttpStatus.OK, response.getStatusCode());

		JsonNode expectedJson = objectMapper.readTree(
				ClassLoader.getSystemResourceAsStream("personInfoBoyd.json"));
		JsonNode json = objectMapper.readTree(response.getBody());

		assertEquals(expectedJson, json);
	}

	@Test
	void getPersonInfo400() throws Exception {

		ResponseEntity<String> response = clientRest
				.getForEntity("/personInfo?lastname=", String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());

		assertEquals("Le nom ne peut être vide !!",
				json.get("message").asText());

	}

	@Test
	void getChildAlertWithChild() throws Exception {

		ResponseEntity<String> response = clientRest.getForEntity(
				"/childAlert?address=1509 Culver St", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		JsonNode expectedJson = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("childAlert1509CulverSt.json"));

		JsonNode json = objectMapper.readTree(response.getBody());

		assertEquals(expectedJson, json);
	}

	@Test
	void getChildAlertWithChildInfo400() throws Exception {

		ResponseEntity<String> response = clientRest
				.getForEntity("/childAlert?address=", String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());

		assertEquals("L'adresse ne peut être vide !!",
				json.get("message").asText());
	}

	@Test
	void getChildAlertWithoutChild() throws Exception {

		ResponseEntity<String> response = clientRest.getForEntity(
				"/childAlert?address=489 Manchester St", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		JsonNode json = objectMapper.readTree(response.getBody());

		assertEquals(json.size(), 0);
	}
}
