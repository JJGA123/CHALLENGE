package com.test.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDto {

	private String status;
	private String errors;
	private String details;
	private Object objects;
	
	public ResponseDto(String status, String errors, String details) {
		super();
		this.status = status;
		this.errors = errors;
		this.details = details;
	}
	
	
	
}
