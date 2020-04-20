package com.safetynet.safetyalerts.dao;

import java.util.List;

import com.safetynet.safetyalerts.model.Person;

public interface PersonDaoInterface {

	List<Person> listPerson();
	List<Person> listPersonByCity(String city);

	Person getPerson(String email);

}
