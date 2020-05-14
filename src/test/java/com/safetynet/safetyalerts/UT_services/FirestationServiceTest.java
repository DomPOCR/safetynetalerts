package com.safetynet.safetyalerts.UT_services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.safetyalerts.dao.FirestationDao;
import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.dto.FireStationCoveragePerson;
import com.safetynet.safetyalerts.dto.FireStationListPerson;
import com.safetynet.safetyalerts.dto.FireStationListPhone;
import com.safetynet.safetyalerts.dto.FireStationPersonAtAddress;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
import com.safetynet.safetyalerts.exceptions.DataNotFoundException;
import com.safetynet.safetyalerts.exceptions.InvalidArgumentException;
import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.service.FirestationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FirestationServiceTest {

	@Autowired
	FirestationService firestationServiceTest;
	@MockBean
	FirestationDao firestationDaoMock;
	@MockBean
	PersonDao personDaoMock;
	@MockBean
	InvalidArgumentException invalidArgumentExceptionMock;

	Firestation firestationNew = new Firestation("99", "999 Paris St");
	Firestation firestationNo1 = new Firestation("1", "644 Gershwin Cir");
	Firestation firestationNo3 = new Firestation("3", "1509 Culver St");

	FireStationListPerson fireStationListPersonTest = new FireStationListPerson();
	FireStationListPhone fireStationListPhoneTest = new FireStationListPhone();
	FireStationCoveragePerson fireStationCoveragePersonTest = new FireStationCoveragePerson();
	FireStationPersonAtAddress fireStationPersonAtAddressTest = new FireStationPersonAtAddress();

	Firestation listFireStationAddressTest = new Firestation();

	Person listPersonByAdressTest = new Person();

	String AddressOK = "834 Binoc Ave";
	String AddressKO = "000 FBI Ave";

	String NoStationOK = "1";
	String NoStationKO = "100";

	List<String> listStationOK = new ArrayList<String>(Arrays.asList("1", "2"));
	List<String> listStationKO = new ArrayList<String>(
			Arrays.asList("100", "200"));
	List<String> listStationEmpty = new ArrayList<String>(
			Arrays.asList("", ""));

	@Test
	public void createExistingFirestationTest() throws Exception {

		// GIVEN
		List<Firestation> listFirestation = new ArrayList<Firestation>();
		listFirestation.add(firestationNo3);

		// WHEN
		Mockito.when(firestationDaoMock.listFirestation())
				.thenReturn(listFirestation);

		// THEN
		// On crée une station qui existe
		try {
			Assertions.assertFalse(
					firestationServiceTest.createFirestation(firestationNo3));
			verify(firestationDaoMock, Mockito.times(0))
					.createFirestation(firestationNo3);
		} catch (DataAlreadyExistException eExp) {
			assert (eExp.getMessage().contains("existe déjà"));
		}
	}

	@Test
	public void createNonExistingFirestationTest() throws Exception {

		// GIVEN
		List<Firestation> listFirestation = new ArrayList<Firestation>();

		// WHEN
		// On retourne une liste vide
		Mockito.when(firestationDaoMock.listFirestation())
				.thenReturn(listFirestation);

		// THEN
		// On crée la station
		Assertions.assertTrue(
				firestationServiceTest.createFirestation(firestationNew));
		verify(firestationDaoMock, Mockito.times(1))
				.createFirestation(firestationNew);
	}

	@Test
	public void updateExistingFirestationTest() throws Exception {

		// GIVEN

		// WHEN

		Mockito.when(
				firestationDaoMock.updateFirestation(any(Firestation.class)))
				.thenReturn(true);

		// THEN
		// On MAJ la station
		Assertions.assertTrue(
				firestationServiceTest.updateFirestation(firestationNo3));
		verify(firestationDaoMock, Mockito.times(1))
				.updateFirestation(firestationNo3);
	}

	@Test
	public void updateNonExistingFirestationTest() throws Exception {

		// GIVEN

		// WHEN
		Mockito.when(
				firestationDaoMock.updateFirestation(any(Firestation.class)))
				.thenReturn(false);

		// THEN
		try {
			Assertions.assertFalse(
					firestationServiceTest.updateFirestation(firestationNew));
			verify(firestationDaoMock, Mockito.times(1))
					.updateFirestation(firestationNew);
		} catch (DataNotFoundException eExp) {
			assert (eExp.getMessage().contains("n'existe pas"));
		}
	}

	@Test
	public void deleteExistingFirestationTest() throws Exception {

		// GIVEN

		// WHEN

		Mockito.when(
				firestationDaoMock.deleteFirestation(any(Firestation.class)))
				.thenReturn(true);

		// THEN
		// On MAJ la station
		Assertions.assertTrue(
				firestationServiceTest.deleteFirestation(firestationNo3));
		verify(firestationDaoMock, Mockito.times(1))
				.deleteFirestation(firestationNo3);
	}

	@Test
	public void deleteNonExistingFirestationTest() throws Exception {

		// GIVEN

		// WHEN
		Mockito.when(
				firestationDaoMock.deleteFirestation(any(Firestation.class)))
				.thenReturn(false);

		// THEN
		try {
			Assertions.assertFalse(
					firestationServiceTest.deleteFirestation(firestationNew));
			verify(firestationDaoMock, Mockito.times(1))
					.deleteFirestation(firestationNew);
		} catch (DataNotFoundException eExp) {
			assert (eExp.getMessage().contains("n'existe pas"));
		}
	}

	@Test
	public void getFirestationTest() throws Exception {

		List<Firestation> listFirestation = new ArrayList<Firestation>();

		// GIVEN
		listFirestation.add(firestationNo1);
		listFirestation.add(firestationNo3);

		// WHEN
		Mockito.when(firestationDaoMock.listFirestation())
				.thenReturn(listFirestation);

		// THEN
		assertThat(firestationServiceTest.getFirestation().size()).isEqualTo(2);
	}

	@Test
	public void getValidAddressFireStationListPerson() throws Exception {

		List<Person> listPersonByAddress = new ArrayList<Person>();
		List<FireStationListPerson> fireStationListPerson = new ArrayList<>();

		// GIVEN
		listPersonByAddress.add(listPersonByAdressTest);
		fireStationListPerson.add(fireStationListPersonTest);

		// WHEN
		Mockito.when(personDaoMock.listPersonByAddress(any(String.class)))
				.thenReturn(listPersonByAddress);

		// THEN
		assertThat(firestationServiceTest.getFireStationListPerson(AddressOK)
				.size()).isEqualTo(1);
	}

	@Test
	public void getInvalidAddressFireStationListPerson() throws Exception {

		List<Person> listPersonByAddress = new ArrayList<Person>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(personDaoMock.listPersonByAddress(any(String.class)))
				.thenReturn(listPersonByAddress);

		// THEN
		assertThat(firestationServiceTest.getFireStationListPerson(AddressKO)
				.size()).isEqualTo(0);

	}

	@Test
	public void getEmptyAddressFireStationListPerson() throws Exception {

		List<Person> listPersonByAddress = new ArrayList<Person>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(personDaoMock.listPersonByAddress(any(String.class)))
				.thenReturn(listPersonByAddress);

		// THEN
		try {
			assertThat(
					firestationServiceTest.getFireStationListPerson("").size())
							.isEqualTo(0);

		} catch (InvalidArgumentException eExp) {
			assert (eExp.getMessage().contains("ne peut être vide"));
		}
	}

	@Test
	public void getValidFireStationListPhone() throws Exception {

		List<Firestation> listFireStationAddress = new ArrayList<Firestation>();
		List<FireStationListPhone> fireStationListPhone = new ArrayList<>();

		// GIVEN
		listFireStationAddress.add(listFireStationAddressTest);
		fireStationListPhone.add(fireStationListPhoneTest);

		// WHEN
		Mockito.when(firestationDaoMock
				.fireStationAdressbyStation(any(String.class)))
				.thenReturn(listFireStationAddress);

		// THEN
		assertThat(firestationServiceTest.getFireStationListPhone(NoStationOK)
				.size()).isEqualTo(1);

	}
	@Test
	public void getInvalidFireStationListPhone() throws Exception {

		List<Firestation> listFireStationAddress = new ArrayList<Firestation>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(firestationDaoMock
				.fireStationAdressbyStation(any(String.class)))
				.thenReturn(listFireStationAddress);

		// THEN
		assertThat(firestationServiceTest.getFireStationListPhone(NoStationKO)
				.size()).isEqualTo(0);

	}

	@Test
	public void getEmptyFireStationListPhone() throws Exception {

		List<Firestation> listFireStationAddress = new ArrayList<Firestation>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(firestationDaoMock
				.fireStationAdressbyStation(any(String.class)))
				.thenReturn(listFireStationAddress);

		// THEN
		try {
			assertThat(
					firestationServiceTest.getFireStationListPhone("").size())
							.isEqualTo(0);

		} catch (InvalidArgumentException eExp) {
			assert (eExp.getMessage().contains("ne peut être vide"));
		}
	}

	@Test
	public void getValidFireStationCoveragePerson() throws Exception {

		List<Firestation> listFireStationAddress = new ArrayList<Firestation>();
		List<FireStationCoveragePerson> fireStationCoveragePerson = new ArrayList<>();

		// GIVEN
		listFireStationAddress.add(listFireStationAddressTest);
		fireStationCoveragePerson.add(fireStationCoveragePersonTest);

		// WHEN
		Mockito.when(firestationDaoMock
				.fireStationAdressbyStation(any(String.class)))
				.thenReturn(listFireStationAddress);

		// THEN
		assertThat(firestationServiceTest
				.getFireStationCoveragePerson(NoStationOK).size()).isEqualTo(1);

	}

	@Test
	public void getInvalidFireStationCoveragePerson() throws Exception {

		List<Firestation> listFireStationAddress = new ArrayList<Firestation>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(firestationDaoMock
				.fireStationAdressbyStation(any(String.class)))
				.thenReturn(listFireStationAddress);

		// THEN
		assertThat(firestationServiceTest
				.getFireStationCoveragePerson(NoStationKO).size()).isEqualTo(0);
	}

	@Test
	public void getEmptyFireStationCoveragePerson() throws Exception {

		List<Firestation> listFireStationAddress = new ArrayList<Firestation>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(firestationDaoMock
				.fireStationAdressbyStation(any(String.class)))
				.thenReturn(listFireStationAddress);

		// THEN
		try {
			assertThat(firestationServiceTest.getFireStationCoveragePerson("")
					.size()).isEqualTo(0);

		} catch (InvalidArgumentException eExp) {
			assert (eExp.getMessage().contains("ne peut être vide"));
		}
	}

	@Test
	public void getValidFireStationPersonAtAddress() throws Exception {

		List<Firestation> listFireStationAddress = new ArrayList<Firestation>();
		List<FireStationPersonAtAddress> fireStationPersonAtAddress = new ArrayList<>();

		// GIVEN
		listFireStationAddress.add(listFireStationAddressTest);
		fireStationPersonAtAddress.add(fireStationPersonAtAddressTest);

		// WHEN
		Mockito.when(firestationDaoMock
				.fireStationAdressbyStation(any(String.class)))
				.thenReturn(listFireStationAddress);

		// THEN
		assertThat(firestationServiceTest
				.getFireStationPersonAtAddress(listStationOK).size())
						.isEqualTo(2);
	}

	@Test
	public void getInvalidFireStationPersonAtAddress() throws Exception {

		List<Firestation> listFireStationAddress = new ArrayList<Firestation>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(firestationDaoMock
				.fireStationAdressbyStation(any(String.class)))
				.thenReturn(listFireStationAddress);

		// THEN
		assertThat(firestationServiceTest
				.getFireStationPersonAtAddress(listStationKO).size())
						.isEqualTo(0);
	}

	@Test
	public void getEmptyFireStationPersonAtAddress() throws Exception {

		List<Firestation> listFireStationAddress = new ArrayList<Firestation>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(firestationDaoMock
				.fireStationAdressbyStation(any(String.class)))
				.thenReturn(listFireStationAddress);

		// THEN
		try {
			assertThat(firestationServiceTest
					.getFireStationPersonAtAddress(listStationEmpty).size())
							.isEqualTo(0);

		} catch (InvalidArgumentException eExp) {
			assert (eExp.getMessage().contains("ne peut être vide"));
		}
	}
}
