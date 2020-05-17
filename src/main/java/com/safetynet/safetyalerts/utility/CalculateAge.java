package com.safetynet.safetyalerts.utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public abstract class CalculateAge {

	public static int personBirthDate(String birthdate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		LocalDate bDate = LocalDate.parse(birthdate, formatter);
		LocalDate toDay = LocalDate.now();

		return Period.between(bDate, toDay).getYears();

	}
}
