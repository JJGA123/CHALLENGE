package com.test.userservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.userservice.dto.ResponseDto;
import com.test.userservice.dto.TransactionDto;

@FeignClient(name="transaction-service")
public interface ITransactionFeignClient {

	/**
	 * PostMapping method to receive the request
	 * @param transaction object that contains the transaction information
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.POST, value = "transaction/save")
	ResponseDto save(@RequestBody TransactionDto transaction);
	
	/**
	 * GetMapping method to receive the request
	 * @param userId text string with the identifier user
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "transaction/getByUserId/{userId}")
	ResponseDto getTransactions(@PathVariable int userId);
	
}
