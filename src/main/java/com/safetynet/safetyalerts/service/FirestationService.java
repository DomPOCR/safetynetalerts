package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.FirestationDao;
import com.safetynet.safetyalerts.dao.MedicalrecordDao;
import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.dto.FireStationListPerson;
import com.safetynet.safetyalerts.dto.FireStationListPhone;
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

	// Cette url doit retourner la liste des habitants vivant à l’adresse
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

	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	// Cette url doit retourner une liste des numéros de téléphone des résidents
	// desservis par la caserne de
	// pompiers. Nous l'utiliserons pour envoyer des messages texte d'urgence à
	// des foyers spécifiques

	public List<FireStationListPhone> getFireStationListPhone(String station) {

		List<Firestation> listFireStationAddress = firestationdao
				.FireStationAdressbyStation(station);

		List<FireStationListPhone> fireStationListPhone = new ArrayList<>();

		Set<String> listPhone = new HashSet<String>();

		for (Firestation firestation : listFireStationAddress) {

			FireStationListPhone fireStationInfo = new FireStationListPhone();

			List<Person> personByAddress = persondao
					.listPersonByAddress(firestation.getAddress());

			if (personByAddress != null) {

				for (Person person : personByAddress) {

					listPhone.add(person.getPhone());
				}
				fireStationInfo.setStation(station);
				fireStationInfo.setResidentsPhone(listPhone);
			}
			fireStationListPhone.add(fireStationInfo);
		}
		return fireStationListPhone;
	}

}
