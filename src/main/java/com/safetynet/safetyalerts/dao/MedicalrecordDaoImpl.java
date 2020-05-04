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

	// Création dossier médical
	@Override
	public boolean createMedicalrecord(Medicalrecord medicalrecord) {
		// Ajout de la nouvelle personne en mémoire
		dataRepository.getDatabase().getMedicalrecords().add(medicalrecord);

		// Commit pour appliquer les changements sur le JSON
		dataRepository.commit();
		return true;
	}

	// MAJ dossier médical
	@Override
	public boolean updateMedicalrecord(Medicalrecord medicalrecord) {

		Database db = dataRepository.getDatabase();

		for (Medicalrecord fs : db.getMedicalrecords()) {

			if (fs.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
					&& fs.getLastName()
							.equalsIgnoreCase(medicalrecord.getLastName())) {

				// Suppression de l'ancien dossier médical
				boolean result = deleteMedicalrecord(fs);
				// Création du nouveau dossier médical si ancien supprimé
				if (result) {
					result = createMedicalrecord(medicalrecord);
					// Commit pour appliquer les changements sur le JSON
					dataRepository.commit();
					return result;
				}
			}
		}
		return false; // Le dossier médical n'a pas été trouvé
	}

	// Suppression dossier médical
	@Override
	public boolean deleteMedicalrecord(Medicalrecord medicalrecord) {

		// Suppression de la personne en mémoire
		boolean result = dataRepository.getDatabase().getMedicalrecords()
				.remove(medicalrecord);

		// Commit pour appliquer les changements sur le JSON
		dataRepository.commit();
		return result;
	}

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
