package com.test.userservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.userservice.dto.AccountDto;
import com.test.userservice.dto.ResponseDto;
import com.test.userservice.dto.TransactionDto;
import com.test.userservice.dto.UserDto;
import com.test.userservice.entity.UserEntity;
import com.test.userservice.exception.ExistException;
import com.test.userservice.exception.NotExistException;
import com.test.userservice.feignclient.ITransactionFeignClient;
import com.test.userservice.feignclient.IAccountFeignClient;
import com.test.userservice.repository.IUserRepository;
import com.test.userservice.service.IUserService;
import com.test.userservice.util.Mapper;

@Service
public class UserService implements IUserService{
	
	@Value("${validate.limitDaily}")
	private int limitDaily;
	
	@Value("${taxe.higher}")
	private double taxeHigher;
	
	@Value("${taxe.lower}")
	private double taxeLower;
	
	private final IUserRepository userRepository;
	private final IAccountFeignClient accountFeignClient;
	private final ITransactionFeignClient transactionFeignClient;
	private final Mapper mapper;
	
	@Autowired
	public UserService(IUserRepository userRepository,IAccountFeignClient accountFeignClient,ITransactionFeignClient transactionFeignClient,Mapper mapper) {
		this.userRepository = userRepository;
		this.accountFeignClient = accountFeignClient;
		this.transactionFeignClient = transactionFeignClient;
		this.mapper = mapper;
	}
	
	/**
	 * Implementation getAll: get the users
	 * @return User information list
	 */
	@Override
	public List<UserDto> getAll(){
		List<UserEntity> users = userRepository.findAll();
		if(users.isEmpty()) {
			throw new NotExistException("");
		}
		List<UserDto> usersDto = users.stream()
		          .map(mapper::toDto)
		          .collect(Collectors.toList());
		return usersDto;
	}
	
	/**
	 * Implementation getUserById: get the user with filter by id
	 * @param id identifier user for get
	 * @return User information
	 */
	@Override
	public UserDto getUserById(int id) {
		UserEntity user = userRepository.findByIdUser(id);
		if(user==null) {
			throw new NotExistException(" by user identifier "+id);
		}
		return mapper.toDto(user);
	}
	
	/**
	 * Implementation getUserByEmail: get the user with filter by email
	 * @param email email user for get
	 * @return User information
	 */
	@Override
	public UserDto getUserByEmail(String email) {
		UserEntity user = userRepository.findByEmail(email);
		if(user==null) {
			throw new NotExistException(" by email "+email);
		}
		return mapper.toDto(user);
	}
	
	/**
	 * Implementation save: save the new user
	 * @param user object that contains the information of new user
	 * @return New user information
	 */
	@Override
	public UserDto save(UserDto user) {
		UserEntity oldUser = userRepository.findByEmail(user.getEmail());
		if(oldUser!=null) {
			throw new ExistException();
		}
		oldUser = userRepository.findByNameUser(user.getNameUser());
		if(oldUser!=null) {
			throw new ExistException();
		}
		UserEntity newUser = userRepository.save(mapper.toEntity(user));
		return mapper.toDto(newUser);
	}
	
	/**
	 * Implementation edit: save the new user information
	 * @param user object that contains the new user information
	 * @return New user information
	 */
	@Override
	public UserDto edit(UserDto user) {
		UserEntity oldUser = userRepository.findByEmail(user.getEmail());
		if(oldUser==null) {
			throw new NotExistException(" by user ");
		}
		oldUser.setLastDate(user.getLastDate());
		oldUser.setLimitDaily(user.getLimitDaily());
		oldUser = userRepository.save(oldUser);
		return mapper.toDto(oldUser);
	}
	
	/**
	 * Implementation getAccounts: get the accounts with filter by userId
	 * @param userId identifier user by filter
	 * @return The response object with information details
	 */
	@Override
	public List<AccountDto> getAccounts(int userId){
		UserEntity user = userRepository.findByIdUser(userId);
		if(user==null) {
			throw new NotExistException(" by user identifier "+userId);
		}
		List<AccountDto> accounts = new ArrayList<>();
		try {
			accounts = accountFeignClient.getAccounts(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return accounts;
	}
	
	/**
	 * Implementation getTransactions: get the transactions with filter by userId
	 * @param userId identifier user by filter
	 * @return The response object with information details
	 */
	@Override
	public List<TransactionDto> getTransactions(int userId){
		UserEntity user = userRepository.findByIdUser(userId);
		if(user==null) {
			throw new NotExistException(" user with identifier "+userId);
		}
		List<AccountDto> accounts = accountFeignClient.getAccounts(userId);
		List<TransactionDto> transactions = new ArrayList<>();
		if(!accounts.isEmpty()) {
			accounts.stream().forEach((account)-> {
				try {
					transactions.addAll(transactionFeignClient.getTransactionByNumberOrigin(account.getNumberAccount()));
				} catch (Exception e) {
				}
				
			});
		}
		return transactions;
	}
	
	/**
	 * Implementation saveAccount: save the new account
	 * @param userId identifier user by associate account
	 * @param account object that contains the information of new account
	 * @return The response object with information details
	 */
	@Override
	public ResponseDto saveAccount(int userId, AccountDto account) {
		if(this.getUserById(userId)==null) {
			throw new NotExistException(" user with identifier "+userId);
		}
		account.setUserId(userId);
		ResponseDto response = accountFeignClient.save(account);
		return response;
	}
	
	/**
	 * Implementation sendTransaction: send the new transaction
	 * @param transaction object that contains the information of new transaction
	 * @return The response object with information details
	 */
	@Override
	public ResponseDto sendTransaction(TransactionDto transaction) {
		ResponseDto response = transactionFeignClient.sendTransaction(transaction);
		return response;
	}
	
	/**
	 * Implementation getUserAll: get all information with filter userId
	 * @param userId identifier user by filter information
	 * @return The response object with information details
	 */
	@Override
	public Map<String, Object> getUserAll(int userId){
		Map<String, Object> result = new HashMap<>();
		UserEntity user = userRepository.findByIdUser(userId);
		if(user==null) {
			throw new NotExistException(" user with identifier "+userId);
		}
		result.put("User", mapper.toDto(user));
		List<AccountDto> accounts = accountFeignClient.getAccounts(userId);
		if(accounts.isEmpty()) {
			result.put("Accounts", "The user hasn't accounts");
		}else {
			result.put("Accounts", accounts);
		}
		List<TransactionDto> transactions = new ArrayList<>();
		
		accounts.stream().forEach((account)-> {
			try {
				transactions.addAll(transactionFeignClient.getTransactionByNumberOrigin(account.getNumberAccount()));
			} catch (Exception e) {
			}
			
		});
		
		if(transactions.isEmpty()) {
			result.put("Transactions", "The user hasn't transactions");
		}else {
			result.put("Transactions", transactions);
		}
		
		return result;
		
	}
	
}
