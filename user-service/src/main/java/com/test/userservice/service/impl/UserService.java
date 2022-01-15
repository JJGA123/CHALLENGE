package com.test.userservice.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.userservice.constants.Constants;
import com.test.userservice.dto.AccountDto;
import com.test.userservice.dto.ResponseDto;
import com.test.userservice.dto.TransactionDto;
import com.test.userservice.entity.UserEntity;
import com.test.userservice.errors.AccountNotAssociatedException;
import com.test.userservice.errors.EqualAccountsException;
import com.test.userservice.errors.ExistException;
import com.test.userservice.errors.InsufficientFoundException;
import com.test.userservice.errors.LimitTransactionsException;
import com.test.userservice.errors.NotExistException;
import com.test.userservice.feignclient.ITransactionFeignClient;
import com.test.userservice.feignclient.IAccountFeignClient;
import com.test.userservice.repository.IUserRepository;
import com.test.userservice.service.IUserService;

@Service
public class UserService implements IUserService{
	
	@Value("${validate.limitDaily}")
	private int limitDaily;
	
	@Value("${taxe.higher}")
	private double taxeHigher;
	
	@Value("${taxe.lower}")
	private double taxeLower;
	
	@Autowired
	IUserRepository userRepository;
		
	@Autowired
	IAccountFeignClient accountFeignClient;
	
	@Autowired
	ITransactionFeignClient transactionFeignClient;
	
	/**
	 * Implementation getAll: get the users
	 * @return User information list
	 */
	@Override
	public List<UserEntity> getAll(){
		return userRepository.findAll();
	}
	
	/**
	 * Implementation getUserById: get the user with filter by id
	 * @param id identifier user for get
	 * @return User information
	 */
	@Override
	public UserEntity getUserById(int id) {
		return userRepository.findById(id).orElse(null);
	}
	
