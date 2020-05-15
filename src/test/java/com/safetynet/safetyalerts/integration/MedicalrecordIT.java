package com.safetynet.safetyalerts.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.safetynet.safetyalerts.dao.MedicalrecordDao;
import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.repositories.DataRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MedicalrecordIT {

	// on utilise cet objet pour envoyer des requetes a notre serveur Spring
	// Boot
	@Autowired
	TestRestTemplate clientRest;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	DataRepository dataRepository;
	@Autowired
	MedicalrecordDao medicalrecordDao;

	@BeforeEach
	void initDb() throws Exception {
		dataRepository.init();
		// Modification Db non autorisée
		dataRepository.setCommit(false);
	}

	@Test
	void createMedicalrecord() throws Exception {

		// on lit le body du json
		JsonNode newMedicalrecord = objectMapper.readTree(
				ClassLoader.getSystemResourceAsStream("newMedicalrecord.json"));

		// POST
		ResponseEntity<String> response = clientRest.postForEntity(
				"/medicalRecord", newMedicalrecord, String.class);

		// on vérifie le status de la réponse
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Medicalrecord medicalrecord = medicalrecordDao.getMedicalrecordInfo("P",
				"Dom");

		// on vérifie le contenu
		assertNotNull(medicalrecord);

		String json = objectMapper.writeValueAsString(medicalrecord);
		assertEquals(newMedicalrecord, objectMapper.readTree(json));
	}

	@Test
	void updateMedicalrecord() throws Exception {

		// on lit le body du json
		JsonNode updateMedicalrecord = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("updateMedicalrecord.json"));

		// PUT
		HttpEntity<JsonNode> objEntity = new HttpEntity<JsonNode>(
				updateMedicalrecord);
		ResponseEntity<String> response = clientRest.exchange("/medicalRecord",
				HttpMethod.PUT, objEntity, String.class);

		// on vérifie le status de la réponse
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		Medicalrecord medicalrecord = medicalrecordDao
				.getMedicalrecordInfo("Boyd", "John");

		// on vérifie le contenu
		assertNotNull(medicalrecord);

		String json = objectMapper.writeValueAsString(medicalrecord);
		assertEquals(updateMedicalrecord, objectMapper.readTree(json));
	}

	@Test
	void deleteteMedicalrecord() throws Exception {

		// on crée le body
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.set("firstName", TextNode.valueOf("John"));
		objectNode.set("lastName", TextNode.valueOf("Boyd"));

		// DELETE
		HttpEntity<ObjectNode> objEntity = new HttpEntity<ObjectNode>(
				objectNode);
		ResponseEntity<String> response = clientRest.exchange("/medicalRecord",
				HttpMethod.DELETE, objEntity, String.class);

		// on vérifie le status de la réponse
		assertEquals(HttpStatus.RESET_CONTENT, response.getStatusCode());

	}
}
