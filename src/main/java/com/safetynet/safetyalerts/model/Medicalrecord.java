package com.safetynet.safetyalerts.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

public class Medicalrecord {

	// "firstName":"John", "lastName":"Boyd", "birthdate":"03/06/1984",
	// "medications"

	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	private String birthdate;

	private List<String> medications = new ArrayList<String>();
	private List<String> allergies = new ArrayList<String>();

	public Medicalrecord() {
		super();
	}

	public Medicalrecord(String firstName, String lastName, String birthdate,
			List<String> medications, List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}

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

	@Override
	public String toString() {
		return "Medicalrecord [lastName=" + lastName + ", firstName="
				+ firstName + ", birthdate=" + birthdate + ", medications="
				+ medications + ", allergies=" + allergies + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicalrecord other = (Medicalrecord) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

}
