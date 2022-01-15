package com.test.userservice.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.userservice.dto.AccountDto;
import com.test.userservice.dto.ResponseDto;
import com.test.userservice.dto.TransactionDto;
import com.test.userservice.entity.UserEntity;
import com.test.userservice.errors.NotExistException;
import com.test.userservice.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Value("${validate.limitDaily}")
	private int limitDaily;
	
	@Autowired
	IUserService userService;
	
	/**
	 * GetMapping method to receive the request
	 * @return The response object with information details
	 */
	@GetMapping("/users")
	public ResponseEntity<ResponseDto> getAll(){
		List<UserEntity> users = userService.getAll();
		if(users.isEmpty()) {
			throw new NotExistException("");
		}
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",users);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param id text string with user identifier
	 * @return The response object with information details
	 */
	@GetMapping("/getById/{id}")
	public ResponseEntity<ResponseDto> getById(@PathVariable("id") int id){
		UserEntity user = userService.getUserById(id);
		if(user==null) {
			throw new NotExistException(" by user identifier "+id);
		}
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",user);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param user object that contains the user information
	 * @return The response object with information details
	 */
	@PostMapping("/save")
	public ResponseEntity<ResponseDto> save(@RequestBody UserEntity user){
		user.setLimitDaily(limitDaily);
		LocalDate lastDateTransaction = LocalDate.now();
		user.setLastDate(lastDateTransaction);
		UserEntity userNew = userService.save(user);
		ResponseDto reponse = new ResponseDto(HttpStatus.OK.getReasonPhrase(), null, "Successful operation.",userNew);
		return ResponseEntity.ok(reponse);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @return The response object with information details
	 */
	@GetMapping("/accounts/getByUserId/{userId}")
	public ResponseEntity<ResponseDto> getAccounts(@PathVariable("userId") int userId){
		UserEntity user = userService.getUserById(userId);
		if(user==null) {
			throw new NotExistException(" by user identifier "+userId);
		}
		ResponseDto accounts = userService.getAccounts(userId);
		return ResponseEntity.ok(accounts);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @return The response object with information details
	 */
	@GetMapping("/transactions/getByUserId/{userId}")
	public ResponseEntity<ResponseDto> getTransactions(@PathVariable("userId") int userId){
		UserEntity user = userService.getUserById(userId);
		if(user==null) {
			throw new NotExistException(" by user identifier "+userId);
		}
		ResponseDto transactions = userService.getTransactions(userId);
		return ResponseEntity.ok(transactions);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @param account object that contains the account information
	 * @return The response object with information details
	 */
	@PostMapping("/saveaccount/{userId}")
	public ResponseEntity<ResponseDto> save(@PathVariable("userId") int userId, @RequestBody AccountDto account){
		ResponseDto response = userService.saveAccount(userId, account);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * PostMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @param transaction object that contains the transaction information
	 * @return The response object with information details
	 */
	@PostMapping("/savetransaction/{userId}")
	public ResponseEntity<ResponseDto> save(@PathVariable("userId") int userId, @RequestBody TransactionDto transaction){
		ResponseDto response = userService.saveTransaction(userId, transaction);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @return The response object with information details
	 */
	@GetMapping("/getAll/{userId}")
	public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") int userId){
		Map<String, Object> result = userService.getUserAll(userId);
		return ResponseEntity.ok(result);
	}
}
