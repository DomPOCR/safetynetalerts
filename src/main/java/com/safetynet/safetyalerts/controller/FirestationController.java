package com.safetynet.safetyalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.dto.FireStationListPerson;
import com.safetynet.safetyalerts.service.FirestationService;

//Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class FirestationController {

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private FirestationService fireStationService;

	@GetMapping(path = "firestation")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getFirestation() {

		List<String> firestation = fireStationService.getFirestation();

		return firestation;

	}

	@GetMapping(path = "fire")
	@ResponseStatus(HttpStatus.OK)
	public List<FireStationListPerson> getFireStationListPerson(
			@RequestParam String address) {

		List<FireStationListPerson> firestation = fireStationService
				.getFireStationListPerson(address);

		return firestation;

	}
}
