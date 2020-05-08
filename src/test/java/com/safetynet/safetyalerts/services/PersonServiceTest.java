package com.safetynet.safetyalerts.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.dto.ChildInfo;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.service.MedicalrecordService;
import com.safetynet.safetyalerts.service.PersonService;
import com.safetynet.safetyalerts.utility.CalculateAge;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceTest {

	@Autowired
	PersonService personServiceTest;
	@MockBean
	PersonDao personDaoMock;
	@MockBean
	MedicalrecordService medicalrecordServiceMock;
	@MockBean
	CalculateAge calculateAgeMock;

	// @Test
	public void createPersonValidTest() throws Exception {

		// GIVEN

		Person personAdd = new Person("Jack", "Bauer", "1 FBI St.", "L.A.",
				"59800", "066666", "test1@test.us");

		// Test si la personne existe déjà
		Mockito.when(personDaoMock.listPerson().contains(personAdd))
				.thenReturn(false);

		// WHEN

		boolean result = personServiceTest.createPerson(personAdd);

		// THEN
		Assertions.assertTrue(result);
	}

	// @Test
	void getChildByAddressTest() throws Exception {

		// GIVEN
		List<Person> listPersonsTest = new ArrayList<>();

		Person person1 = new Person("Jack", "Bauer", "1 FBI St.", "L.A.",
				"59800", "066666", "test1@test.us");
		Person person2 = new Person("Jim", "Bauer", "1 FBI St.", "L.A.",
				"59800", "066666", "test2@test.us");

		listPersonsTest.add(person1);
		listPersonsTest.add(person2);

		Mockito.when(personDaoMock.listPersonByAddress(any(String.class)))
				.thenReturn(listPersonsTest);

		String birthDate = "02/05/2010";

		List<String> medications = new ArrayList<String>();
		medications.add("");

		List<String> allergies = new ArrayList<String>();
		allergies.add("");

		List<String> medicalrecordTest = new ArrayList<String>();

		Mockito.when(medicalrecordServiceMock
				.getMedicalrecordInfo(any(String.class), any(String.class)))
				.thenReturn(medicalrecordTest);

		Mockito.when(CalculateAge.personBirthDate(any(String.class)))
				.thenReturn(15);

		// WHEN
		List<ChildInfo> listChildInfoTest = personServiceTest
				.getChildByAddress("addresstest");

		// THEN
		assertThat(listChildInfoTest.size()).isEqualTo(2);

	}

}
