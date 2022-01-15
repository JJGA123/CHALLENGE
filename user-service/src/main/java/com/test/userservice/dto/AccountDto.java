package com.test.userservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

	private String numberAccount;
	private String currency;
	private double amount;
	private int userId;
	
	
}
