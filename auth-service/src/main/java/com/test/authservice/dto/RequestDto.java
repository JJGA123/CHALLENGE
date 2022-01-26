package com.test.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestDto {
	
	@Schema(description = "URI to validate", allowableValues = "/api/v1/auths")
	private String uri;
	
	@Schema(description = "Method to validate", allowableValues = "GET")
	private String method;
}
