package com.test.accountservice.service;

import java.util.List;

import com.test.accountservice.entity.AccountEntity;

public interface IAccountService {

	/**
	 * Specification getAll: get the accounts
	 * @return Accounts information list
	 */
	public List<AccountEntity> getAll();

	/**
	 * Specification getAccountByNumberAccount: get the account with filter by numberAccount
	 * @param numberAccount number account for get
	 * @return Account information
	 */
	public AccountEntity getAccountByNumberAccount(String numberAccount);
	
	/**
	 * Specification save: save the new user
	 * @param account object that contains the information of new account
	 * @return New account information
	 */
	public AccountEntity save(AccountEntity account);
	
	/**
	 * Specification getAccountByUserId: get the accounts with filter userId
	 * @param userId identifier user by filter
	 * @return Accounts information list by userId
	 */
	public List<AccountEntity> getAccountByUserId(int userId);
	
}
