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

	@Override
	public List<Person> listPerson() {

		return dataRepository.getDatabase().getPersons();
	}

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

}
