package com.test.transactionservice.errors;

public class NotExistException extends RuntimeException {
	
	public NotExistException(String details) {
		super(("The information consulted").concat(details).concat(" does not exist in the system."));
	}

	private static final long serialVersionUID = 1L;
	
	

}
