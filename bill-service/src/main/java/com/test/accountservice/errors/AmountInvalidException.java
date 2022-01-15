package com.test.accountservice.errors;

public class AmountInvalidException extends RuntimeException {
	
	public AmountInvalidException() {
		super("The account amount cannot be less than 0.");
	}

	private static final long serialVersionUID = 1L;
	
	

}
