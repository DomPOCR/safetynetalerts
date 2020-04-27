package com.safetynet.safetyalerts.dto;

import java.util.ArrayList;
import java.util.List;

public class FireStationCoveragePerson {

	private String station;
	private Integer adultCount;
	private Integer childCount;
	private List<PersonList> personListForFirestation = new ArrayList<>();

	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public Integer getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}
	public Integer getChildCount() {
		return childCount;
	}
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
	public List<PersonList> getPersonListForFirestation() {
		return personListForFirestation;
	}
	public void setPersonListForFirestation(
			List<PersonList> personListForFirestation) {
		this.personListForFirestation = personListForFirestation;
	}

}
