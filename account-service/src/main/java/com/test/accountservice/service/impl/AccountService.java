package com.test.accountservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.accountservice.dto.RqAccountDto;
import com.test.accountservice.dto.RsAccountDto;
import com.test.accountservice.entity.AccountEntity;
import com.test.accountservice.exception.AmountInvalidException;
import com.test.accountservice.exception.ExistException;
import com.test.accountservice.exception.NotExistException;
import com.test.accountservice.repository.IAccountRepository;
import com.test.accountservice.service.IAccountService;
import com.test.accountservice.util.Mapper;

@Service
public class AccountService implements IAccountService{

	private final IAccountRepository accountRepository;
	
	private final Mapper mapper;
	
	@Autowired
	public AccountService(IAccountRepository accountRepository, Mapper mapper) {
		this.accountRepository = accountRepository;
		this.mapper = mapper;
	}
	
	/**
	 * Implementation getAll: get the accounts
	 * @return Accounts information list
	 */
	@Override
	public List<RsAccountDto> getAll(){
		List<AccountEntity> accounts = accountRepository.findAll();
		if(accounts.isEmpty()) {
			throw new NotExistException("");
		}
		List<RsAccountDto> accountsDto = accounts.stream()
		          .map(mapper::toDto)
		          .collect(Collectors.toList());
		
		return accountsDto;
	}

	/**
	 * Implementation getAccountByNumberAccount: get the account with filter by numberAccount
	 * @param numberAccount number account for get
	 * @return Account information
	 */
	@Override
	public RsAccountDto getAccountByNumberAccount(String numberAccount) {
		AccountEntity account = accountRepository.findByNumberAccount(numberAccount);
		if(account==null) {
			throw new NotExistException(" by account number "+numberAccount);
		}
		return mapper.toDto(account);
	}
	
	/**
	 * Implementation save: save the new account
	 * @param account object that contains the information of new account
	 * @return New account information
	 */
	@Override
	public RsAccountDto save(RqAccountDto account) {
		AccountEntity accountNew = accountRepository.findByNumberAccount(account.getNumberAccount());
		if(accountNew!=null) {
			throw new ExistException("Account "+accountNew.getNumberAccount());
		}
		if(account.getAmount()<=0) {
			throw new AmountInvalidException();
		}
		accountNew = accountRepository.save(mapper.toEntity(account));
		return mapper.toDto(accountNew);
	}
	
	/**
	 * Implementation getAccountByUserId: get the accounts with filter userId
	 * @param userId identifier user by filter
	 * @return Accounts information list by userId
	 */
	@Override
	public List<RsAccountDto> getAccountByUserId(int userId) {
		List<AccountEntity> accounts = accountRepository.findByUserId(userId);
		if(accounts.isEmpty()) {
			throw new NotExistException(" by user identifier "+userId);
		}
		List<RsAccountDto> accountsDto = accounts.stream()
		          .map(mapper::toDto)
		          .collect(Collectors.toList());
		return accountsDto;
	}
	
	/**
	 * Specification updateAccount: edit the account information 
	 * @param account object that contains the new information account
	 * @param numberAccount text string with number account to edit
	 * @return New information account
	 */
	@Override
	public RsAccountDto edit(RqAccountDto account, String numberAccount) {
		AccountEntity updateAccount = accountRepository.findByNumberAccount(numberAccount);
		if(account==null) {
			throw new NotExistException(" by account number "+numberAccount);
		}
		updateAccount.setCurrency(account.getCurrency());
		updateAccount.setAmount(account.getAmount());
		accountRepository.save(updateAccount);
		return mapper.toDto(updateAccount);
	}
}
