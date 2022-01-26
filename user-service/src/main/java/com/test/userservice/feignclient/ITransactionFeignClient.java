package com.test.userservice.feignclient;

import java.util.List;

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
	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/transactions")
	ResponseDto sendTransaction(@RequestBody TransactionDto transaction);
	
	/**
	 * GetMapping method to receive the request
	 * @param numberOrigin text string with number origin
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/transactions/numberOrigin/{numberOrigin}")
	List<TransactionDto> getTransactionByNumberOrigin(@PathVariable("numberOrigin") String numberOrigin);
	
}
