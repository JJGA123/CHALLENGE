package com.test.accountservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.accountservice.entity.AccountEntity;

public interface IAccountRepository  extends JpaRepository<AccountEntity, Integer>{
	
	/**
	 * findByUserId get the user information with filter by userId
	 * @param userId integer with identifier user
	 * @return Accounts list by user
	 */
	List<AccountEntity> findByUserId(int userId);
	
	/**
	 * findByNumberAccount get the user information with filter by numberAccount
	 * @param numberAccount text string with user number account
	 * @return The AccountEntity object with the user information
	 */
	Optional<AccountEntity> findByNumberAccount(String numberAccount);
}
