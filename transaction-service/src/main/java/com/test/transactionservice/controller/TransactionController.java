package com.test.transactionservice.controller;

import java.util.List;

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
import com.test.transactionservice.entity.TransactionEntity;
import com.test.transactionservice.errors.NotExistException;
import com.test.transactionservice.service.ITransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	ITransactionService transactionService;
	
	/**
	 * GetMapping method to receive the request
	 * @return The response object with information details
	 */
	@GetMapping("/transactions")
	public ResponseEntity<ResponseDto> getAll(){
		List<TransactionEntity> transactions = transactionService.getAll();
		if(transactions.isEmpty()) {
			throw new NotExistException("");
		}
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",transactions);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param transaction object that contains the transaction information
	 * @return The response object with information details
	 */
	@PostMapping("/save")
	public ResponseEntity<ResponseDto> save(@RequestBody TransactionEntity transaction){
		TransactionEntity transactionNew = transactionService.save(transaction);
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",transactionNew);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with user identifier
	 * @return The response object with information details
	 */
	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<ResponseDto> getByUserId(@PathVariable("userId") int userId){
		List<TransactionEntity> transaction = transactionService.getTransactionByUserId(userId);
		if(transaction.isEmpty()) {
			throw new NotExistException(" by user identifier "+userId);
		}
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",transaction);
		return ResponseEntity.ok(reponse);
	}
	
}
