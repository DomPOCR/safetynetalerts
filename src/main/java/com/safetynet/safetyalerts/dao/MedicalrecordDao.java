package com.safetynet.safetyalerts.dao;

import java.util.List;

import com.safetynet.safetyalerts.model.Medicalrecord;

public interface MedicalrecordDao {

	List<Medicalrecord> listMedicalrecord();
	List<Medicalrecord> listMedicalrecordInfo(String lastname,
			String firstname);

	Medicalrecord getMedicalrecordInfo(String lastname, String firstname);
	void createMedicalRecord(Medicalrecord medicalrecord);
}
