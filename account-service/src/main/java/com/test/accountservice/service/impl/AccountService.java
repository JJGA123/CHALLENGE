package com.test.accountservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.accountservice.entity.AccountEntity;
import com.test.accountservice.repository.IAccountRepository;
import com.test.accountservice.service.IAccountService;

@Service
public class AccountService implements IAccountService{

	@Autowired
	IAccountRepository accountRepository;
	
	/**
	 * Implementation getAll: get the accounts
	 * @return Accounts information list
	 */
	@Override
	public List<AccountEntity> getAll(){
		return accountRepository.findAll();
	}

	/**
	 * Implementation getAccountByNumberAccount: get the account with filter by numberAccount
	 * @param numberAccount number account for get
	 * @return Account information
	 */
	@Override
	public AccountEntity getAccountByNumberAccount(String numberAccount) {
		return accountRepository.findByNumberAccount(numberAccount).orElse(null);
	}
	
	/**
	 * Implementation save: save the new user
	 * @param account object that contains the information of new account
	 * @return New account information
	 */
	@Override
	public AccountEntity save(AccountEntity account) {
		AccountEntity newAccount = accountRepository.save(account);
		return newAccount;
	}
	
	/**
	 * Implementation getAccountByUserId: get the accounts with filter userId
	 * @param userId identifier user by filter
	 * @return Accounts information list by userId
	 */
	@Override
	public List<AccountEntity> getAccountByUserId(int userId) {
		return accountRepository.findByUserId(userId);
	}
}
