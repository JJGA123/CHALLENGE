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
public class TokenDto {

	@Schema(description = "Valid token", allowableValues = "eyJhbGciOiJIUzI1NiJ9")
    private String token;
}