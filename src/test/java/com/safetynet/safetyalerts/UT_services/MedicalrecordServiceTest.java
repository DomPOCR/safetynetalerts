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
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
import com.safetynet.safetyalerts.exceptions.DataNotFoundException;
import com.safetynet.safetyalerts.exceptions.InvalidArgumentException;
import com.safetynet.safetyalerts.model.Medicalrecord;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.service.MedicalrecordService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalrecordServiceTest {

	@Autowired
	MedicalrecordService MedicalrecordServiceTest;
	@MockBean
	MedicalrecordDao medicalrecordDaoMock;
	@MockBean
	PersonDao personDaoMock;

	Person personBoyd = new Person("John", "Boyd", "1509 Culver St", "Culver",
			"97451", "841-874-6512", "jaboyd@email.com");
	Person personBauer = new Person("Jack", "Bauer", "1 FBI St.", "L.A.",
			"59800", "066666", "test1@test.us");
	Person personGates = new Person("Bill", "Gates", "1 MS-DOS St.", "L.A.",
			"59801", "077777", "test2@test.us");

	List<String> medications = new ArrayList<String>(
			Arrays.asList("a", "b", "c", "d"));
	List<String> allergies = new ArrayList<String>(
			Arrays.asList("e", "f", "g", "h"));

	Medicalrecord MedicalrecordBoyd = new Medicalrecord("John", "Boyd",
			"03/06/1984", medications, allergies);
	Medicalrecord MedicalrecordBauer = new Medicalrecord("Jack", "Bauer",
			"03/01/1984", medications, allergies);
	Medicalrecord MedicalrecordUnknown = new Medicalrecord("Bill", "Gates",
			"03/01/1944", medications, allergies);
	Medicalrecord MedicalrecordEmpty = new Medicalrecord("", "", "03/01/1984",
			medications, allergies);

	@Test
	public void createNonExistingMedicalrecordForExistingPersonTest()
			throws Exception {

		List<Person> listPersonInfo = new ArrayList<Person>();
		List<Medicalrecord> listMedicalrecord = new ArrayList<Medicalrecord>();

		// GIVEN
		listPersonInfo.add(personBauer);

		// WHEN
		Mockito.when(personDaoMock.listPersonInfo(any(String.class),
				any(String.class))).thenReturn(listPersonInfo);

		Mockito.when(medicalrecordDaoMock.listMedicalrecord())
				.thenReturn(listMedicalrecord);

		// THEN
		Assertions.assertTrue(MedicalrecordServiceTest
				.createMedicalrecord(MedicalrecordBauer));
		verify(medicalrecordDaoMock, Mockito.times(1))
				.createMedicalrecord(MedicalrecordBauer);
	}

	@Test
	public void createExistingMedicalrecordForExistingPersonTest()
			throws Exception {

		List<Person> listPersonInfo = new ArrayList<Person>();
		List<Medicalrecord> listMedicalrecord = new ArrayList<Medicalrecord>();

		// GIVEN
		listPersonInfo.add(personBoyd);
		listMedicalrecord.add(MedicalrecordBoyd);

		// WHEN
		Mockito.when(personDaoMock.listPersonInfo(any(String.class),
				any(String.class))).thenReturn(listPersonInfo);

		Mockito.when(medicalrecordDaoMock.listMedicalrecord())
				.thenReturn(listMedicalrecord);

		// THEN
		try {
			Assertions.assertFalse(MedicalrecordServiceTest
					.createMedicalrecord(MedicalrecordBoyd));
			verify(medicalrecordDaoMock, Mockito.times(0))
					.createMedicalrecord(MedicalrecordBoyd);
		} catch (DataAlreadyExistException eExp) {
			assert (eExp.getMessage().contains("existe déjà"));
		}
	}
	@Test
	public void createMedicalrecordForNonExistingPersonTest() throws Exception {

		List<Person> listPersonInfo = new ArrayList<Person>();
		// GIVEN

		// WHEN
		Mockito.when(personDaoMock.listPersonInfo(any(String.class),
				any(String.class))).thenReturn(listPersonInfo);

		// THEN
		try {
			Assertions.assertFalse(MedicalrecordServiceTest
					.createMedicalrecord(MedicalrecordBauer));
			verify(medicalrecordDaoMock, Mockito.times(0))
					.createMedicalrecord(MedicalrecordBauer);
		} catch (DataNotFoundException eExp) {
			assert (eExp.getMessage().contains("n'existe pas"));
		}
	}
	@Test
	public void updateMedicalrecordForExistingPersonTest() throws Exception {

		// GIVEN
		// WHEN

		Mockito.when(medicalrecordDaoMock
				.updateMedicalrecord(any(Medicalrecord.class)))
				.thenReturn(true);

		// THEN
		Assertions.assertTrue(MedicalrecordServiceTest
				.updateMedicalrecord(MedicalrecordBoyd));
		verify(medicalrecordDaoMock, Mockito.times(1))
				.updateMedicalrecord(MedicalrecordBoyd);
	}

	@Test
	public void updateMedicalrecordForNonExistingPersonTest() throws Exception {

		List<Person> listPersonInfo = new ArrayList<Person>();
		List<Medicalrecord> listMedicalrecord = new ArrayList<Medicalrecord>();

		// GIVEN
		listPersonInfo.add(personBauer);

		// WHEN

		Mockito.when(personDaoMock.listPersonInfo(any(String.class),
				any(String.class))).thenReturn(listPersonInfo);

		Mockito.when(medicalrecordDaoMock.listMedicalrecord())
				.thenReturn(listMedicalrecord);

		// THEN
		try {
			Assertions.assertFalse(MedicalrecordServiceTest
					.updateMedicalrecord(MedicalrecordBauer));
			verify(medicalrecordDaoMock, Mockito.times(0))
					.updateMedicalrecord(MedicalrecordBauer);
		} catch (DataNotFoundException eExp) {
			assert (eExp.getMessage().contains("n'existe pas"));
		}
	}
	@Test
	public void deleteMedicalrecordForExistingPersonTest() throws Exception {

		// GIVEN
		// WHEN

		Mockito.when(medicalrecordDaoMock
				.deleteMedicalrecord(any(Medicalrecord.class)))
				.thenReturn(true);

		// THEN
		Assertions.assertTrue(MedicalrecordServiceTest
				.deleteMedicalrecord(MedicalrecordBoyd));
		verify(medicalrecordDaoMock, Mockito.times(1))
				.deleteMedicalrecord(MedicalrecordBoyd);
	}

	@Test
	public void deleteMedicalrecordForNonExistingPersonTest() throws Exception {

		// GIVEN
		// WHEN

		Mockito.when(medicalrecordDaoMock
				.deleteMedicalrecord(any(Medicalrecord.class)))
				.thenReturn(false);

		// THEN
		try {
			Assertions.assertFalse(MedicalrecordServiceTest
					.deleteMedicalrecord(MedicalrecordBoyd));
			verify(medicalrecordDaoMock, Mockito.times(0))
					.deleteMedicalrecord(MedicalrecordBoyd);
		} catch (DataNotFoundException eExp) {
			assert (eExp.getMessage().contains("n'a pas de dossier médical"));
		}
	}

	@Test
	public void getMedicalrecordTest() throws Exception {

		List<Medicalrecord> listMedicalrecord = new ArrayList<Medicalrecord>();

		// GIVEN
		listMedicalrecord.add(MedicalrecordBoyd);
		listMedicalrecord.add(MedicalrecordBauer);

		// WHEN
		Mockito.when(medicalrecordDaoMock.listMedicalrecord())
				.thenReturn(listMedicalrecord);

		// THEN
		assertThat(MedicalrecordServiceTest.getMedicalrecord().size())
				.isEqualTo(2);
	}

	@Test
	public void getValidMedicalrecordInfoTest() throws Exception {

		List<Medicalrecord> listMedicalrecord = new ArrayList<Medicalrecord>();

		// GIVEN
		listMedicalrecord.add(MedicalrecordBoyd);

		// WHEN
		Mockito.when(medicalrecordDaoMock.listMedicalrecord())
				.thenReturn(listMedicalrecord);

		// THEN
		assertThat(MedicalrecordServiceTest
				.getMedicalrecordInfo(MedicalrecordBoyd.getLastName(),
						MedicalrecordBoyd.getFirstName())
				.size()).isEqualTo(1);
	}

	@Test
	public void getEmptyMedicalrecordInfoTest() throws Exception {

		// GIVEN
		// Liste vide

		// WHEN
		Mockito.when(medicalrecordDaoMock.listMedicalrecord()).thenReturn(null);

		// THEN
		try {
			assertThat(MedicalrecordServiceTest.getMedicalrecordInfo("", "")
					.size()).isEqualTo(0);
		} catch (InvalidArgumentException eExp) {
			assert (eExp.getMessage().contains("ne peut être vide"));
		}
	}
}
