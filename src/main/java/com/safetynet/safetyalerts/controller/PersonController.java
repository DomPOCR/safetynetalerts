package com.safetynet.safetyalerts.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetyalerts.dto.ChildInfo;
import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.exceptions.IllegalArgumentException;
import com.safetynet.safetyalerts.model.Person;
import com.safetynet.safetyalerts.service.PersonService;

// Controller expose les API REST pour gérer les requetes qui viennent d'un client web

@RestController
public class PersonController {

	// On appelle un service pour nous remonter les infos dont on a besoin pour
	// les réponses d'API

	@Autowired
	private PersonService personService;

	// Vérification des données en entrée
	public void checkInputPerson(Person person) {

		if ("".equals(person.getFirstName()) || person.getFirstName() == null
				|| "".equals(person.getLastName())
				|| person.getLastName() == null) {
			throw new IllegalArgumentException(
					"Le nom ET le prénom sont obligatoires !!");
		}
	}

	// Création personne
	@PostMapping(path = "person")
	@ResponseStatus(HttpStatus.CREATED)
	public void createPerson(@RequestBody @Valid Person person) {

		// checkInputPerson(person);
		personService.createPerson(person);
	}

	// MAJ personne
	@PutMapping(path = "person")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePerson(@RequestBody @Valid Person person) {

		// checkInputPerson(person);
		personService.updatePerson(person);
	}

	// Suppression personne

	@DeleteMapping(path = "person")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public void deletePerson(@RequestBody @Valid Person person) {

		// checkInputPerson(person);
		personService.deletePerson(person);
	}

	@GetMapping(path = "communityEmail")
	@ResponseStatus(HttpStatus.OK)
	public Collection<String> getCommunityEmail(@RequestParam String city) {

		Collection<String> communityEmail = personService
				.getCommunityEmail(city);

		return communityEmail;
	}

	@GetMapping(path = "person")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getPerson() {

		List<String> person = personService.getPerson();

		return person;
	}

	@GetMapping(path = "personInfo")
	@ResponseStatus(HttpStatus.OK)
	public List<PersonInfo> getPersonInfo(@RequestParam String lastname,
			@RequestParam(required = false) String firstname) {

		List<PersonInfo> person = personService.getPersonInfo(lastname,
				firstname);

		return person;
	}

	@GetMapping(path = "childAlert")
	@ResponseStatus(HttpStatus.OK)
	public List<ChildInfo> getChildByAddress(@RequestParam String address) {

		List<ChildInfo> child = personService.getChildByAddress(address);

		return child;
	}

}