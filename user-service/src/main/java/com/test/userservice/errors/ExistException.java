package com.test.userservice.errors;

public class ExistException extends RuntimeException {
	
	public ExistException() {
		super("El usuario ya fue registrado.");
	}

	private static final long serialVersionUID = 1L;
	
	

}
