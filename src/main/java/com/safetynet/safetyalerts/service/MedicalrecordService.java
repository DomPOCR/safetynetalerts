package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.MedicalrecordDao;
import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
import com.safetynet.safetyalerts.exceptions.DataNotFoundException;
import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.model.Person;

@Service
public class MedicalrecordService {

	@Autowired
	MedicalrecordDao medicalrecorddao;
	@Autowired
	PersonDao persondao;

	// Création dossier médical
	public void createMedicalRecord(Medicalrecord medicalrecord) {

		// Vérification que la personne existe dans la DAO (nom + prénom)
		// Et quelle n'a pas de dossier médical

		List<Person> personInfo = persondao.listPersonInfo(
				medicalrecord.getLastName(), medicalrecord.getFirstName());
		String mess = null;

		if ((!medicalrecorddao.listMedicalrecord().contains(medicalrecord))
				&& (personInfo != null) && (!personInfo.isEmpty())) {
			medicalrecorddao.createMedicalRecord(medicalrecord);

		} else {
			if (medicalrecorddao.listMedicalrecord().contains(medicalrecord)) {
				mess = "Le dossier médical de " + medicalrecord.getFirstName()
						+ " " + medicalrecord.getLastName()
						+ " existe déjà !! ";
			}
			if ((personInfo == null) || (personInfo.isEmpty())) {
				mess = "La personne " + medicalrecord.getFirstName() + " "
						+ medicalrecord.getLastName() + " n'existe pas !!";
			}
			throw new DataAlreadyExistException(mess);
		}

	}

	// MAJ dossier médical (à partir du nom / prénom)
	public void updateMedicalRecord(Medicalrecord medicalrecord) {

		// Vérification que la personne existe dans la DAO
		if (!medicalrecorddao.updateMedicalRecord(medicalrecord)) {

			throw new DataNotFoundException("La personne " + medicalrecord.getLastName()
					+ " " + medicalrecord.getFirstName() + " n'existe pas !!");
		}
	}

	// Suppression dossier médical (à partir du nom / prénom)
	public void deleteMedicalRecord(Medicalrecord medicalrecord) {

		// Vérification que le dossier médical existe dans la DAO (nom + prénom)
		if (!medicalrecorddao.deleteMedicalRecord(medicalrecord)) {
			throw new DataNotFoundException("La personne " + medicalrecord.getLastName()
					+ " " + medicalrecord.getFirstName()
					+ " n'a pas de dossier médical !!");

		}
	}

	public List<String> getMedicalrecord() {

		List<Medicalrecord> listMedicalrecord = medicalrecorddao
				.listMedicalrecord();
		List<String> listMedicalrecords = new ArrayList<String>();

		for (Medicalrecord Medicalrecord : listMedicalrecord) {
			listMedicalrecords.add(Medicalrecord.toString());
		}
		Collections.sort(listMedicalrecords);
		return listMedicalrecords;
	}

	public List<String> getMedicalrecordInfo(String lastname,
			String firstname) {

		List<Medicalrecord> listMedicalrecord = medicalrecorddao
				.listMedicalrecord();
		List<String> listMedicalrecordInfo = new ArrayList<String>();

		for (Medicalrecord Medicalrecord : listMedicalrecord) {
			listMedicalrecordInfo.add(
					Medicalrecord.getFirstName() + Medicalrecord.getLastName()
							+ Medicalrecord.getMedications()
							+ Medicalrecord.getAllergies());
		}
		return listMedicalrecordInfo;
	}
}
