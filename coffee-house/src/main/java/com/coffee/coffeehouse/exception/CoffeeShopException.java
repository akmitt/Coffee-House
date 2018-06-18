package com.coffee.coffeehouse.exception;

import java.util.List;

import org.springframework.validation.FieldError;

public class CoffeeShopException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CoffeeShopException(String message) {
		super(message);
	}
	
}
