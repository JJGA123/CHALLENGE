package com.test.transactionservice.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Amount transaction", allowableValues = "100000.00")
	private double amount;
	
	@Schema(description = "Currency transaction", allowableValues = "COP,USD,EUR")
	private String currency;
	
	@Schema(description = "Origin account number of the transaction", allowableValues = "1001001")
	private String numberOrigin;
	
	@Schema(description = "Destinaction account number of the transaction", allowableValues = "1001002")
	private String numberDestination;
	
	@Schema(description = "Description transaction", allowableValues = "This transaction is done to pay bills")
	private String description;
	
}
