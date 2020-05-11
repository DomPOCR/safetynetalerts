package com.safetynet.safetyalerts.services;

import java.util.ArrayList;
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

import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
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

	@Test
	public void createPersonTest() throws Exception {

		// GIVEN

		Person personAdd1 = new Person("John", "Boyd", "1509 Culver St",
				"Culver", "97451", "841-874-6512", "jaboyd@email.com");

		Person personAdd2 = new Person("Jack", "Bauer", "1 FBI St.", "L.A.",
				"59800", "066666", "test1@test.us");

		List<Person> listPerson = new ArrayList<Person>();
		listPerson.add(personAdd1);

		Mockito.when(personDaoMock.listPerson()).thenReturn(listPerson);

		// WHEN

		// On crée un personne qui n'existe pas
		boolean result = personServiceTest.createPerson(personAdd2);

		// THEN
		Assertions.assertTrue(result);
	}

	@Test
	public void createExistingPersonTest() throws Exception {

		// GIVEN

		Person personAdd = new Person("John", "Boyd", "1509 Culver St",
				"Culver", "97451", "841-874-6512", "jaboyd@email.com");

		List<Person> listPerson = new ArrayList<Person>();
		listPerson.add(personAdd);

		Mockito.when(personDaoMock.listPerson()).thenReturn(listPerson);

		// WHEN
		// THEN
		// On crée un personne qui existe
		try {
			boolean result = personServiceTest.createPerson(personAdd);
		} catch (DataAlreadyExistException eExp) {
			assert (eExp.getMessage().contains("existe déjà"));
		}
	}

}
