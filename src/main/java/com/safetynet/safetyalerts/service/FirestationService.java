package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.FirestationDao;
import com.safetynet.safetyalerts.dao.MedicalrecordDao;
import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.dto.FireStationCoveragePerson;
import com.safetynet.safetyalerts.dto.FireStationListPerson;
import com.safetynet.safetyalerts.dto.FireStationListPhone;
import com.safetynet.safetyalerts.dto.FireStationPersonAtAddress;
import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.dto.PersonList;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExist;
import com.safetynet.safetyalerts.exceptions.DataNotFound;
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

	// Création station
	public void createFirestation(Firestation firestation) {

		// Vérification que la station n'existe pas dans la DAO
		if (!firestationdao.listFirestation().contains(firestation)) {
			firestationdao.createFirestation(firestation);
		} else {
			throw new DataAlreadyExist(
					"La station " + firestation.toString() + " existe déjà !!");
		}
	}

	// MAJ numéro station à partir d'une adresse (= suppression et création)
	public void UpdateFirestation(Firestation firestation) {

		// Vérification que la station existe dans la DAO
		if (!firestationdao.updateFirestation(firestation)) {

			throw new DataNotFound("La station " + firestation.toString()
					+ " n'existe pas !!");
		}
	}
	// Suppression station
	public void deleteFirestation(Firestation firestation) {

		// Vérification que la station existe dans la DAO
		if (!firestationdao.deleteFirestation(firestation)) {
			throw new DataNotFound("La station " + firestation.toString()
					+ " n'existe pas !!");
		}
	}
	public List<String> getFirestation() {

		List<Firestation> listFirestation = firestationdao.listFirestation();
		List<String> listFirestations = new ArrayList<String>();

		for (Firestation firestation : listFirestation) {
			listFirestations.add(firestation.toString());
		}
		Collections.sort(listFirestations);
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
					.fireStationAtAddress(address);

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
				.fireStationAdressbyStation(station);

		List<FireStationListPhone> fireStationListPhone = new ArrayList<>();

		Set<String> listPhone = new HashSet<String>();

		FireStationListPhone fireStationInfo = new FireStationListPhone();

		for (Firestation firestation : listFireStationAddress) {

			List<Person> personByAddress = persondao
					.listPersonByAddress(firestation.getAddress());

			if (personByAddress != null) {

				for (Person person : personByAddress) {

					listPhone.add(person.getPhone());
				}
			}
			fireStationInfo.setStation(station);
			fireStationInfo.setResidentsPhone(listPhone);
		}
		fireStationListPhone.add(fireStationInfo);
		return fireStationListPhone;
	}

	// http://localhost:8080/firestation?stationNumber=<station_number>
	// Cette url doit retourner une liste des personnes couvertes par la caserne
	// de pompiers correspondante.
	// Donc, si le numéro de station = 1, elle doit renvoyer les habitants
	// couverts par la station numéro 1. La liste
	// doit inclure les informations spécifiques suivantes : prénom, nom,
	// adresse, numéro de téléphone. De plus,
	// elle doit fournir un décompte du nombre d'adultes et du nombre d'enfants
	// (tout individu âgé de 18 ans ou
	// moins) dans la zone desservie.

	public List<FireStationCoveragePerson> getFireStationCoveragePerson(
			String station) {

		List<Firestation> listFireStationAddress = firestationdao
				.fireStationAdressbyStation(station);

		List<FireStationCoveragePerson> fireStationCoveragePerson = new ArrayList<>();

		FireStationCoveragePerson fireStationInfo = new FireStationCoveragePerson();

		int adultCount = 0;
		int childCount = 0;

		for (Firestation firestation : listFireStationAddress) {

			PersonList personInfo = new PersonList();

			List<Person> personByAddress = persondao
					.listPersonByAddress(firestation.getAddress());

			if (personByAddress != null) {

				for (Person person : personByAddress) {

					Medicalrecord personMedicalRecord = medicalrecorddao
							.getMedicalrecordInfo(person.getLastName(),
									person.getFirstName());

					int age = CalculateAge.personBirthDate(
							personMedicalRecord.getBirthdate());

					if (personMedicalRecord != null) {

						if (age <= 18)
							childCount++;
						else
							adultCount++;
					}
					personInfo.setLastName(person.getLastName());
					personInfo.setFirstName(person.getFirstName());
					personInfo.setAddress(person.getAddress());
					personInfo.setCity(person.getCity());
					personInfo.setZip(person.getZip());
					personInfo.setPhone(person.getPhone());
				}

				fireStationInfo.setStation(station);
				fireStationInfo.setAdultCount(adultCount);
				fireStationInfo.setChildCount(childCount);
				fireStationInfo.getPersonListForFirestation().add(personInfo);
			}
		}
		fireStationCoveragePerson.add(fireStationInfo);
		return fireStationCoveragePerson;
	}

	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// Cette url doit retourner une liste de tous les foyers desservis par la
	// caserne. Cette liste doit regrouper les personnes par adresse. Elle doit
	// aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
	// faire figurer leurs antécédents médicaux (médicaments, posologie et
	// allergies) à côté de chaque nom

	public List<FireStationPersonAtAddress> getFireStationPersonAtAddress(
			List<String> station) {

		List<FireStationPersonAtAddress> fireStationPersonAtAddress = new ArrayList<>();

		for (String Station : station) { // Pour chaque n° de station

			// Récupération de son adresse

			List<Firestation> listFireStationAddress = firestationdao
					.fireStationAdressbyStation(Station);

			for (Firestation firestation : listFireStationAddress) {

				FireStationPersonAtAddress fireStationInfo = new FireStationPersonAtAddress();
				PersonInfo personInfo = new PersonInfo();

				// Récupération des habitants et de leurs infos médicales à
				// cette adresse
				List<Person> personByAddress = persondao
						.listPersonByAddress(firestation.getAddress());

				for (Person person : personByAddress) {

					Medicalrecord personMedicalRecord = medicalrecorddao
							.getMedicalrecordInfo(personInfo.getLastName(),
									personInfo.getFirstName());

					if (personMedicalRecord != null) {

						int age = CalculateAge.personBirthDate(
								personMedicalRecord.getBirthdate());

						personInfo.setAge(age);
						personInfo.setMedications(
								personMedicalRecord.getMedications());
						personInfo.setAllergies(
								personMedicalRecord.getAllergies());
					}
					personInfo.setAddress(person.getAddress());
					personInfo.setLastName(person.getLastName());
					personInfo.setFirstName(person.getFirstName());
					personInfo.setPhone(person.getPhone());
				}
				fireStationInfo.setAddress(firestation.getAddress());
				fireStationInfo.getPersonAtAddress().add(personInfo);
				fireStationInfo.setStation(Station);
				fireStationPersonAtAddress.add(fireStationInfo);
			}
		}
		return fireStationPersonAtAddress;
	}

}
