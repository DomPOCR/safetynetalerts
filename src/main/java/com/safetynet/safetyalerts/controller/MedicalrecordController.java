package com.safetynet.safetyalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.service.MedicalrecordService;

//Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class MedicalrecordController {

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private MedicalrecordService medicalrecordservice;

	// Création Dossier médical
	@PostMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.CREATED)
	public void createMedicalRecord(@RequestBody Medicalrecord medicalrecord) {

		medicalrecordservice.createMedicalRecord(medicalrecord);
	}

	@GetMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getmedicalRecord() {

		List<String> medicalrecord = medicalrecordservice.getMedicalrecord();

		return medicalrecord;

	}
}
