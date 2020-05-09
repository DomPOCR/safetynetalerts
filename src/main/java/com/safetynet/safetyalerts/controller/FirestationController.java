package com.safetynet.safetyalerts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.dto.FireStationCoveragePerson;
import com.safetynet.safetyalerts.dto.FireStationListPerson;
import com.safetynet.safetyalerts.dto.FireStationListPhone;
import com.safetynet.safetyalerts.dto.FireStationPersonAtAddress;
import com.safetynet.safetyalerts.exceptions.InvalidArgumentException;
import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.service.FirestationService;

//Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class FirestationController {

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private FirestationService fireStationService;

	// Vérification des données en entrée (Création)
	public void checkInputFirestation(Firestation firestation) {

		if (("".equals(firestation.getStation())
				|| firestation.getStation() == null)
				|| ("".equals(firestation.getAddress())
						|| firestation.getAddress() == null)) {
			throw new InvalidArgumentException(
					"Le numéro OU l'adresse de la station sont obligatoires !!");
		}
	}

	// Vérification des données en entrée (Suppression)
	public void checkInputFirestationDelete(Firestation firestation) {

		if (("".equals(firestation.getStation())
				|| firestation.getStation() == null)
				&& ("".equals(firestation.getAddress())
						|| firestation.getAddress() == null)) {
			throw new InvalidArgumentException(
					"Le numéro OU l'adresse de la station sont obligatoires !!");
		}
	}

	// Création Firestation
	@PostMapping(path = "firestation")
	@ResponseStatus(HttpStatus.CREATED)
	public void createFirestation(@RequestBody @Valid Firestation firestation) {

		// checkInputFirestation(firestation);
		fireStationService.createFirestation(firestation);
	}

	// MAJ Firestation
	@PutMapping(path = "firestation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateFirestation(@RequestBody @Valid Firestation firestation) {

		// checkInputFirestation(firestation);
		fireStationService.updateFirestation(firestation);
	}
	// Suppression FireStation
	@DeleteMapping(path = "firestation")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deleteFirestation(@RequestBody @Valid Firestation firestation) {

		// checkInputFirestationDelete(firestation);
		fireStationService.deleteFirestation(firestation);
	}

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
