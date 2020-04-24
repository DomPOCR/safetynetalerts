package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.FirestationDao;
import com.safetynet.safetyalerts.dao.MedicalrecordDao;
import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.dto.FireStationListPerson;
import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.utility.CalculateAge;

@Service
public class FirestationService {

	@Autowired
	FirestationDao firestationdao;
	@Autowired
	PersonDao persondao;
	@Autowired
	MedicalrecordDao medicalrecorddao;

	public List<String> getFirestation() {

		List<Firestation> listFirestation = firestationdao.listFirestation();
		List<String> listFirestations = new ArrayList<String>();

		for (Firestation firestation : listFirestation) {
			listFirestations.add("Liste des casernes de pompiers du fichier  "
					+ " : n° " + firestation.getStation() + " à l'Addresse : "
					+ firestation.getAddress());
		}
		return listFirestations;
	}

	// http://localhost:8080/fire?address=<address>

	// Cette url doit retourner la liste des habitants vivant à l’Addresse
	// donnée
	// ainsi que le numéro de la caserne
	// de pompiers la desservant. La liste doit inclure le nom, le numéro de
	// téléphone, l'âge et les antécédents
	// médicaux (médicaments, posologie et allergies) de chaque personne.

	public List<FireStationListPerson> getFireStationListPerson(
			String address) {

		List<Person> personByAddress = persondao.listPersonByAddress(address);

		List<FireStationListPerson> fireStationListPerson = new ArrayList<>();

		for (Person person : personByAddress) {

			FireStationListPerson fireStationInfo = new FireStationListPerson();

			fireStationInfo.setLastName(person.getLastName());
			fireStationInfo.setFirstName(person.getFirstName());
			fireStationInfo.setAddress(person.getAddress());
			fireStationInfo.setPhone(person.getPhone());

			Medicalrecord personMedicalRecord = medicalrecorddao
					.getMedicalrecordInfo(person.getLastName(),
							person.getFirstName());

			if (personMedicalRecord != null) {
				fireStationInfo.setAge(CalculateAge
						.personBirthDate(personMedicalRecord.getBirthdate()));
				fireStationInfo
						.setMedications(personMedicalRecord.getMedications());
				fireStationInfo
						.setAllergies(personMedicalRecord.getAllergies());
			}

			Firestation firestation = firestationdao
					.FireStationAtAddress(address);

			if (firestation != null) {
				fireStationInfo.setStation(firestation.getStation());
			}
			fireStationListPerson.add(fireStationInfo);
		}

		return fireStationListPerson;

	}
}
