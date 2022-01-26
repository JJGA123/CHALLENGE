package com.test.accountservice.service;

import java.util.List;

import com.test.accountservice.dto.RsAccountDto;
import com.test.accountservice.dto.RqAccountDto;

public interface IAccountService {

	/**
	 * Specification getAll: get the accounts
	 * @return Accounts information list
	 */
	public List<RsAccountDto> getAll();

	/**
	 * Specification getAccountByNumberAccount: get the account with filter by numberAccount
	 * @param numberAccount number account for get
	 * @return Account information
	 */
	public RsAccountDto getAccountByNumberAccount(String numberAccount);
	
	/**
	 * Specification save: save the new account
	 * @param account object that contains the information of new account
	 * @return New account information
	 */
	public RsAccountDto save(RqAccountDto account);
	
	/**
	 * Specification getAccountByUserId: get the accounts with filter userId
	 * @param userId identifier user by filter
	 * @return Accounts information list by userId
	 */
	public List<RsAccountDto> getAccountByUserId(int userId);
	
	/**
	 * Specification updateAccount: edit the account information 
	 * @param account object that contains the new information account
	 * @param numberAccount text string with number account to edit
	 * @return New information account
	 */
	public RsAccountDto edit(RqAccountDto account, String numberAccount);
	
}
