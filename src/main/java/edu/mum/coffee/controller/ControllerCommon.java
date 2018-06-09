package edu.mum.coffee.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import edu.mum.coffee.service.ServiceAdapter;

public abstract class ControllerCommon<T, K> {

	private ServiceAdapter<T, K> serviceAdapter;

	public ControllerCommon(ServiceAdapter<T, K> serviceAdapter) {
		this.serviceAdapter = serviceAdapter;
	}

	@RequestMapping(value = "*", method = RequestMethod.GET)
	public List<T> redirectDefault() {
		return get();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<T> get() {
		return serviceAdapter.getAll().get();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public T get(@PathVariable K id) {
		return serviceAdapter.getById().apply(id);
	}

	@RequestMapping(value = "*", method = RequestMethod.POST)
	public T redirectDefault(@Valid T entity, BindingResult result, HttpServletResponse response) {
		return post(entity, result, response);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public T post(@RequestBody @Valid T entity, BindingResult result, HttpServletResponse response) {
		if (result.hasErrors()) {
			return unprocessableEntity(response);
		}

		return serviceAdapter.add().apply(entity);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public T put(@PathVariable K id, @RequestBody @Valid T entity, BindingResult result, HttpServletResponse response) {
		if (result.hasErrors()) {
			return unprocessableEntity(response);
		}

		if (serviceAdapter.getById().apply(id) == null) {
			return notFound(response);
		}

		return serviceAdapter.edit().apply(entity, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable K id, HttpServletResponse response) {
		T entity = serviceAdapter.getById().apply(id);

		if (entity == null) {
			notFound(response);
			return;
		}

		serviceAdapter.delete().accept(id);
	}

	public T unprocessableEntity(HttpServletResponse response) {
		response.setStatus(422);
		return null;
	}

	public T notFound(HttpServletResponse response) {
		response.setStatus(404);
		return null;
	}
}
