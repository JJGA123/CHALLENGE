package com.test.accountservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.accountservice.dto.RsAccountDto;
import com.test.accountservice.dto.ResponseDto;
import com.test.accountservice.dto.RqAccountDto;
import com.test.accountservice.service.IAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	private final IAccountService accountService;
		
	@Autowired
	public AccountController(IAccountService accountService) {
		this.accountService = accountService;
	}
	
	/**
	 * GetMapping method to receive the request
	 * @return The response object with information details
	 */
	@Operation(summary = "Get all accounts",tags = {"accounts"},description = "Get all accounts created.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@GetMapping()
	public ResponseEntity<ResponseDto> getAccounts(){
		List<RsAccountDto> accounts = accountService.getAll();
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",accounts);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param numberAccount text string with number account
	 * @return The response object with information details
	 */
	@Operation(summary = "Get account by numberAccount",tags = {"accounts"},description = "Get the account with filter by numberAccount.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@GetMapping("/numberAccount/{numberAccount}")
	public ResponseEntity<RsAccountDto> getAccountByNumber(
			@Parameter(description = "Number account for get.", required = true) @PathVariable("numberAccount") String numberAccount){
		RsAccountDto account = accountService.getAccountByNumberAccount(numberAccount);
		return ResponseEntity.ok(account);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param account object that contains the account information
	 * @return The response object with information details
	 */
	@Operation(summary = "Save a account",tags = {"accounts"},description = "Save the new account.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@PostMapping()
	public ResponseEntity<ResponseDto> createAccount(
			@Parameter(description = "Object that contains the information of new account.", required = true) @Valid @RequestBody RqAccountDto account){
		RsAccountDto accountNew = accountService.save(account);
		ResponseDto reponse = new ResponseDto(HttpStatus.CREATED.getReasonPhrase(), null, "Successful operation.",accountNew);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with user identifier
	 * @return Objects list with information details
	 */
	@Operation(summary = "Get accounts with filter userId",tags = {"accounts"},description = "Get accounts with filter by userId.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@GetMapping("/userId/{userId}")
	public ResponseEntity<List<RsAccountDto>> getAccountByUserId(
			@Parameter(description = "Identifier user by filter.", required = true) @PathVariable("userId") int userId){
		List<RsAccountDto> account = accountService.getAccountByUserId(userId);
		return ResponseEntity.ok(account);
	}
	
	/**
	 * PutMapping method to receive the request
	 * @param account object that contains the new information account
	 * @param numberAccount text string with number account to edit
	 * @return The response object with information details
	 */
	@Operation(summary = "Edit the information account",tags = {"accounts"},description = "Edit the information account.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@PutMapping("/{numberAccount}")
	public ResponseEntity<ResponseDto> editInformation(
			@Parameter(description = "Object that contains the new information account.", required = true) @Valid @RequestBody RqAccountDto account,
			@Parameter(description = "Number account to edit.", required = true) @PathVariable("numberAccount") String numberAccount){
		RsAccountDto updateAccount = accountService.edit(account,numberAccount);
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",updateAccount);
		return ResponseEntity.ok(reponse);
	}
	
}
