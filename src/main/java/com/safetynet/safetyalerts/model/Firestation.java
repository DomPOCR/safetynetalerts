package com.safetynet.safetyalerts.model;

public class Firestation {

	private String address;
	private String station;

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "Firestation [address=" + address + ", station=" + station
				+ ", getAddress()=" + getAddress() + ", getStation()="
				+ getStation() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
