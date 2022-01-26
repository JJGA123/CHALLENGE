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
public class NewUserDto {

	@Schema(description = "Name of the new user", allowableValues = "Jhon")
    private String nameUser;
	
	@Schema(description = "Password of the new user", allowableValues = "password")
    private String password;
	
	@Schema(description = "Role of the new user", allowableValues = "admin")
    private String role;
}
