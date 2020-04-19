package com.safetynet.safetyalerts.repositories;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetyalerts.model.Database;

@Repository
public class DataRepository {

	// // Permet de mapper du JSON en objet Java
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// // Fichier JSON em mémoire
	private static Database db;
	//
	// // Charger le fichier data.json en mémoire dans l'objet database
	public DataRepository() throws IOException {

		InputStream ips = this.getClass().getClassLoader()
				.getSystemResourceAsStream("data.json");

		db = objectMapper.readerFor(Database.class).readValue(ips);

		System.out.println(db.getPersons());
	}

}
