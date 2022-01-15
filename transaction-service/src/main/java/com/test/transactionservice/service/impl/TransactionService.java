package com.test.transactionservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.transactionservice.entity.TransactionEntity;
import com.test.transactionservice.repository.ITransactionRepository;
import com.test.transactionservice.service.ITransactionService;

@Service
public class TransactionService implements ITransactionService{

	@Autowired
	ITransactionRepository transactionRepository;
	
	/**
	 * Implementation getAll: get the transactions
	 * @return Transactions information list
	 */
	public List<TransactionEntity> getAll(){
		return transactionRepository.findAll();
	}
	
	/**
	 * Implementation save: save the new transaction
	 * @param transaction object that contains the information of new account
	 * @return New account information
	 */
	public TransactionEntity save(TransactionEntity account) {
		TransactionEntity newAccount = transactionRepository.save(account);
		return newAccount;
	}
	
	/**
	 * Implementation getTransactionByUserId: get the transactions with filter userId
	 * @param userId identifier user by filter
	 * @return Transactions information list by userId
	 */
	public List<TransactionEntity> getTransactionByUserId(int userId) {
		return transactionRepository.findByUserId(userId);
	}
	
}
