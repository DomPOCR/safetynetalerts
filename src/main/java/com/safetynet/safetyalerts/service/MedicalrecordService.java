package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.MedicalrecordDaoImpl;
import com.safetynet.safetyalerts.model.Medicalrecord;

@Service
public class MedicalrecordService {

	@Autowired
	MedicalrecordDaoImpl medicalrecorddao;

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
