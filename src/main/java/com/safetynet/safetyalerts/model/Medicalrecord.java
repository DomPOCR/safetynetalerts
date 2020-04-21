package com.safetynet.safetyalerts.model;

import java.util.ArrayList;
import java.util.List;

public class Medicalrecord {

	// "firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984",
	// "medications"

	private String firstName;
	private String lastName;
	private String birthdate;

	@Override
	public String toString() {
		return "Medicalrecord [firstName=" + firstName + ", lastName="
				+ lastName + ", birthdate=" + birthdate + ", medications="
				+ medications + ", allergies=" + allergies + ", getFirstName()="
				+ getFirstName() + ", getLastName()=" + getLastName()
				+ ", getBirthdate()=" + getBirthdate() + ", getMedications()="
				+ getMedications() + ", getAllergies()=" + getAllergies()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	private List<String> medications = new ArrayList<String>();
	private List<String> allergies = new ArrayList<String>();

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public List<String> getMedications() {
		return medications;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

}
