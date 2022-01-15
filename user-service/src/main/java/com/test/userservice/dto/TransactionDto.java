package com.test.userservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

	private double amount;
	private String currency;
	private String numberOrigin;
	private String numberDestination;
	private String description;
	private int userId;
	
}
