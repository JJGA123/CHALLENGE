package com.test.transactionservice.dto;

import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Schema(description = "User name", allowableValues = "Jhon Galvis")
	private String nameUser;
	
	@Schema(description = "Email user", allowableValues = "sistemasjhoning@gmail.com")
	private String email;
	
	@Schema(description = "Limit daily by transactions", allowableValues = "3")
	private int limitDaily;
	
	@Schema(description = "Date last transaction Successful", allowableValues = "25/01/2022")
	private LocalDate lastDate;
	
	@Schema(description = "Password user", allowableValues = "password")
	private String password;
	
	@Schema(description = "Role user", allowableValues = "admin")
    private String role;
	
}