	/**
	 * Implementation getUserByEmail: get the user with filter by email
	 * @param email email user for get
	 * @return User information
	 */
	@Override
	public UserEntity getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}
	
	/**
	 * Implementation save: save the new user
	 * @param user object that contains the information of new user
	 * @return New user information
	 */
	@Override
	public UserEntity save(UserEntity user) {
		UserEntity oldUser = userRepository.findByEmail(user.getEmail()).orElse(null);
		if(oldUser!=null) {
			throw new ExistException();
		}
		oldUser = userRepository.findByNameUser(user.getNameUser()).orElse(null);
		if(oldUser!=null) {
			throw new ExistException();
		}
		UserEntity newUser = userRepository.save(user);
		return newUser;
	}
	
	/**
	 * Implementation getAccounts: get the accounts with filter by userId
	 * @param userId identifier user by filter
	 * @return The response object with information details
	 */
	@Override
	public ResponseDto getAccounts(int userId){
		ResponseDto accounts = accountFeignClient.getAccounts(userId);
		return accounts;
	}
	
	/**
	 * Implementation getTransactions: get the transactions with filter by userId
	 * @param userId identifier user by filter
	 * @return The response object with information details
	 */
	@Override
	public ResponseDto getTransactions(int userId){
		ResponseDto transactions = transactionFeignClient.getTransactions(userId);
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
	 * Implementation saveTransaction: save the new transaction
	 * @param userId identifier user by associate transaction
	 * @param transaction object that contains the information of new transaction
	 * @return The response object with information details
	 */
	@Override
	public ResponseDto saveTransaction(int userId, TransactionDto transaction) {
		UserEntity user = this.getUserById(userId);
		LocalDate lastDateTransaction = LocalDate.now();
		
		if(user==null) {
			throw new NotExistException(" user with identifier "+userId);
		}
		if(user.getLimitDaily()==0) {
			if(lastDateTransaction.equals(LocalDate.now())) {
				throw new LimitTransactionsException();
			} else {
				user.setLastDate(LocalDate.now());
				user.setLimitDaily(limitDaily);
			}
		} else {
			if(lastDateTransaction.equals(LocalDate.now())) {
				user.setLimitDaily(user.getLimitDaily()-1);
			} else {
				user.setLastDate(LocalDate.now());
				user.setLimitDaily(limitDaily);
			}
		}
		if(!validateDifferentAccounts(transaction)) {
			throw new EqualAccountsException();
		}
		if(!accountAssociated(userId,transaction)) {
			throw new AccountNotAssociatedException();
		}
		if(!sufficientFunds(transaction)) {
			throw new InsufficientFoundException();
		}
		
		
		transaction.setUserId(userId);
		updateAmounts(transaction);
		userRepository.save(user);
		ResponseDto response = transactionFeignClient.save(transaction);
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
		UserEntity user = userRepository.findById(userId).orElse(null);
		if(user==null) {
			throw new NotExistException(" user with identifier "+userId);
		}
		result.put("User", user);
		ResponseDto accounts = accountFeignClient.getAccounts(userId);
		if(accounts.getObjects()==null) {
			result.put("Accounts", "The user hasn't accounts");
		}else {
			result.put("Accounts", accounts.getObjects());
		}
		
		ResponseDto transactions = transactionFeignClient.getTransactions(userId);
		if(transactions.getObjects()==null) {
			result.put("Transactions", "The user hasn't transactions");
		}else {
			result.put("Transactions", transactions.getObjects());
		}
		
		return result;
		
	}
	
	/**
	 * validateDiferentAccounts validate if accounts are different
	 * @param transaction transaction information for validate accounts
	 * @return If the accounts are different
	 */
	private boolean validateDifferentAccounts(TransactionDto transaction) {
		if(transaction.getNumberOrigin().equalsIgnoreCase(transaction.getNumberDestination())) {
			return false;
		}
		return true;
	}
	
	/**
	 * accountAssociated validate if account is associate to user
	 * @param userId userId identifier user by validate associate with account
	 * @param transaction object with information for validate associate with account origin
	 * @return If the account is associate to user
	 */
	private boolean accountAssociated(int userId, TransactionDto transaction) {
		AccountDto accountOrigin = accountFeignClient.getByNumberAccount(transaction.getNumberOrigin());
		if(accountOrigin==null || accountOrigin.getUserId()!=userId) {
			return false;
		}
		return true;
	}
	
	/**
	 * sufficientFunds validate if account have sufficient funds to do the transaction 
	 * @param transaction transaction information for validate funds
	 * @return If the account have sufficient funds
	 */
	private boolean sufficientFunds(TransactionDto transaction) {
		AccountDto accountOrigin = accountFeignClient.getByNumberAccount(transaction.getNumberOrigin());
		if(accountOrigin==null) {
			return false;
		}
		double amountOrigin = convertToCurrency(accountOrigin.getCurrency(), transaction.getCurrency(),accountOrigin.getAmount());
		if(amountOrigin<transaction.getAmount()) {
			return false;
		}
		return true;
	}
	
	/**
	 * convertToCurrency conversion of currency 
	 * @param currencyInto currency type of amount
	 * @param currencyOut currency conversion
	 * @param amount amount by convert
	 * @return Conversion of amount to currency conversion
	 */
	private double convertToCurrency(String currencyInto, String currencyOut, double amount) {
		double amountToCurrency = 0.0; 
		if(!currencyInto.equalsIgnoreCase(currencyOut)) {
			if(currencyInto.equals(Constants.COP)) {
				amountToCurrency = amount;
				if(currencyOut.equals(Constants.EUR)) {
					amountToCurrency /= Constants.TRM_EUR;
				}
				if(currencyOut.equals(Constants.USD)) {
					amountToCurrency /= Constants.TRM_USD;
				}
				return amountToCurrency;
			}
			if(currencyInto.equals(Constants.USD)) {
				amountToCurrency = amount*Constants.TRM_USD;
				if(currencyOut.equals(Constants.EUR)) {
					amountToCurrency /= Constants.TRM_EUR;
				}
				return amountToCurrency;
			}
			if(currencyInto.equals(Constants.EUR)) {
				amountToCurrency = amount*Constants.TRM_EUR;
				if(currencyOut.equals(Constants.USD)) {
					amountToCurrency /= Constants.TRM_USD;
				}
				return amountToCurrency;
			}
		}
		return amount;
	}
	
	/**
	 * updateAmounts update amount on accounts of transaction 
	 * @param transaction object with information of transaction
	 */
	private void updateAmounts(TransactionDto transaction) {
		AccountDto accountOrigin = accountFeignClient.getByNumberAccount(transaction.getNumberOrigin());
		AccountDto accountDestination = accountFeignClient.getByNumberAccount(transaction.getNumberDestination());
		double amountOrigin = convertToCurrency(accountOrigin.getCurrency(), Constants.USD, accountOrigin.getAmount());
		double taxe = 0.0;
		if(amountOrigin>100) {
			taxe = amountOrigin*taxeHigher;
		} else {
			taxe = amountOrigin*taxeLower;
		}
		taxe = convertToCurrency(Constants.USD, accountOrigin.getCurrency(), taxe);
		
		accountOrigin.setAmount(accountOrigin.getAmount()-convertToCurrency(transaction.getCurrency(), accountOrigin.getCurrency(), transaction.getAmount())-calculateTax(transaction));
		accountDestination.setAmount(accountDestination.getAmount()+convertToCurrency(transaction.getCurrency(), accountDestination.getCurrency(), transaction.getAmount()));
		
		accountFeignClient.update(accountOrigin, transaction.getNumberOrigin());
		accountFeignClient.update(accountDestination, transaction.getNumberDestination());		
	}
	
	/**
	 * calculateTaxe calculate taxes
	 * @param transaction object with information of transaction
	 * @return Percentage value to apply
	 */
	private double calculateTax(TransactionDto transaction) {
		double amountTransaction = convertToCurrency(transaction.getCurrency(), Constants.USD, transaction.getAmount());
		double taxe = 0.0;
		if(amountTransaction>100) {
			taxe = amountTransaction*taxeHigher;
		} else {
			taxe = amountTransaction*taxeLower;
		}
		taxe = amountTransaction-convertToCurrency(Constants.USD, transaction.getCurrency(), taxe);
		return taxe;
	}
}
