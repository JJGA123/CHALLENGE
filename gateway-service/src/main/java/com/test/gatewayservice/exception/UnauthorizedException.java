package com.test.gatewayservice.exception;

public class UnauthorizedException extends RuntimeException {
	
	public UnauthorizedException() {
		super("The user does not have sufficient permissions.");
	}

	private static final long serialVersionUID = 1L;
	
	

}
