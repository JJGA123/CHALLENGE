package com.test.transactionservice.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.transactionservice.constants.ConstantType;
import com.test.transactionservice.dto.AccountDto;
import com.test.transactionservice.dto.TransactionDto;
import com.test.transactionservice.dto.UserDto;
import com.test.transactionservice.entity.TransactionEntity;
import com.test.transactionservice.exception.AccountNotAssociatedException;
import com.test.transactionservice.exception.EqualAccountsException;
import com.test.transactionservice.exception.InsufficientFoundException;
import com.test.transactionservice.exception.LimitTransactionsException;
import com.test.transactionservice.exception.NotExistException;
import com.test.transactionservice.feignclient.IAccountFeignClient;
import com.test.transactionservice.feignclient.IUserFeignClient;
import com.test.transactionservice.repository.ITransactionRepository;
import com.test.transactionservice.service.ITransactionService;
import com.test.transactionservice.util.Mapper;

@Service
public class TransactionService implements ITransactionService{
	
	@Value("${validate.limitDaily}")
	private int limitDaily;
	
	@Value("${taxe.higher}")
	private double taxeHigher;
	
	@Value("${taxe.lower}")
	private double taxeLower;
	
	private final ITransactionRepository transactionRepository;
	
	private final Mapper mapper;
	
	private final IAccountFeignClient accountFeignClient;
	
	private final IUserFeignClient userFeignClient;
	
	@Autowired
	public TransactionService(ITransactionRepository transactionRepository, Mapper mapper,IAccountFeignClient accountFeignClient,IUserFeignClient userFeignClient) {
		this.transactionRepository = transactionRepository;
		this.mapper = mapper;
		this.accountFeignClient = accountFeignClient;
		this.userFeignClient = userFeignClient;
	}
	
	/**
	 * Implementation getAll: get the transactions
	 * @return Transactions information list
	 */
	@Override
	public List<TransactionDto> getAll(){
		List<TransactionEntity> transactions = transactionRepository.findAll();
		if(transactions.isEmpty()) {
			throw new NotExistException("");
		}
		List<TransactionDto> transactionsDto = transactions.stream()
		          .map(mapper::toDto)
		          .collect(Collectors.toList());
		return transactionsDto;
	}
	
	/**
	 * Implementation getTransactionByNumberOrigin: get the transactions by number origin
	 * @param numberOrigin text string with number origin
	 * @return Transactions information list
	 */
	@Override
	public List<TransactionDto> getTransactionByNumberOrigin(String numberOrigin){
		List<TransactionEntity> transactions = transactionRepository.findByNumberOrigin(numberOrigin);
		if(transactions.isEmpty()) {
			throw new NotExistException("");
		}
		List<TransactionDto> transactionsDto = transactions.stream()
		          .map(mapper::toDto)
		          .collect(Collectors.toList());
		return transactionsDto;
	}
	
	/**
	 * Implementation save: save the new transaction
	 * @param transaction object that contains the information of new transaction
	 * @return New account information
	 */
	@Override
	public TransactionDto save(TransactionDto transaction) {
		AccountDto account = accountFeignClient.getByNumberAccount(transaction.getNumberDestination());
		if(account==null) {
			throw new NotExistException(" number account destination "+transaction.getNumberDestination());
		}
		account = accountFeignClient.getByNumberAccount(transaction.getNumberOrigin());
		if(account==null) {
			throw new NotExistException(" number account origin "+transaction.getNumberOrigin());
		}
		UserDto user = userFeignClient.getById(account.getUserId());
		if(user==null) {
			throw new NotExistException(" identifier user "+account.getUserId());
		}
		
		user = updateUserValues(user);
		
		if(!validateDifferentAccounts(transaction)) {
			throw new EqualAccountsException();
		}
		if(!accountAssociated(account.getUserId(),transaction)) {
			throw new AccountNotAssociatedException();
		}
		if(!sufficientFunds(transaction)) {
			throw new InsufficientFoundException();
		}
		
		TransactionEntity newTransaction = transactionRepository.save(mapper.toEntity(transaction));
		if(newTransaction!=null) {
			userFeignClient.edit(user);
			updateAmounts(transaction);
		}
		return mapper.toDto(newTransaction);
	}
	
	/**
	 * updateUserValues update limit daily and last date
	 * @param user user that contains information for update
	 * @return User with information update
	 */
	private UserDto updateUserValues(UserDto user) {
		LocalDate lastDateTransaction = user.getLastDate();
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
		return user;
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
			if(currencyInto.equals(ConstantType.COP)) {
				amountToCurrency = amount;
				if(currencyOut.equals(ConstantType.EUR)) {
					amountToCurrency /= ConstantType.TRM_EUR;
				}
				if(currencyOut.equals(ConstantType.USD)) {
					amountToCurrency /= ConstantType.TRM_USD;
				}
				return amountToCurrency;
			}
			if(currencyInto.equals(ConstantType.USD)) {
				amountToCurrency = amount*ConstantType.TRM_USD;
				if(currencyOut.equals(ConstantType.EUR)) {
					amountToCurrency /= ConstantType.TRM_EUR;
				}
				return amountToCurrency;
			}
			if(currencyInto.equals(ConstantType.EUR)) {
				amountToCurrency = amount*ConstantType.TRM_EUR;
				if(currencyOut.equals(ConstantType.USD)) {
					amountToCurrency /= ConstantType.TRM_USD;
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
		double amountOrigin = convertToCurrency(accountOrigin.getCurrency(), ConstantType.USD, accountOrigin.getAmount());
		double taxe = 0.0;
		if(amountOrigin>100) {
			taxe = amountOrigin*taxeHigher;
		} else {
			taxe = amountOrigin*taxeLower;
		}
		taxe = convertToCurrency(ConstantType.USD, accountOrigin.getCurrency(), taxe);
		
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
		double amountTransaction = convertToCurrency(transaction.getCurrency(), ConstantType.USD, transaction.getAmount());
		double taxe = 0.0;
		if(amountTransaction>100) {
			taxe = amountTransaction*taxeHigher;
		} else {
			taxe = amountTransaction*taxeLower;
		}
		taxe = amountTransaction-convertToCurrency(ConstantType.USD, transaction.getCurrency(), taxe);
		return taxe;
	}
}
