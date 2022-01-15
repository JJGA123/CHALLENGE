package com.test.transactionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.transactionservice.entity.TransactionEntity;

public interface ITransactionRepository  extends JpaRepository<TransactionEntity, Integer>{
	
	/**
	 * findByUserId get the user information with filter by userId
	 * @param userId integer with identifier user
	 * @return Transactions list by user
	 */
	List<TransactionEntity> findByUserId(int userId);
	
}
