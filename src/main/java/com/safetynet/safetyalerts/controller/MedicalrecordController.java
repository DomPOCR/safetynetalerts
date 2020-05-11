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

import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.service.MedicalrecordService;

//Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class MedicalrecordController {

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private MedicalrecordService medicalrecordService;

	// Création Dossier médical
	@PostMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.CREATED)
	public void createMedicalRecord(
			@RequestBody @Valid Medicalrecord medicalrecord) {

		medicalrecordService.createMedicalrecord(medicalrecord);
	}

	// MAJ Dossier médical d'une personne
	@PutMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateMedicalRecord(
			@RequestBody @Valid Medicalrecord medicalrecord) {

		medicalrecordService.updateMedicalrecord(medicalrecord);
	}

	// SuppressionDossier médical d'une personne

	@DeleteMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deleteMedicalRecord(
			@RequestBody @Valid Medicalrecord medicalrecord) {

		medicalrecordService.deleteMedicalrecord(medicalrecord);
	}

	@GetMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getmedicalRecord() {

		List<String> medicalrecord = medicalrecordService.getMedicalrecord();

		return medicalrecord;

	}

	@GetMapping(path = "medicalRecordInfo")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getMedicalrecordInfo(
			@RequestParam @Valid String lastname,
			@RequestParam(required = false) String firstname) {

		List<String> medicalrecordInfo = medicalrecordService
				.getMedicalrecordInfo(lastname, firstname);

		return medicalrecordInfo;

	}
}
