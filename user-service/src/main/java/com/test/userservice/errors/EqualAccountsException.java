package com.test.userservice.errors;

public class EqualAccountsException extends RuntimeException {
	
	public EqualAccountsException() {
		super("Origin and destination account are the same, the transaction cannot be carried out.");
	}

	private static final long serialVersionUID = 1L;
	
	

}
