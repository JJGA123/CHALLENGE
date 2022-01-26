package com.test.authservice.exception;

public class UnauthorizedException extends RuntimeException {
	
	public UnauthorizedException(String details) {
		super(details.concat("User unauthorizaed."));
	}

	private static final long serialVersionUID = 1L;
	
	

}
