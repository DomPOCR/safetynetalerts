package com.safetynet.safetyalerts.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.service.PersonService;

// Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class ReadResourceController {

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private PersonService personService;

	// http://localhost:8080/communityEmail?city=<city>

	@GetMapping(path = "communityEmail")
	public Collection<String> getCommunityEmail(@RequestParam String city) {

		return personService.getCommunityEmail(city);

	}
}
