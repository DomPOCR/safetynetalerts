package com.safetynet.safetyalerts.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.repositories.DataRepository;

@Service
public class PersonService {

	@Autowired
	private DataRepository dataRepository;

	public Collection<String> getCommunityEmail(String city) {

		Collection<String> collectionEmails = new HashSet<String>();

		for (Person person : dataRepository.getPersonsByCity(city)) {

			collectionEmails.add("email : " + person.getEmail());

		}
		return collectionEmails;
	}

}
