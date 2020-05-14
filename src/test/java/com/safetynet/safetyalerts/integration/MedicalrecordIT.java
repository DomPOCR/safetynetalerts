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
	void updateMedicalrecord() throws Exception {

		// on lit le bofy du json
		JsonNode unMedicalrecord = objectMapper.readTree(ClassLoader
				.getSystemResourceAsStream("updateMedicalrecord.json"));
		HttpEntity<JsonNode> objEntity = new HttpEntity<JsonNode>(
				unMedicalrecord);
		ResponseEntity<String> response = clientRest.exchange("/medicalRecord",
				HttpMethod.PUT, objEntity, String.class);

		// on check le status de la réponse
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		Medicalrecord medicalrecord = medicalrecordDao
				.getMedicalrecordInfo("Boyd", "John");
		assertNotNull(medicalrecord);

		String json = objectMapper.writeValueAsString(medicalrecord);
		assertEquals(unMedicalrecord, objectMapper.readTree(json));

	}
}
