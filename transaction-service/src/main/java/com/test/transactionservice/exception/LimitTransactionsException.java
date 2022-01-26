package com.test.transactionservice.exception;

public class LimitTransactionsException extends RuntimeException {
	
	public LimitTransactionsException() {
		super("Today you have reached the limit of allowed transactions, try again tomorrow.");
	}

	private static final long serialVersionUID = 1L;
	
	

}
