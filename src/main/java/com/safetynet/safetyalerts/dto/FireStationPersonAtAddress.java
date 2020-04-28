package com.safetynet.safetyalerts.dto;

import java.util.ArrayList;
import java.util.List;

public class FireStationPersonAtAddress {

	private String station;
	private String address;
	private List<PersonInfo> personAtAddress = new ArrayList<>();
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<PersonInfo> getPersonAtAddress() {
		return personAtAddress;
	}
	public void setPersonAtAddress(List<PersonInfo> personAtAddress) {
		this.personAtAddress = personAtAddress;
	}
	
	
}
