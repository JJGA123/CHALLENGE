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
public class UserDto {

	@Schema(description = "User name", allowableValues = "Jhon")
    private String nameUser;
	
	@Schema(description = "User password", allowableValues = "password")
    private String password;
}
