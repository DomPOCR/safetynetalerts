package com.safetynet.safetyalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.dto.FireStationCoveragePerson;
import com.safetynet.safetyalerts.dto.FireStationListPerson;
import com.safetynet.safetyalerts.dto.FireStationListPhone;
import com.safetynet.safetyalerts.dto.FireStationPersonAtAddress;
import com.safetynet.safetyalerts.service.FirestationService;

//Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class FirestationController {

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private FirestationService fireStationService;

	@GetMapping(path = "firestationlist")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getFirestation() {

		List<String> firestationInfo = fireStationService.getFirestation();

		return firestationInfo;

	}

	@GetMapping(path = "fire")
	@ResponseStatus(HttpStatus.OK)
	public List<FireStationListPerson> getFireStationListPerson(
			@RequestParam String address) {

		List<FireStationListPerson> firestationInfo = fireStationService
				.getFireStationListPerson(address);

		return firestationInfo;

	}

	@GetMapping(path = "phoneAlert")
	@ResponseStatus(HttpStatus.OK)
	public List<FireStationListPhone> getFireStationListPhone(
			@RequestParam String firestation) {

		List<FireStationListPhone> firestationInfo = fireStationService
				.getFireStationListPhone(firestation);

		return firestationInfo;

	}

	@GetMapping(path = "firestation")
	@ResponseStatus(HttpStatus.OK)
	public List<FireStationCoveragePerson> getFireStationCoveragePerson(
			@RequestParam String stationNumber) {

		List<FireStationCoveragePerson> firestationInfo = fireStationService
				.getFireStationCoveragePerson(stationNumber);

		return firestationInfo;

	}

	@GetMapping(path = "flood/stations")
	@ResponseStatus(HttpStatus.OK)
	public List<FireStationPersonAtAddress> getFireStationPersonAtAddress(
			@RequestParam List<String> stations) {

		List<FireStationPersonAtAddress> firestationInfo = fireStationService
				.getFireStationPersonAtAddress(stations);

		return firestationInfo;

	}
}
