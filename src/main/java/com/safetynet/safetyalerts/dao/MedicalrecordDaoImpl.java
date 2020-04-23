package com.safetynet.safetyalerts.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.model.Database;
import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.repositories.DataRepository;

@Service
public class MedicalrecordDaoImpl implements MedicalrecordDao {

	@Autowired
	private DataRepository dataRepository;

	@Override
	public List<Medicalrecord> listMedicalrecord() {

		return dataRepository.getDatabase().getMedicalrecords();
	}

	@Override
	public List<Medicalrecord> listMedicalrecordInfo(String lastname,
			String firstname) {

		List<Medicalrecord> ListMedicalrecord = new ArrayList<Medicalrecord>();

		Database db = dataRepository.getDatabase();
		for (Medicalrecord medicalrecord : db.getMedicalrecords()) {
			if ((medicalrecord.getLastName().equalsIgnoreCase(lastname))
					&& (medicalrecord.getFirstName()
							.equalsIgnoreCase(firstname))) {
				ListMedicalrecord.add(medicalrecord);
			}
		}
		return ListMedicalrecord;
	}

	@Override
	public Medicalrecord getMedicalrecordInfo(String lastname,
			String firstname) {

		Database db = dataRepository.getDatabase();

		for (Medicalrecord medicalrecord : db.getMedicalrecords()) {
			if ((medicalrecord.getLastName().equalsIgnoreCase(lastname))
					&& (medicalrecord.getFirstName()
							.equalsIgnoreCase(firstname))) {
				return medicalrecord;
			}
		}
		return null;
	}
}
