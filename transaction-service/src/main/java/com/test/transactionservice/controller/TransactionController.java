package com.test.transactionservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.transactionservice.dto.ResponseDto;
import com.test.transactionservice.dto.TransactionDto;
import com.test.transactionservice.service.ITransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

	private final ITransactionService transactionService;
	
	@Autowired
	public TransactionController(ITransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	/**
	 * GetMapping method to receive the request
	 * @return The response object with information details
	 */
	@Operation(summary = "Get the transactions",tags = {"transactions"},description = "Get all transactions created.",
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
		List<TransactionDto> transactions = transactionService.getAll();
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",transactions);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param numberOrigin text string with number origin
	 * @return The response object with information details
	 */
	@Operation(summary = "Get the transactions by number origin",tags = {"transactions"},description = "Get all transactions by number origin.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@GetMapping("/numberOrigin/{numberOrigin}")
	public ResponseEntity<List<TransactionDto>> getTransactionByNumberOrigin(
			@Parameter(description = "Number account origin.", required = true) @PathVariable("numberOrigin") String numberOrigin){
		List<TransactionDto> transactions = transactionService.getTransactionByNumberOrigin(numberOrigin);
		return ResponseEntity.ok(transactions);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param transaction object that contains the transaction information
	 * @return The response object with information details
	 */
	@Operation(summary = "Save a transaction",tags = {"transactions"},description = "Save the new transaction.",
	        responses = {
	        		@ApiResponse(responseCode  = "200", description  = "Successful operation."),
	    			@ApiResponse(responseCode  = "400", description  = "Poorly structured message."),
	    			@ApiResponse(responseCode  = "401", description  = "Not authorized."),
	    			@ApiResponse(responseCode  = "403", description  = "Prohibited resource."),
	    			@ApiResponse(responseCode  = "404", description  = "Resource Not Found."),
	    			@ApiResponse(responseCode  = "500", description  = "Server error.")
			})
	@PostMapping()
	public ResponseEntity<ResponseDto> create(
			@Parameter(description = "Object that contains the new information transaction.", required = true) @Valid @RequestBody TransactionDto transaction){
		TransactionDto transactionNew = transactionService.save(transaction);
		ResponseDto reponse = new ResponseDto(HttpStatus.CREATED.getReasonPhrase(), null, "Successful operation.",transactionNew);
		return ResponseEntity.ok(reponse);
	}
			
}
