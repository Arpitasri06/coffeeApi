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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductService service;

	@GetMapping
	public List<Product> getProducts() {
		return service.getAllProduct();
	}

	@GetMapping("/{id}")
	public Product getProductsById(@PathVariable int id) {
		return service.getProduct(id);
	}

	@DeleteMapping("/{id}")
	public void ProductsDelete(@PathVariable int id) {
		service.delete(service.getProduct(id));
	}

	@PostMapping
	public void ProductSave(@RequestBody @Valid Product product, BindingResult result) {
		if (!result.hasErrors()) {
			service.save(product);
		}

	}

	@PutMapping("/{id}")
	public void ProductUpdate(@PathVariable int id, @RequestBody @Valid Product entity, BindingResult result) {
		if (!result.hasErrors()) {
			entity.setId(id);
			service.save(entity);

		}

	}

}