package com.test.userservice.errors;

public class InsufficientFoundException extends RuntimeException {
	
	public InsufficientFoundException() {
		super(("Insufficient funds to complete the transaction."));
	}

	private static final long serialVersionUID = 1L;
	
	

}
