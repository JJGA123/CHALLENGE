package com.test.userservice.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.userservice.dto.AccountDto;
import com.test.userservice.dto.ResponseDto;
import com.test.userservice.dto.TransactionDto;
import com.test.userservice.dto.UserDto;
import com.test.userservice.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Value("${validate.limitDaily}")
	private int limitDaily;
	
	private final IUserService userService;
	
	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}
	
	/**
	 * GetMapping method to receive the request
	 * @return The response object with information details
	 */
	@Operation(summary = "Get all users",tags = {"users"},description = "Get all users created.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@GetMapping()
	public ResponseEntity<ResponseDto> getAll(){
		List<UserDto> users = userService.getAll();
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",users);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param id text string with user identifier
	 * @return The response object with information details
	 */
	@Operation(summary = "Get user by id",tags = {"users"},description = "Get the user with filter by id.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@GetMapping("/id/{id}")
	public ResponseEntity<UserDto> getById(
			@Parameter(description = "Identifier user.", required = true) @PathVariable("id") int id){
		UserDto user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param user object that contains the user information
	 * @return The response object with information details
	 */
	@Operation(summary = "Save a user",tags = {"users"},description = "Save the new user.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@PostMapping()
	public ResponseEntity<ResponseDto> save(
			@Parameter(description = "Object that contains the information of new user.", required = true) @Valid @RequestBody UserDto user){
		UserDto userNew = userService.save(user);
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",userNew);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * PutMapping method to receive the request
	 * @param user object that contains the user information
	 * @return The response object with information details
	 */
	@Operation(summary = "Update a user",tags = {"users"},description = "Update the new user.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@PutMapping()
	public ResponseEntity<ResponseDto> edit(
			@Parameter(description = "Object that contains user information.", required = true) @Valid @RequestBody UserDto user){
		UserDto userNew = userService.edit(user);
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",userNew);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @return The response object with information details
	 */
	@Operation(summary = "Get all accounts by userId",tags = {"users"},description = "Get all accounts by userId.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@GetMapping("/accounts/{userId}")
	public ResponseEntity<ResponseDto> getAccounts(
			@Parameter(description = "Identifier user.", required = true) @PathVariable("userId") int userId){
		List<AccountDto> accounts = userService.getAccounts(userId);
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",accounts);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @return The response object with information details
	 */
	@Operation(summary = "Get all transactions by userId",tags = {"users"},description = "Get all transactions by userId.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@GetMapping("/transactions/{userId}")
	public ResponseEntity<ResponseDto> getTransactions(
			@Parameter(description = "Identifier user.", required = true) @PathVariable("userId") int userId){
		List<TransactionDto> transactions = userService.getTransactions(userId);
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",transactions);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @param account object that contains the account information
	 * @return The response object with information details
	 */
	@Operation(summary = "Save a account",tags = {"users"},description = "Save the new account.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@PostMapping("/createAccount/{userId}")
	public ResponseEntity<ResponseDto> createAccount(
			@Parameter(description = "Identifier user.", required = true) @PathVariable("userId") int userId, 
			@Parameter(description = "Object that contains the user information.", required = true) @Valid @RequestBody AccountDto account){
		ResponseDto response = userService.saveAccount(userId, account);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param transaction object that contains the transaction information
	 * @return The response object with information details
	 */
	@Operation(summary = "Send a transaction",tags = {"users"},description = "Send the new transaction.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@PostMapping("/sendTransaction")
	public ResponseEntity<ResponseDto> sendTransaction(
			@Parameter(description = "Object that contains the transaction information.", required = true) @Valid @RequestBody TransactionDto transaction){
		ResponseDto response = userService.sendTransaction(transaction);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @return The response object with information details
	 */
	@Operation(summary = "Get all information by userId",tags = {"users"},description = "Get all information by userId.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@GetMapping("/allUserId/{userId}")
	public ResponseEntity<Map<String, Object>> getAllInformation(
			@Parameter(description = "Identifier user.", required = true) @PathVariable("userId") int userId){
		Map<String, Object> result = userService.getUserAll(userId);
		return ResponseEntity.ok(result);
	}
}
