package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.MedicalrecordDao;
import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.dto.ChildInfo;
import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
import com.safetynet.safetyalerts.exceptions.DataNotFoundException;
import com.safetynet.safetyalerts.exceptions.InvalidArgumentException;
import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.utility.CalculateAge;

@Service
public class PersonService {

	@Autowired
	PersonDao persondao;
	@Autowired
	MedicalrecordDao medicalrecorddao;

	// Création d'une personne
	public boolean createPerson(Person person) {

		// Vérification que la personne n'existe pas dans la DAO (nom + prénom)
		if (!persondao.listPerson().contains(person)) {
			persondao.createPerson(person);
			return true;
		} else {
			throw new DataAlreadyExistException(
					"La personne " + person.getFirstName() + " "
							+ person.getLastName() + " existe déjà !!");
		}
	}

	// MAJ personne (= suppression et création)
	public void updatePerson(Person person) {

		// Vérification que la personne existe dans la DAO (nom + prénom)
		if (!persondao.updatePerson(person)) {
			throw new DataNotFoundException(
					"La personne " + person.toString() + " n'existe pas !!");
		}

	}

	// Suppression d'une personne
	public void deletePerson(Person person) {
		// Vérification que la personne existe dans la DAO (nom + prénom)
		if (!persondao.deletePerson(person)) {
			throw new DataNotFoundException(
					"La personne " + person.toString() + " n'existe pas !!");
		}
	}

	// http://localhost:8080/person

	public List<String> getPerson() {

		List<Person> listPerson = persondao.listPerson();
		List<String> listPersons = new ArrayList<String>();

		for (Person person : listPerson) {
			listPersons.add(person.toString());
		}
		Collections.sort(listPersons);
		return listPersons;
	}

	// http://localhost:8080/communityEmail?city=<city>

	// Cette url doit retourner les Addresses mail de tous les habitants de la
	// ville.

	public Collection<String> getCommunityEmail(String city) {

		List<Person> listPerson = persondao.listPersonByCity(city);
		Set<String> listEmails = new HashSet<String>();

		if (city.isEmpty()) {

			throw new InvalidArgumentException("La ville ne peut être vide !!");
		}
		for (Person person : listPerson) {
			listEmails.add(person.getEmail());
		}
		return listEmails;
	}

	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>

	// Cette url doit retourner le nom, l'Addresse, l'âge, l'Addresse mail et
	// les
	// antécédents médicaux (médicaments,
	// posologie, allergies) de chaque habitant. Si plusieurs personnes portent
	// le même nom, elles doivent
	// toutes apparaître.

	public List<PersonInfo> getPersonInfo(String lastname, String firstname) {

		List<Person> listPerson = persondao.listPersonInfo(lastname, firstname);

		List<PersonInfo> listPersonInfo = new ArrayList<>();

		for (Person person : listPerson) {

			PersonInfo personInfo = new PersonInfo();

			personInfo.setLastName(person.getLastName());
			personInfo.setFirstName(person.getFirstName());
			personInfo.setAddress(person.getAddress());
			personInfo.setEmail(person.getEmail());

			Medicalrecord personMedicalRecord = medicalrecorddao
					.getMedicalrecordInfo(person.getLastName(),
							person.getFirstName());

			if (personMedicalRecord != null) {
				personInfo.setAge(CalculateAge
						.personBirthDate(personMedicalRecord.getBirthdate()));
				personInfo.setMedications(personMedicalRecord.getMedications());
				personInfo.setAllergies(personMedicalRecord.getAllergies());
			}
			listPersonInfo.add(personInfo);
		}
		return listPersonInfo;
	}

	// http://localhost:8080/childAlert?address=
	// Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans
	// ou moins) habitant à cette adresse. La liste doit comprendre le prénom et
	// le nom de famille de chaque enfant, son âge et une liste des autres
	// membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une
	// chaîne vide.

	public List<ChildInfo> getChildByAddress(String address) {

		if (address.isEmpty()) {

			throw new InvalidArgumentException(
					"L'adresse ne peut être vide !!");
		}
		List<Person> personByAddress = persondao.listPersonByAddress(address);

		List<ChildInfo> listChildInfo = new ArrayList<>();

		for (Person person : personByAddress) {

			ChildInfo childInfo = new ChildInfo();

			Medicalrecord personMedicalRecord = medicalrecorddao
					.getMedicalrecordInfo(person.getLastName(),
							person.getFirstName());

			int age = CalculateAge
					.personBirthDate(personMedicalRecord.getBirthdate());
			if ((personMedicalRecord != null) && (age <= 18)) {

				childInfo.setLastName(person.getLastName());
				childInfo.setFirstName(person.getFirstName());
				childInfo.setAge(age);
				childInfo.setAddress(person.getAddress());

				childInfo.setFamillyMember(personByAddress);
				listChildInfo.add(childInfo);
			}
		}
		return listChildInfo;
	}

}
