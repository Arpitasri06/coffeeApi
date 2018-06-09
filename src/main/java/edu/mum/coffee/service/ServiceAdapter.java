package edu.mum.coffee.service;

import java.util.List;
import java.util.function.*;

public class ServiceAdapter<T, K> {
	private Supplier<List<T>> getAllFunction;
	private Function<K, T> getByIdFunction;
	private Function<T, T> addFunction;
	private BiFunction<T, K, T> editFunction;
	private Consumer<K> deleteFunction;

	public ServiceAdapter<T, K> getAll(Supplier<List<T>> getAllFunction){
		this.getAllFunction = getAllFunction;
		return this;
	}
	
	public Supplier<List<T>> getAll() {
		return getAllFunction;
	}

	public  ServiceAdapter<T, K> getById(Function<K, T> getByIdFunction) {
		this.getByIdFunction = getByIdFunction;
		return this;
	}
	
	public Function<K, T> getById() {
		return getByIdFunction;
	}

	public ServiceAdapter<T, K> add(Function<T, T> addFunction) {
		this.addFunction = addFunction;
		return this;
	}
	
	public Function<T, T> add() {
		return addFunction;
	}
	
	public ServiceAdapter<T, K> edit(BiFunction<T, K, T> editFunction){
		this.editFunction = editFunction;
		return this;
	}
	
	public BiFunction<T,K,T> edit(){
		return editFunction;
	}

	public ServiceAdapter<T, K> delete(Consumer<K> deleteFunction) {
		this.deleteFunction = deleteFunction;
		return this;
	}
	
	public Consumer<K> delete() {
		return deleteFunction;
	}
}
