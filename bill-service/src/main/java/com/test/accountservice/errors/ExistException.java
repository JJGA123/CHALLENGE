package com.test.accountservice.errors;

public class ExistException extends RuntimeException {
	
	public ExistException(String details) {
		super(details.concat(" already exists in the system."));
	}

	private static final long serialVersionUID = 1L;
	
	

}
