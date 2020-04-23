package com.safetynet.safetyalerts.controller;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.dto.PersonInfo;
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
	public Collection<String> getCommunityEmail(@RequestParam String city) {

		Collection<String> communityEmail = personService
				.getCommunityEmail(city);

		return communityEmail;
	}

	@GetMapping(path = "person")
	public List<String> getPerson() {

		List<String> person = personService.getPerson();

		return person;
	}

	@GetMapping(path = "personInfo")
	public List<PersonInfo> getPersonInfo(@RequestParam String lastname,
			@RequestParam(required = false) String firstname) {

		List<PersonInfo> person = personService.getPersonInfo(lastname,
				firstname);

		return person;
	}

	// @GetMapping(path = "fire")
	// public list<Fire> GetFireAdress(@RequestParam String adress) {

	// }
}
