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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetyalerts.exceptions.DataRepositoryException;
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

	// Commit uniquement dans le main et pas dans les tests pour ne pas modifier
	// le JSON
	private boolean commit = true;

	public Database getDatabase() {

		return DataRepository.db;
	}

	public DataRepository() throws DataRepositoryException {

		this.init();

	}

	// Charger le fichier data.json en mémoire dans l'objet database

	public void init() {

		try (InputStream ips = ClassLoader
				.getSystemResourceAsStream(jsonFile)) {
			db = objectMapper.readerFor(Database.class).readValue(ips);
			logger.info("OK - FILE_OPEN : " + jsonFile);

		} catch (FileNotFoundException e) {
			logger.info("KO - FILE_NOT_FOUND :" + jsonFile);
			throw new DataRepositoryException("KO - FILE_NOT_FOUND", e);
		} catch (IOException e) {
			logger.info("KO - I/O ERROR :" + jsonFile);
			throw new DataRepositoryException("KO - I/O ERROR", e);
		}
	}
	public void commit() {

		if (commit) {

			// Récupérer le path du JSON
			URL url = ClassLoader.getSystemResource(jsonFile);

			// On ouvre un flux d'écriture vers le fichier JSON

			try (OutputStream ops = new FileOutputStream(url.getFile())) {

				// Ecriture du fichier JSON avec formatage
				// (WithDefaultPrettyPrinter)
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(ops,
						db);

				logger.info("OK - fichier JSON  mis à jour " + jsonFile);

			} catch (FileNotFoundException e) {
				logger.info("KO - FILE_NOT_FOUND" + jsonFile);
				throw new DataRepositoryException("KO - FILE_NOT_FOUND", e);
			} catch (IOException e) {
				logger.info("KO - I/O ERROR" + jsonFile);
				throw new DataRepositoryException("KO - I/O ERROR", e);
			}
		}
	}

	// Autorisation de modifier la velur de commit
	public void setCommit(boolean commit) {
		this.commit = commit;
	}

}
