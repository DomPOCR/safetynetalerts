package com.safetynet.safetyalerts.model;

import java.util.ArrayList;
import java.util.List;

public class Database {

	private List<Person> persons = new ArrayList<Person>();
	private List<Firestation> firestations = new ArrayList<Firestation>();
	private List<Medicalrecord> medicalrecords = new ArrayList<Medicalrecord>();

	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	public List<Firestation> getFirestations() {
		return firestations;
	}
	public void setFirestations(List<Firestation> firestations) {
		this.firestations = firestations;
	}
	public List<Medicalrecord> getMedicalrecords() {
		return medicalrecords;
	}
	public void setMedicalrecords(List<Medicalrecord> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}

}
