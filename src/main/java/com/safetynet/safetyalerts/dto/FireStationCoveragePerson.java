package com.safetynet.safetyalerts.dto;

public class FireStationCoveragePerson {

	private String station;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private Integer adultCount;
	private Integer childCount;

	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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

}
