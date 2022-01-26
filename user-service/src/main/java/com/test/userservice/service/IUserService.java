package com.test.userservice.service;

import java.util.List;
import java.util.Map;

import com.test.userservice.dto.AccountDto;
import com.test.userservice.dto.ResponseDto;
import com.test.userservice.dto.TransactionDto;
import com.test.userservice.dto.UserDto;

public interface IUserService {

	/**
	 * Specification getAll: get the users
	 * @return User information list
	 */
	public List<UserDto> getAll();
	
	/**
	 * Specification getUserById: get the user with filter by id
	 * @param id identifier user for get
	 * @return User information
	 */
	public UserDto getUserById(int id);
	
	/**
	 * Specification getUserByEmail: get the user with filter by email
	 * @param email email user for get
	 * @return User information
	 */
	public UserDto getUserByEmail(String email);
	
	/**
	 * Specification save: save the new user
	 * @param user object that contains the information of new user
	 * @return New user information
	 */
	public UserDto save(UserDto user);
	
	/**
	 * Implementation edit: save the new user information
	 * @param user object that contains the new user information
	 * @return New user information
	 */
	public UserDto edit(UserDto user);
	
	/**
	 * Specification getAccounts: get the accounts with filter by userId
	 * @param userId identifier user by filter
	 * @return Accounts information list
	 */
	public List<AccountDto> getAccounts(int userId);
	
	/**
	 * Specification getTransactions: get the transactions with filter by userId
	 * @param userId identifier user by filter
	 * @return Transaction information list
	 */
	public List<TransactionDto> getTransactions(int userId);
	
	/**
	 * Specification saveAccount: save the new account
	 * @param userId identifier user by associate account
	 * @param account object that contains the information of new account
	 * @return The response object with information details
	 */
	public ResponseDto saveAccount(int userId, AccountDto account);
	
	/**
	 * Specification sendTransaction: send the new transaction
	 * @param transaction object that contains the information of new transaction
	 * @return The response object with information details
	 */
	public ResponseDto sendTransaction(TransactionDto transaction);
	
	/**
	 * Specification getUserAll: get all information with filter userId
	 * @param userId identifier user by filter information
	 * @return The response object with information details
	 */
	public Map<String, Object> getUserAll(int userId);
	
}
