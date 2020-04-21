package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.FirestationDaoImpl;
import com.safetynet.safetyalerts.model.Firestation;

@Service
public class FirestationService {

	@Autowired
	FirestationDaoImpl firestationdao;

	public List<String> getFirestation() {

		List<Firestation> listFirestation = firestationdao.listFirestation();
		List<String> listFirestations = new ArrayList<String>();

		for (Firestation firestation : listFirestation) {
			listFirestations.add("Liste des casernes de pompiers du fichier  "
					+ " : n° " + firestation.getStation() + " à l'adresse : "
					+ firestation.getAddress());
		}
		return listFirestations;
	}
}
