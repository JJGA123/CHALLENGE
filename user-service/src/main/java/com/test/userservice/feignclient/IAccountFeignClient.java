package com.test.userservice.feignclient;

import java.util.List;

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
	@RequestMapping(method = RequestMethod.POST, value = "api/v1/accounts")
	ResponseDto save(@RequestBody AccountDto account);
	
	/**
	 * GetMapping method to receive the request
	 * @param numberAccount text string with the number account
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "api/v1/accounts/numberAccount/{numberAccount}")
	AccountDto getByNumberAccount(@PathVariable("numberAccount") String numberAccount);
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "api/v1/accounts/userId/{userId}")
	List<AccountDto> getAccounts(@PathVariable int userId);
	
	/**
	 * PutMapping method to receive the request
	 * @param account object that contains the account information
	 * @param numberAccount text string with the number account
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "api/v1/accounts/{numberAccount}")
	ResponseDto update(@RequestBody AccountDto account,@PathVariable String numberAccount);
}
