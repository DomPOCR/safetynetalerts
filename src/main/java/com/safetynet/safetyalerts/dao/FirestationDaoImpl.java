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

	// Création Firestation
	@Override
	public boolean createFirestation(Firestation firestation) {

		// Ajout de la nouvelle station en mémoire
		boolean result = dataRepository.getDatabase().getFirestations()
				.add(firestation);

		// Commit pour appliquer les changements sur le JSON
		dataRepository.commit();

		return result;
	}

	// MAJ FireStation
	@Override
	public boolean updateFirestation(Firestation firestation) {

		Database db = dataRepository.getDatabase();

		for (Firestation fs : db.getFirestations()) {

			if (fs.getAddress().contentEquals(firestation.getAddress())) {

				// Suppression de l'anciennne station
				boolean result = deleteFirestation(fs);
				// Création de la nouvelle station si ancienne supprimée
				if (result) {
					result = createFirestation(firestation);
					// Commit pour appliquer les changements sur le JSON
					dataRepository.commit();
					return result;
				}
			}
		}
		return false; // La station n'a pas été trouvée
	}

	// Suppression FireStation
	@Override
	public boolean deleteFirestation(Firestation firestation) {

		List<Firestation> firestationToDelete = new ArrayList<>();
		Database db = dataRepository.getDatabase();

		// Suppression par l'adresse uniquement
		if ((!"".equals(firestation.getAddress()))
				&& (firestation.getAddress()) != null) {
			for (Firestation fs : db.getFirestations()) {
				if (fs.getAddress().contentEquals(firestation.getAddress())) {
					firestationToDelete.add(fs);
				}
			}
		}
		// Suppression par le n° de station uniquement
		if ((!"".equals(firestation.getStation()))
				&& (firestation.getStation()) != null) {
			for (Firestation fs : db.getFirestations()) {
				if (fs.getStation().contentEquals(firestation.getStation())) {
					firestationToDelete.add(fs);
				}
			}
		}
		// Suppression par l'adresse et le n° de station
		if ((!"".equals(firestation.getStation()))
				&& (firestation.getStation()) != null
				&& (!"".equals(firestation.getAddress()))
				&& (firestation.getAddress()) != null) {
			for (Firestation fs : db.getFirestations()) {
				if ((fs.getAddress().contentEquals(firestation.getAddress()))
						&& fs.getStation()
								.contentEquals(firestation.getStation())) {
					firestationToDelete.add(fs);
				}
			}
		}

		boolean result = dataRepository.getDatabase().getFirestations()
				.removeAll(firestationToDelete);

		// Commit pour appliquer les changements sur le JSON
		dataRepository.commit();
		return result;
	}

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
