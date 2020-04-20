package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.PersonDaoImpl;
import com.safetynet.safetyalerts.model.Person;

@Service
public class PersonService {

	@Autowired
	PersonDaoImpl persondao;

	public List<String> getCommunityEmail(String city) {

		List<Person> listPerson = persondao.listPersonByCity(city);

		List<String> collectionEmails = new ArrayList<String>();

		for (Person person : listPerson) {

			collectionEmails.add("email de tous les habitants de la ville de "
					+ city + " : " + person.getEmail());

		}
		return collectionEmails;
	}

}
