package com.safetynet.safetyalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetyalerts.dao.PersonDaoImpl;
import com.safetynet.safetyalerts.dao.medicalrecordDaoImpl;
import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.model.personInfo;

@Service
public class PersonService {

	@Autowired
	PersonDaoImpl persondao;
	medicalrecordDaoImpl medicalrecorddao;

	public List<String> getCommunityEmail(String city) {

		List<Person> listPerson = persondao.listPersonByCity(city);
		List<String> listEmails = new ArrayList<String>();

		for (Person person : listPerson) {
			listEmails.add("email des habitants de la ville de " + city + " : "
					+ person.getEmail());
		}
		return listEmails;
	}

	public List<String> getPerson() {

		List<Person> listPerson = persondao.listPerson();
		List<String> listPersons = new ArrayList<String>();

		for (Person person : listPerson) {
			listPersons.add("Liste des personnes du fichier : "
					+ person.getFirstName() + " " + person.getLastName());
		}
		return listPersons;
	}

	public List<personInfo> getPersonInfo(String lastname, String firstname) {

		List<Person> listPerson = persondao.listPersonInfo(lastname, firstname);

		List<Medicalrecord> listMedicalRecord = medicalrecorddao
				.listMedicalrecordInfo(lastname, firstname);

		List<personInfo> listPersonInfo = new ArrayList<>();

		for (Person person : listPerson) {

		}
		return listPersonInfo;
	}
}
