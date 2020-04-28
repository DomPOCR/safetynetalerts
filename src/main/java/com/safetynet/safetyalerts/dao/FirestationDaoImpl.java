package com.safetynet.safetyalerts.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.model.Database;
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

	@Override
	public Firestation fireStationAtAddress(String Address) {

		Database db = dataRepository.getDatabase();

		for (Firestation firestation : db.getFirestations()) {
			if (firestation.getAddress().equalsIgnoreCase(Address)) {
				return firestation;
			}
		}
		return null;
	}

	@Override
	public List<Firestation> fireStationAdressbyStation(String Station) {

		List<Firestation> ListFirestation = new ArrayList<Firestation>();

		Database db = dataRepository.getDatabase();

		for (Firestation firestation : db.getFirestations()) {
			if (firestation.getStation().equals(Station)) {
				ListFirestation.add(firestation);
			}
		}
		return ListFirestation;

	}
}
