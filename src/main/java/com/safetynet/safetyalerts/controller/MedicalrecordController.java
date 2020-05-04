package com.safetynet.safetyalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.exceptions.IllegalArgumentException;
import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.service.MedicalrecordService;

//Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class MedicalrecordController {

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private MedicalrecordService medicalrecordService;

	// Vérification des données en entrée
	public void checkInputMedicalrecord(Medicalrecord medicalrecord) {

		if ("".equals(medicalrecord.getFirstName())
				|| medicalrecord.getFirstName() == null
				|| "".equals(medicalrecord.getLastName())
				|| medicalrecord.getLastName() == null) {
			throw new IllegalArgumentException(
					"Le nom ET le prénom sont obligatoires !!");
		}
	}

	// Création Dossier médical
	@PostMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.CREATED)
	public void createMedicalRecord(@RequestBody Medicalrecord medicalrecord) {

		checkInputMedicalrecord(medicalrecord);
		medicalrecordService.createMedicalRecord(medicalrecord);
	}

	// MAJ Dossier médical d'une personne
	@PutMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateMedicalRecord(@RequestBody Medicalrecord medicalrecord) {

		checkInputMedicalrecord(medicalrecord);
		medicalrecordService.updateMedicalRecord(medicalrecord);
	}

	// SuppressionDossier médical d'une personne

	@DeleteMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deleteMedicalRecord(@RequestBody Medicalrecord medicalrecord) {

		checkInputMedicalrecord(medicalrecord);
		medicalrecordService.deleteMedicalRecord(medicalrecord);
	}

	@GetMapping(path = "medicalRecord")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getmedicalRecord() {

		List<String> medicalrecord = medicalrecordService.getMedicalrecord();

		return medicalrecord;

	}
}
