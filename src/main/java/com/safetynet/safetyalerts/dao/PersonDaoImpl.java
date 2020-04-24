package com.safetynet.safetyalerts.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.model.Database;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.repositories.DataRepository;

@Service
public class PersonDaoImpl implements PersonDao {

	@Autowired
	private DataRepository dataRepository;

	// Liste l'ensemble des personnes du fichier
	@Override
	public List<Person> listPerson() {

		return dataRepository.getDatabase().getPersons();
	}

	// Liste les personnes par leur nom et prénom
	@Override
	public List<Person> listPersonInfo(String lastname, String firstname) {

		List<Person> ListPerson = new ArrayList<Person>();

		Database db = dataRepository.getDatabase();
		for (Person person : db.getPersons()) {
			if (lastname == null
					|| (person.getLastName().equalsIgnoreCase(lastname))
							&& (firstname == null || person.getFirstName()
									.equalsIgnoreCase(firstname))) {
				ListPerson.add(person);
			}
		}
		return ListPerson;
	}

	// Liste l'ensemble des personnes d'une ville
	@Override
	public List<Person> listPersonByCity(String city) {

		List<Person> ListPerson = new ArrayList<Person>();

		Database db = dataRepository.getDatabase();
		for (Person person : db.getPersons()) {
			if (person.getCity().equalsIgnoreCase(city)) {
				ListPerson.add(person);
			}
		}
		return ListPerson;
	}

	// Liste l'ensemble des personnes à une Addresse
	@Override
	public List<Person> listPersonByAddress(String Address) {

		List<Person> ListPerson = new ArrayList<Person>();

		Database db = dataRepository.getDatabase();
		for (Person person : db.getPersons()) {
			if (person.getAddress().equalsIgnoreCase(Address)) {
				ListPerson.add(person);
			}
		}
		return ListPerson;
	}
}
