package com.safetynet.safetyalerts.repositories;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetyalerts.model.Database;

@Repository
public class DataRepository {

	// Permet de mapper du JSON en objet Java
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// Pour le log4j2
	private static final Logger logger = LogManager
			.getLogger(DataRepository.class);

	// Fichier JSON en mémoire
	private static Database db;
	private String jsonFile = "data.json";

	public Database getDatabase() {

		return DataRepository.db;
	}
	// Charger le fichier data.json en mémoire dans l'objet database
	public DataRepository() throws JsonMappingException, IOException {

		InputStream ips = null;
		try {
			this.getClass().getClassLoader();
			ips = ClassLoader.getSystemResourceAsStream(jsonFile);
			db = objectMapper.readerFor(Database.class).readValue(ips);
			logger.info("OK - Fichier JSON chargé");

		} catch (FileNotFoundException e) {
			logger.info("KO - FILE_NOT_FOUND" + jsonFile);
		}
		// Fermeture du fichier
		try {
			ips.close();
		} catch (IOException e) {
			logger.info("KO - PROBLEM TO CLOSE FILE" + jsonFile);
		}
	}
	public void commit() {

		// Récupérer le path du JSON
		URL url = ClassLoader.getSystemResource(jsonFile);

		// On ouvre un flux d'écriture vers le fichier JSON

		try (OutputStream ops = new FileOutputStream(url.getFile())) {

			// Ecriture du fichier JSON avec formatage
			// (WithDefaultPrettyPrinter)
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(ops, db);

			logger.info("OK - fichier JSON  mis à jour " + jsonFile);

		} catch (FileNotFoundException e) {
			logger.info("KO - FILE_NOT_FOUND" + jsonFile);
		}
		// Fermeture du fichier
		catch (IOException e) {
			logger.info("KO - PROBLEM TO CLOSE FILE" + jsonFile);
		}

	}

}
