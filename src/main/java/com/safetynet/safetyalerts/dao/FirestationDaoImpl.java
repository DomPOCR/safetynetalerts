package com.safetynet.safetyalerts.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.repositories.DataRepository;

@Service
public class FirestationDaoImpl implements FirestationDao {

	@Autowired
	private DataRepository dataRepository;

	@Override
	public List<Firestation> listFirestation() {

		return dataRepository.getDatabase().getFirestations();
	}

}
