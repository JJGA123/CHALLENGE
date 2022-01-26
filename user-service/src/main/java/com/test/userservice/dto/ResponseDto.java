package com.test.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDto {

	@Schema(description = "Status response", allowableValues = "OK")
	private String status;
	
	@Schema(description = "Error in consumption", allowableValues = "Information exists")
	private String errors;
	
	@Schema(description = "Exception detail", allowableValues = "Information exists")
	private String details;
	
	@Schema(description = "Objects response", allowableValues = "AccountDto.class")
	private Object objects;
	
	public ResponseDto(String status, String errors, String details) {
		super();
		this.status = status;
		this.errors = errors;
		this.details = details;
	}
	
	
	
}
