package edu.mum.coffee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.mum.coffee.domain.*;
import edu.mum.coffee.service.OrderService;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ServiceAdapter;

@RestController
@RequestMapping("/orders")
public class OrdersController extends ControllerCommon<Order, Integer> {
	
	OrderService orderService;
	PersonService personService;
	
	public OrdersController(OrderService service, PersonService personService) {
		super(new ServiceAdapter<Order, Integer>()
				.getAll(() -> service.findAll())
				.getById((Integer id) -> {
					Order order = service.findById(id);

					for(Orderline l : order.getOrderLines()) {
						l.setOrder(null);
					}
					
					return order;
				})
				.add((Order p) -> { 
					
					for(Orderline l : p.getOrderLines()) {
						l.setOrder(p);
					}
					
					service.save(p);

					for(Orderline l : p.getOrderLines()) {
						l.setOrder(null);
					}
					
					return p;
				})
				.edit((Order p, Integer id) -> {
					p.setId(id);

					for(Orderline l : p.getOrderLines()) {
						l.setOrder(p);
					}
					
					service.save(p);

					for(Orderline l : p.getOrderLines()) {
						l.setOrder(null);
					}
					
					return p;
				})
				.delete((Integer id) -> {
					Order o = service.findById(id);
					service.delete(o);
				})
		);
		
		this.orderService = service;
		this.personService = personService;
	}
	

	@RequestMapping(value = "/getByPerson/{personId}", method = RequestMethod.GET)
	public List<Order> get(@PathVariable long personId) {
		Person p = personService.findById(personId);
		List<Order> orders = orderService.findByPerson(p);

		for(Order o : orders) {
			for(Orderline l : o.getOrderLines()) {
				l.setOrder(null);
			}
		}
		
		return orders;
	}
}
