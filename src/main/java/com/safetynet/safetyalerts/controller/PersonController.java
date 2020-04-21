package com.safetynet.safetyalerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.exceptions.EndPointIntrouvableException;
import com.safetynet.safetyalerts.service.PersonService;

// Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class PersonController {

	// Pour le log4j2
	private static final Logger logger = LogManager
			.getLogger(PersonController.class);

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private PersonService personService;
	public String endPoint;

	@GetMapping(path = "communityEmail")
	public List<String> getCommunityEmail(@RequestParam String city) {

		logger.info("GET:/communityEmail");

		return personService.getCommunityEmail(city);
	}

	@GetMapping(path = "person")
	public List<String> getPerson() {

		endPoint = "person";
		logger.info("GET:/person");

		List<String> person = personService.getPerson();
		if (person.isEmpty()) {

			logger.info("Le EndPoint " + endPoint + " est vide");

			throw new EndPointIntrouvableException(
					"Le EndPoint " + endPoint + " est vide");
		}
		return personService.getPerson();

	}

}
