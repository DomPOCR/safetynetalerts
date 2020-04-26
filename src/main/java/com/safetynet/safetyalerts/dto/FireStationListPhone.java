package com.safetynet.safetyalerts.dto;

import java.util.Set;

public class FireStationListPhone {

	private String station;
	private Set<String> residentsPhone;

	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public Set<String> getResidentsPhone() {
		return residentsPhone;
	}
	public void setResidentsPhone(Set<String> residentsPhone) {
		this.residentsPhone = residentsPhone;
	}

}
