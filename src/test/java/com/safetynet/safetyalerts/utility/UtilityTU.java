package com.safetynet.safetyalerts.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@AutoConfigureMockMvc
public class UtilityTU {

	@Autowired
	MockMvc mockmvc;

	@Test
	void CalculateAge() throws Exception {

		int age;

		age = CalculateAge.personBirthDate("03/06/1984");
		assertEquals(36, age);

		age = CalculateAge.personBirthDate("01/01/1995");
		assertEquals(25, age);
	}

}
