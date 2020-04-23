package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.medicalrecordDaoImpl;
import com.safetynet.safetyalerts.model.Medicalrecord;

@Service
public class MedicalrecordService {

	@Autowired
	medicalrecordDaoImpl medicalrecorddao;

	public List<String> getMedicalrecord() {

		List<Medicalrecord> listMedicalrecord = medicalrecorddao
				.listMedicalrecord();
		List<String> listMedicalrecords = new ArrayList<String>();

		for (Medicalrecord Medicalrecord : listMedicalrecord) {
			listMedicalrecords
					.add("Liste des informations médicales du fichier  " + " : "
							+ Medicalrecord.getFirstName() + " "
							+ Medicalrecord.getLastName() + " "
							+ Medicalrecord.getAllergies());
		}
		return listMedicalrecords;
	}

	public List<String> getMedicalrecordInfo(String lastname,
			String firstname) {

		List<Medicalrecord> listMedicalrecord = medicalrecorddao
				.listMedicalrecord();
		List<String> listMedicalrecordInfo = new ArrayList<String>();

		for (Medicalrecord Medicalrecord : listMedicalrecord) {
			listMedicalrecordInfo
					.add("Liste des informations médicales du fichier  " + " : "
							+ Medicalrecord.getFirstName() + " "
							+ Medicalrecord.getLastName() + " "
							+ Medicalrecord.getAllergies());
		}
		return listMedicalrecordInfo;
	}
}
