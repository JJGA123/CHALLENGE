package com.test.gatewayservice.errors;

public class UnauthorizedException extends RuntimeException {
	
	public UnauthorizedException() {
		super("The user does not have sufficient permissions.");
	}

	private static final long serialVersionUID = 1L;
	
	

}
