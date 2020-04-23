package com.safetynet.safetyalerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.exceptions.EndPointIntrouvableException;
import com.safetynet.safetyalerts.model.personInfo;
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
	// @ResponseStatus(HttpStatus.OK)
	public List<String> getCommunityEmail(@RequestParam String city) {

		endPoint = "communityEmail";

		logger.debug("Start");

		List<String> communityEmail = personService.getCommunityEmail(city);

		if (communityEmail.isEmpty()) {

			logger.info("Le EndPoint " + endPoint + " est vide");

			throw new EndPointIntrouvableException(
					"Le EndPoint " + endPoint + " est vide");
		}

		logger.info("GET:/communityEmail");
		logger.debug("End");
		return communityEmail;
	}

	@GetMapping(path = "person")
	public List<String> getPerson() {

		endPoint = "person";

		logger.debug("Start");

		List<String> person = personService.getPerson();

		if (person.isEmpty()) {

			logger.info("Le EndPoint " + endPoint + " est vide");

			throw new EndPointIntrouvableException(
					"Le EndPoint " + endPoint + " est vide");
		}

		logger.info("GET:/person" + person);
		logger.debug("End");

		return person;

	}
	@GetMapping(path = "personInfo")
	public List<personInfo> getPersonInfo(@RequestParam String lastname,
			@RequestParam String firstname) {

		endPoint = "personInfo";

		logger.debug("Start");

		List<personInfo> person = personService.getPersonInfo(lastname,
				firstname);

		if (person.isEmpty()) {

			logger.info("Le EndPoint " + endPoint + " est vide");

			throw new EndPointIntrouvableException(
					"Le EndPoint " + endPoint + " est vide");
		}

		logger.info("GET:/personInfo" + lastname + firstname);
		logger.debug("End");

		return person;

	}

}
