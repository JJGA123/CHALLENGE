package com.test.transactionservice.util;

import org.springframework.stereotype.Component;

import com.test.transactionservice.dto.TransactionDto;
import com.test.transactionservice.entity.TransactionEntity;

@Component
public class Mapper {
	
	/**
	 * toDto change the information Entity to Dto
	 * @param account object that contains the information to change
	 * @return Object Dto
	 */
    public TransactionDto toDto(TransactionEntity transaction) {
        return new TransactionDto(transaction.getAmount(),transaction.getCurrency(),transaction.getNumberOrigin(),transaction.getNumberDestination(),transaction.getDescription());
    }
    
    /**
	 * toEntity change the information Dto to Entity
	 * @param account object that contains the information to change
	 * @return Object Entity
	 */
    public TransactionEntity toEntity(TransactionDto transaction) {
        return new TransactionEntity(0,transaction.getAmount(),transaction.getCurrency(),transaction.getNumberOrigin(),transaction.getNumberDestination(),transaction.getDescription());
    }

}
