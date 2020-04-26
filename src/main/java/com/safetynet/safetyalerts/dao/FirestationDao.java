package com.safetynet.safetyalerts.dao;

import java.util.List;

import com.safetynet.safetyalerts.model.Firestation;

public interface FirestationDao {

	List<Firestation> listFirestation();
	Firestation FireStationAtAddress(String Address);
	List<Firestation> FireStationAdressbyStation(String Station);

}
