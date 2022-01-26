package com.test.accountservice.util;

import org.springframework.stereotype.Component;

import com.test.accountservice.dto.RqAccountDto;
import com.test.accountservice.dto.RsAccountDto;
import com.test.accountservice.entity.AccountEntity;

@Component
public class Mapper {
	
	/**
	 * toDto change the information Entity to Dto
	 * @param account object that contains the information to change
	 * @return Object Dto
	 */
    public RsAccountDto toDto(AccountEntity account) {
        return new RsAccountDto(account.getNumberAccount(), account.getCurrency(), account.getAmount(),account.getUserId());
    }
    
    /**
	 * toEntity change the information Dto to Entity
	 * @param account object that contains the information to change
	 * @return Object Entity
	 */
    public AccountEntity toEntity(RqAccountDto account) {
        return new AccountEntity(0,account.getNumberAccount(), account.getCurrency(), account.getAmount(), account.getUserId());
    }

}
