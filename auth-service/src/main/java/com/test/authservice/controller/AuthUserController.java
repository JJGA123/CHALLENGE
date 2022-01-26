package com.test.authservice.controller;

import com.test.authservice.dto.UserDto;
import com.test.authservice.dto.NewUserDto;
import com.test.authservice.dto.RequestDto;
import com.test.authservice.dto.TokenDto;
import com.test.authservice.service.IAuthUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthUserController {

    private final IAuthUserService authUserService;
    
    @Autowired
	public AuthUserController(IAuthUserService authUserService) {
		this.authUserService = authUserService;
	}
    
    /**
	 * PostMapping method to receive the request
	 * @param dto object that contains the user information
	 * @return The token requested
	 */
    @Operation(summary = "Save a account",tags = {"auths"},description = "Save the new account.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(
    		@Parameter(description = "Object that contains the user information.", required = true) @Valid @RequestBody UserDto dto){
        TokenDto tokenDto = authUserService.login(dto);
        return ResponseEntity.ok(tokenDto);
    }

    /**
	 * PostMapping method to receive the request
	 * @param token text string with the token to validate
	 * @param dto object that contains the information of validation
	 * @return The token requested
	 */
    @Operation(summary = "Save a account",tags = {"auths"},description = "Save the new account.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(
    		@Parameter(description = "Corresponding token for validation.", required = true) @RequestParam String token, 
    		@Parameter(description = "Object that contains the request information.", required = true) @Valid @RequestBody RequestDto dto){
        TokenDto tokenDto = authUserService.validate(token,dto);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    /**
	 * PostMapping method to receive the request
	 * @param dto object that contains the information of creation
	 * @return The user information
	 */
    @Operation(summary = "Save a account",tags = {"auths"},description = "Save the new account.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
    @PostMapping("/create")
    public ResponseEntity<NewUserDto> create(
    		@Parameter(description = "Object that contains the information of new user.", required = true) @Valid @RequestBody NewUserDto dto){
    	NewUserDto authUser = authUserService.save(dto);
        return ResponseEntity.ok(authUser);
    }
}
