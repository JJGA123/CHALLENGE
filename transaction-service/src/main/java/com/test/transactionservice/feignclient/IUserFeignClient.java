package com.test.transactionservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.transactionservice.dto.ResponseDto;
import com.test.transactionservice.dto.UserDto;

@FeignClient(name="user-service")
public interface IUserFeignClient {

	/**
	 * GetMapping method to receive the request
	 * @param id text string with the user id
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "api/v1/users/id/{id}")
	UserDto getById(@PathVariable("id") int id);
	
	/**
	 * PutMapping method to receive the request
	 * @param user object that contains the user information
	 * @return The response object with information details
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "api/v1/users")
	ResponseDto edit(@RequestBody UserDto user);
}
