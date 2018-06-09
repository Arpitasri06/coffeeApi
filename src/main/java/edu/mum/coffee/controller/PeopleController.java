package edu.mum.coffee.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.mum.coffee.domain.*;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ServiceAdapter;

@RestController
@RequestMapping("/people")
public class PeopleController extends ControllerCommon<Person, Long> {
	
	private PersonService service;
	
	public PeopleController(PersonService service) {
		super(new ServiceAdapter<Person, Long>()
				.getAll(() -> service.getAllPeople())
				.getById((Long id) -> service.findById(id))
				.add((Person p) -> { 
					p.setAdmin(false);
					service.savePerson(p);
					return p;
				})
				.edit((Person p, Long id) -> {
					p.setId(id);
					service.savePerson(p);
					return p;
				})
				.delete((Long id) -> {
					Person p = service.findById(id);
					service.removePerson(p);
				})
		);
		
		this.service = service;
	}

	@PostMapping({"/getByEmail"})
	public Person getByEmail(@RequestBody String email) {
		return service.findByEmail(email).stream().findFirst().orElse(null);
	}
	
	@PostMapping({"/login"})
	public Person login(@RequestBody Person user) {
		List<Person> personDb = service.findByEmail(user.getEmail());
		return personDb.stream().filter(p -> p.getPassword().equals(user.getPassword())).findFirst().orElse(null);
	}
	
	@PostMapping({"/admin"})
	public Person postAdmin(@RequestBody @Valid Person entity, BindingResult result, HttpServletResponse response) {
		if (result.hasErrors()) {
			return unprocessableEntity(response);
		}
		
		entity.setAdmin(true);
		
		return service.savePerson(entity);
	}
}
