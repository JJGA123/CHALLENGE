package com.test.accountservice.exception;

public class AmountInvalidException extends RuntimeException {
	
	public AmountInvalidException() {
		super("The account amount cannot be less than 0.");
	}

	private static final long serialVersionUID = 1L;
	
	

}
