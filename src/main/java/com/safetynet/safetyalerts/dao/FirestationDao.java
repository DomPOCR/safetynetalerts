package com.safetynet.safetyalerts.dao;

import java.util.List;

import com.safetynet.safetyalerts.model.Firestation;

public interface FirestationDao {

	List<Firestation> listFirestation();
	Firestation fireStationAtAddress(String Address);
	List<Firestation> fireStationAdressbyStation(String Station);

	boolean createFirestation(Firestation firestation);
	boolean updateFirestation(Firestation firestation);
	boolean deleteFirestation(Firestation firestation);

}
