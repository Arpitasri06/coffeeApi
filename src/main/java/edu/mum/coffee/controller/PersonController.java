package edu.mum.coffee.controller;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.PersonServiceTest;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ProductService;

@RestController
@RequestMapping("/peoples")
public class PersonController {
	
	@Autowired
	private PersonService service;

	@GetMapping("/{id}")
	public Person getPersonById(@PathVariable int id) {
		return service.findById((long) id);
	}

	@DeleteMapping("/{id}")
	public void PersonsDelete(@PathVariable int id) {
		service.removePerson(service.findById((long) id));
	}

	@PostMapping
	public void PersontSave(@RequestBody @Valid Person people, BindingResult result) {
		if (!result.hasErrors()) {
			service.savePerson(people);
		}

	}

	@PutMapping("/{id}")
	public void personUpdate(@PathVariable long id, @RequestBody @Valid Person entity, BindingResult result) {
		if (!result.hasErrors()) {
			entity.setId(id);
			service.savePerson(entity);

		}

	}

}