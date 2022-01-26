package com.test.transactionservice.service;

import java.util.List;

import com.test.transactionservice.dto.TransactionDto;

public interface ITransactionService {
	
	/**
	 * Specification getAll: get the transactions
	 * @return Transactions information list
	 */
	public List<TransactionDto> getAll();
	
	/**
	 * Specification getTransactionByNumberOrigin: get the transactions by number origin
	 * @param numberOrigin text string with number origin
	 * @return Transactions information list
	 */
	public List<TransactionDto> getTransactionByNumberOrigin(String numberOrigin);
	
	/**
	 * Specification save: save the new transaction
	 * @param transaction object that contains the information of new transaction
	 * @return New account information
	 */
	public TransactionDto save(TransactionDto transaction);
	
}
