package com.test.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.accountservice.entity.AccountEntity;

public interface IAccountRepository  extends JpaRepository<AccountEntity, Integer>{
	
	/**
	 * findByUserId get the account information with filter by userId
	 * @param userId integer with identifier user
	 * @return Accounts list by user
	 */
	List<AccountEntity> findByUserId(int userId);
	
	/**
	 * findByNumberAccount get the account information with filter by numberAccount
	 * @param numberAccount text string with number account
	 * @return The AccountEntity object with the account information
	 */
	AccountEntity findByNumberAccount(String numberAccount);
	
	/**
	 * save save the new account
	 * @param account object that contains the information of new account
	 * @return The AccountEntity object with the account information
	 */
	AccountEntity save(AccountEntity account);
	
}
