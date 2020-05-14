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

import com.safetynet.safetyalerts.dao.MedicalrecordDao;
import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
import com.safetynet.safetyalerts.exceptions.DataNotFoundException;
import com.safetynet.safetyalerts.exceptions.InvalidArgumentException;
import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.service.MedicalrecordService;
import com.safetynet.safetyalerts.service.PersonService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceTest {

	@Autowired
	PersonService personServiceTest;
	@MockBean
	PersonDao personDaoMock;
	@MockBean
	MedicalrecordDao medicalrecordDaoMock;
	@MockBean
	MedicalrecordService medicalrecordServiceMock;
	@MockBean
	DataNotFoundException dataNotFoundExceptionMock;
	@MockBean
	InvalidArgumentException invalidArgumentExceptionMock;

	Person personBoyd = new Person("John", "Boyd", "1509 Culver St", "Culver",
			"97451", "841-874-6512", "jaboyd@email.com");

	Person personBauer = new Person("Jack", "Bauer", "1 FBI St.", "L.A.",
			"59800", "066666", "test1@test.us");

	Person personChild = new Person("Roger", "Boyd", "1509 Culver St", "Culver",
			"97451", "841-874-6512", "jaboyd@email.com");

	List<String> medications = new ArrayList<String>(
			Arrays.asList("a", "b", "c", "d"));
	List<String> allergies = new ArrayList<String>(
			Arrays.asList("e", "f", "g", "h"));

	Medicalrecord MedicalrecordBoyd = new Medicalrecord("John", "Boyd",
			"03/06/1984", medications, allergies);
	Medicalrecord MedicalrecordChild = new Medicalrecord("Roger", "Boyd",
			"09/06/2017", medications, allergies);
	Medicalrecord MedicalrecordUnknown = new Medicalrecord("Jack", "Bauer",
			"03/01/1984", medications, allergies);
	Medicalrecord MedicalrecordEmpty = new Medicalrecord("", "", "03/01/1984",
			medications, allergies);

	PersonInfo personInfoTest = new PersonInfo();

	String cityOK = "Culver";
	String cityKO = "Lille";

	String AddressOK = "1509 Culver St";
	String AddressKO = "000 FBI Ave";

	@Test
	public void createExistingPersonTest() throws Exception {

		List<Person> listPerson = new ArrayList<Person>();

		// GIVEN
		listPerson.add(personBoyd);

		// WHEN
		Mockito.when(personDaoMock.listPerson()).thenReturn(listPerson);

		// THEN
		// On crée un personne qui existe
		try {
			Assertions.assertFalse(personServiceTest.createPerson(personBoyd));
			verify(personDaoMock, Mockito.times(0)).createPerson(any());
		} catch (DataAlreadyExistException eExp) {
			assert (eExp.getMessage().contains("existe déjà"));
		}
	}

	@Test
	public void createNonExistingPersonTest() throws Exception {

		List<Person> listPerson = new ArrayList<Person>();

		// GIVEN

		// WHEN
		// On retourne une liste vide
		Mockito.when(personDaoMock.listPerson()).thenReturn(listPerson);

		// THEN
		Assertions.assertTrue(personServiceTest.createPerson(personBauer));
		verify(personDaoMock, Mockito.times(1)).createPerson(personBauer);
	}

	@Test
	public void updateExistingPersonTest() throws Exception {

		// GIVEN

		// WHEN
		Mockito.when(personDaoMock.updatePerson(any(Person.class)))
				.thenReturn(true);

		// THEN
		Assertions.assertTrue(personServiceTest.updatePerson(personBoyd));
		verify(personDaoMock, Mockito.times(1)).updatePerson(personBoyd);
	}

	@Test
	public void updateNonExistingPersonTest() throws Exception {

		// GIVEN

		// WHEN
		Mockito.when(personDaoMock.updatePerson(any(Person.class)))
				.thenReturn(false);

		// THEN
		try {
			Assertions.assertFalse(personServiceTest.updatePerson(personBoyd));
			verify(personDaoMock, Mockito.times(1)).updatePerson(personBoyd);
		} catch (DataNotFoundException eExp) {
			assert (eExp.getMessage().contains("n'existe pas"));
		}
	}

	@Test
	public void deleteExistingPersonTest() throws Exception {

		// GIVEN

		// WHEN
		Mockito.when(personDaoMock.deletePerson(any(Person.class)))
				.thenReturn(true);

		// THEN
		Assertions.assertTrue(personServiceTest.deletePerson(personBoyd));
		verify(personDaoMock, Mockito.times(1)).deletePerson(personBoyd);
	}

	@Test
	public void deleteNonExistingPersonTest() throws Exception {

		// GIVEN

		// WHEN
		Mockito.when(personDaoMock.deletePerson(any(Person.class)))
				.thenReturn(false);

		// THEN
		try {
			Assertions.assertFalse(personServiceTest.deletePerson(personBoyd));
			verify(personDaoMock, Mockito.times(0)).deletePerson(personBoyd);
		} catch (DataNotFoundException eExp) {
			assert (eExp.getMessage().contains("n'existe pas"));
		}
	}

	@Test
	public void getPersonTest() throws Exception {

		List<Person> listPerson = new ArrayList<Person>();

		// GIVEN
		listPerson.add(personBoyd);
		listPerson.add(personBauer);

		// WHEN
		Mockito.when(personDaoMock.listPerson()).thenReturn(listPerson);

		// THEN
		assertThat(personServiceTest.getPerson().size()).isEqualTo(2);

		verify(personDaoMock, Mockito.times(1)).listPerson();
	}

	@Test
	public void getValidCommunityEmailTest() throws Exception {

		List<Person> listPersonByCity = new ArrayList<Person>();

		// GIVEN
		listPersonByCity.add(personBoyd);

		// WHEN
		Mockito.when(personDaoMock.listPersonByCity(any(String.class)))
				.thenReturn(listPersonByCity);

		// THEN
		assertThat(personServiceTest.getCommunityEmail(cityOK).size())
				.isEqualTo(1);

	}

	@Test
	public void getInvalidCommunityEmailTest() throws Exception {

		List<Person> listPersonByCity = new ArrayList<Person>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(personDaoMock.listPersonByCity(any(String.class)))
				.thenReturn(listPersonByCity);

		// THEN
		assertThat(personServiceTest.getCommunityEmail(cityKO).size())
				.isEqualTo(0);

	}

	@Test
	public void getEmptyCityCommunityEmailTest() throws Exception {

		List<Person> listPersonByCity = new ArrayList<Person>();

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(personDaoMock.listPersonByCity(any(String.class)))
				.thenReturn(listPersonByCity);

		// THEN
		try {
			assertThat(personServiceTest.getCommunityEmail("").size())
					.isEqualTo(0);

		} catch (InvalidArgumentException eExp) {
			assert (eExp.getMessage().contains("ne peut être vide"));
		}
	}

	@Test
	public void getValidPersonInfoTest() throws Exception {

		List<PersonInfo> listPersonInfo = new ArrayList<PersonInfo>();
		List<Medicalrecord> listMedicalrecord = new ArrayList<Medicalrecord>();

		// GIVEN
		listPersonInfo.add(personInfoTest);
		listMedicalrecord.add(MedicalrecordBoyd);

		// WHEN
		Mockito.when(medicalrecordDaoMock
				.getMedicalrecordInfo(any(String.class), any(String.class)))
				.thenReturn(MedicalrecordBoyd);

		listPersonInfo = personServiceTest.getPersonInfo(
				personBoyd.getLastName(), personBoyd.getFirstName());

		// THEN
		assertThat(personServiceTest.getPersonInfo(personBoyd.getLastName(),
				personBoyd.getFirstName())).isEqualTo(listPersonInfo);

	}

	@Test
	public void getUnknownPersonInfoTest() throws Exception {

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(medicalrecordDaoMock
				.getMedicalrecordInfo(any(String.class), any(String.class)))
				.thenReturn(MedicalrecordUnknown);

		// THEN
		assertThat(personServiceTest.getPersonInfo(personBauer.getLastName(),
				personBauer.getFirstName()).size()).isEqualTo(0);

	}

	@Test
	public void getEmptyPersonInfoTest() throws Exception {

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(medicalrecordDaoMock
				.getMedicalrecordInfo(any(String.class), any(String.class)))
				.thenReturn(MedicalrecordEmpty);

		// THEN
		try {
			assertThat(personServiceTest.getPersonInfo("", "").size())
					.isEqualTo(0);
		} catch (InvalidArgumentException eExp) {
			assert (eExp.getMessage().contains("ne peut être vide"));
		}
	}

	@Test
	public void getValidChildByAddressTest() throws Exception {

		List<Person> listPersonByAddress = new ArrayList<Person>();
		List<Medicalrecord> listMedicalrecord = new ArrayList<Medicalrecord>();

		// GIVEN
		// On teste avec un enfant
		listPersonByAddress.add(personChild);
		listMedicalrecord.add(MedicalrecordChild);

		// WHEN
		Mockito.when(medicalrecordDaoMock
				.getMedicalrecordInfo(any(String.class), any(String.class)))
				.thenReturn(MedicalrecordChild);

		Mockito.when(personDaoMock.listPersonByAddress(any(String.class)))
				.thenReturn(listPersonByAddress);

		// THEN
		assertThat(personServiceTest.getChildByAddress(personChild.getAddress())
				.size()).isEqualTo(1);
	}

	@Test
	public void getInvalidChildByAddressTest() throws Exception {

		List<Person> listPersonByAddress = new ArrayList<Person>();
		List<Medicalrecord> listMedicalrecord = new ArrayList<Medicalrecord>();

		// GIVEN
		// On teste avec un adulte
		listPersonByAddress.add(personBoyd);
		listMedicalrecord.add(MedicalrecordBoyd);

		// WHEN
		Mockito.when(medicalrecordDaoMock
				.getMedicalrecordInfo(any(String.class), any(String.class)))
				.thenReturn(MedicalrecordBoyd);

		Mockito.when(personDaoMock.listPersonByAddress(any(String.class)))
				.thenReturn(listPersonByAddress);

		// THEN
		// Le retour est vide
		assertThat(personServiceTest.getChildByAddress(personBoyd.getAddress())
				.size()).isEqualTo(0);
	}
	@Test
	public void getValidChildByEmptyAddressTest() throws Exception {

		List<Person> listPersonByAddress = new ArrayList<Person>();
		List<Medicalrecord> listMedicalrecord = new ArrayList<Medicalrecord>();

		// GIVEN
		// On teste avec un enfant
		listPersonByAddress.add(personChild);
		listMedicalrecord.add(MedicalrecordChild);

		// WHEN
		Mockito.when(medicalrecordDaoMock
				.getMedicalrecordInfo(any(String.class), any(String.class)))
				.thenReturn(MedicalrecordChild);

		Mockito.when(personDaoMock.listPersonByAddress(any(String.class)))
				.thenReturn(listPersonByAddress);

		// THEN
		try {
			assertThat(personServiceTest.getChildByAddress("").size())
					.isEqualTo(0);
		} catch (InvalidArgumentException eExp) {
			assert (eExp.getMessage().contains("ne peut être vide"));
		}
	}
}
