package com.test.accountservice.controller;

import java.util.List;

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

import com.test.accountservice.dto.ResponseDto;
import com.test.accountservice.entity.AccountEntity;
import com.test.accountservice.errors.AmountInvalidException;
import com.test.accountservice.errors.ExistException;
import com.test.accountservice.errors.NotExistException;
import com.test.accountservice.service.IAccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	IAccountService accountService;
	
	/**
	 * GetMapping method to receive the request
	 * @return The response object with information details
	 */
	@GetMapping("/accounts")
	public ResponseEntity<ResponseDto> getAll(){
		List<AccountEntity> accounts = accountService.getAll();
		if(accounts.isEmpty()) {
			throw new NotExistException("");
		}
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",accounts);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param numberAccount text string with number account
	 * @return The response object with information details
	 */
	@GetMapping("/getByNumberAccount/{numberAccount}")
	public ResponseEntity<AccountEntity> getByNumberAccount(@PathVariable("numberAccount") String numberAccount){
		AccountEntity account = accountService.getAccountByNumberAccount(numberAccount);
		if(account==null) {
			throw new NotExistException(" by account number "+numberAccount);
		}
		return ResponseEntity.ok(account);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param account object that contains the account information
	 * @return The response object with information details
	 */
	@PostMapping("/save")
	public ResponseEntity<ResponseDto> save(@RequestBody AccountEntity account){
		AccountEntity accountNew = accountService.getAccountByNumberAccount(account.getNumberAccount());
		if(accountNew!=null) {
			throw new ExistException("Account "+accountNew.getNumberAccount());
		}
		if(account.getAmount()<0) {
			throw new AmountInvalidException();
		}
		accountNew = accountService.save(account);
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",accountNew);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with user identifier
	 * @return The response object with information details
	 */
	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<ResponseDto> getByUserId(@PathVariable("userId") int userId){
		List<AccountEntity> account = accountService.getAccountByUserId(userId);
		if(account.isEmpty()) {
			throw new NotExistException(" by user identifier "+userId);
		}
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",account);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * PutMapping method to receive the request
	 * @param account object that contains the new information account
	 * @param numberAccount text string with number account to edit
	 * @return The response object with information details
	 */
	@PutMapping("/edit/{numberAccount}")
	public ResponseEntity<ResponseDto> edit(@RequestBody AccountEntity account,@PathVariable("numberAccount") String numberAccount){
		AccountEntity updateAccount = accountService.getAccountByNumberAccount(numberAccount);
		
		updateAccount.setNumberAccount(account.getNumberAccount());
		updateAccount.setCurrency(account.getCurrency());
		updateAccount.setAmount(account.getAmount());
		
		accountService.save(updateAccount);
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",updateAccount);
		return ResponseEntity.ok(reponse);
	}
	
}
