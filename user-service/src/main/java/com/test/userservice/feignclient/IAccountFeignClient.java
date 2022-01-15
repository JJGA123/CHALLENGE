package com.test.userservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.userservice.dto.AccountDto;
import com.test.userservice.dto.ResponseDto;

@FeignClient(name="account-service")
public interface IAccountFeignClient {

	/**
	 * PostMapping method to receive the request
	 * @param account object that contains the account information
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.POST, value = "account/save")
	ResponseDto save(@RequestBody AccountDto account);
	
	/**
	 * GetMapping method to receive the request
	 * @param numberAccount text string with the number account
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "account/getByNumberAccount/{numberAccount}")
	AccountDto getByNumberAccount(@PathVariable("numberAccount") String numberAccount);
	
	/**
	 * GetMapping method to receive the request
	 * @param numberAccount text string with the identifier user
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "account/getByUserId/{userId}")
	ResponseDto getAccounts(@PathVariable int userId);
	
	/**
	 * PutMapping method to receive the request
	 * @param numberAccount text string with the identifier account
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "account/edit/{numberAccount}")
	ResponseDto update(@RequestBody AccountDto account,@PathVariable String numberAccount);
}
