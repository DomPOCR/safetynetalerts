package com.safetynet.safetyalerts.dao;

import java.util.List;

import com.safetynet.safetyalerts.model.Person;

public interface PersonDao {

	List<Person> listPerson();
	List<Person> listPersonInfo(String lastname, String firstname);
	List<Person> listPersonByCity(String city);
	List<Person> listPersonByAddress(String address);

	void createPerson(Person person);
	boolean deletePerson(Person person);
	boolean updatePerson(Person person);
}
