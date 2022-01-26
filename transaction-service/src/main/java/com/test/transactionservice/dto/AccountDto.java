package com.test.transactionservice.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Schema(description = "Number account", allowableValues = "1001001")
	private String numberAccount;
	
	@Schema(description = "Currency according to account", allowableValues = "COP,USD,EUR")
	private String currency;
	
	@Schema(description = "Amount according to account", allowableValues = "100000.00")
	private double amount;
	
	@Schema(description = "Identifier of the user who owns the account", allowableValues = "1")
	private int userId;
	
	
}
