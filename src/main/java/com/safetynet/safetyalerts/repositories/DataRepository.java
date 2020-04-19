package com.safetynet.safetyalerts.repositories;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetyalerts.model.Database;
import com.safetynet.safetyalerts.model.Person;

@Repository
public class DataRepository {

	// Permet de mapper du JSON en objet Java
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// Fichier JSON em mémoire
	private static Database db;

	// Charger le fichier data.json en mémoire dans l'objet database
	public DataRepository() throws IOException {

		InputStream ips = this.getClass().getClassLoader()
				.getSystemResourceAsStream("data.json");

		db = objectMapper.readerFor(Database.class).readValue(ips);

	}

	// Recuperer les personnes d'une ville (on ignore la casse de la ville)
	public Collection<Person> getPersonsByCity(String city) {

		Collection<Person> collectionPerson = new ArrayList<Person>();

		for (Person person : db.getPersons()) {
			if (person.getCity().equalsIgnoreCase(city)) {
				collectionPerson.add(person);
			}
		}
		return collectionPerson;
	}
}
