package com.test.transactionservice.exception;

public class InsufficientFoundException extends RuntimeException {
	
	public InsufficientFoundException() {
		super(("Insufficient funds to complete the transaction."));
	}

	private static final long serialVersionUID = 1L;
	
	

}
