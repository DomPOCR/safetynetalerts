package com.safetynet.safetyalerts.dto;

import java.util.List;

import com.safetynet.safetyalerts.model.Person;

public class ChildInfo {

	private String firstName;
	private String lastName;
	private String address;
	private int age;
	private List<Person> famillyMember;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<Person> getFamillyMember() {
		return famillyMember;
	}
	public void setFamillyMember(List<Person> famillyMember) {
		this.famillyMember = famillyMember;
	}
}
