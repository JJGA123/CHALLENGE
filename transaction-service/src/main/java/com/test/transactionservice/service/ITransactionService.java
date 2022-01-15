package com.test.transactionservice.service;

import java.util.List;

import com.test.transactionservice.entity.TransactionEntity;

public interface ITransactionService {
	
	/**
	 * Specification getAll: get the transactions
	 * @return Transactions information list
	 */
	public List<TransactionEntity> getAll();
	
	/**
	 * Specification save: save the new transaction
	 * @param transaction object that contains the information of new account
	 * @return New account information
	 */
	public TransactionEntity save(TransactionEntity transaction);
	
	/**
	 * Specification getTransactionByUserId: get the transactions with filter userId
	 * @param userId identifier user by filter
	 * @return Transactions information list by userId
	 */
	public List<TransactionEntity> getTransactionByUserId(int userId);
	
}
