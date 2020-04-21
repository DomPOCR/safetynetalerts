package com.safetynet.safetyalerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.exceptions.EndPointIntrouvableException;
import com.safetynet.safetyalerts.service.FirestationService;

//Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class FirestationController {

	// Pour le log4j2
	private static final Logger logger = LogManager
			.getLogger(FirestationController.class);

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private FirestationService fireStationService;
	public String endPoint;

	@GetMapping(path = "firestation")
	public List<String> getFirestation() {

		endPoint = "firestation";
		logger.info("GET:/firestation");

		List<String> firestation = fireStationService.getFirestation();
		if (firestation.isEmpty()) {

			logger.info("Le EndPoint " + endPoint + " est vide");

			throw new EndPointIntrouvableException(
					"Le EndPoint " + endPoint + " est vide");
		}

		return fireStationService.getFirestation();

	}
}
