package com.test.transactionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.transactionservice.entity.TransactionEntity;

@Repository
public interface ITransactionRepository  extends JpaRepository<TransactionEntity, Integer>{
		
	/**
	 * findAll get transaction created
	 * @return Transactions list
	 */
	List<TransactionEntity> findAll();
	
	/**
	 * findByNumberOrigin get the transaction information with filter by number origin
	 * @param numberOrigin integer with number origin
	 * @return Transactions list by number origin
	 */
	List<TransactionEntity> findByNumberOrigin(String numberOrigin);
	
	/**
	 * save save the new transaction
	 * @param transaction object that contains the information of new transaction
	 * @return The TransactionEntity object with the transaction information
	 */
	TransactionEntity save(TransactionEntity transaction);
}
