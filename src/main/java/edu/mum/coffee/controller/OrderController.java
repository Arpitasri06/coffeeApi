package edu.mum.coffee.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@GetMapping
	public List<Order> findAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Order findById(@PathVariable int id) {
		return service.findById(id);
	}

	@DeleteMapping("/{id}")
	public void OrderDelete(@PathVariable int id) {
		service.delete(service.findById(id));
	}

	@PostMapping
	public void OrderSave(@RequestBody @Valid Order order, BindingResult result) {
		if (!result.hasErrors()) {
			service.save(order);
		}

	}

	@PutMapping("/{id}")
	public void OrderUpdate(@PathVariable int id, @RequestBody @Valid Order entity, BindingResult result) {
		if (!result.hasErrors()) {
			entity.setId(id);
			service.save(entity);

		}

	}
	
}