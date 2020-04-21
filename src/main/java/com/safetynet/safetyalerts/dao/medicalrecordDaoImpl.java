package com.safetynet.safetyalerts.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.repositories.DataRepository;

@Service
public class medicalrecordDaoImpl implements medicalrecordDao {

	@Autowired
	private DataRepository dataRepository;

	@Override
	public List<Medicalrecord> listMedicalrecord() {

		return dataRepository.getDatabase().getMedicalrecords();
	}

}
