package com.test.accountservice;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.accountservice.dto.RqAccountDto;
import com.test.accountservice.dto.RsAccountDto;
import com.test.accountservice.entity.AccountEntity;
import com.test.accountservice.repository.IAccountRepository;
import com.test.accountservice.service.impl.AccountService;
import com.test.accountservice.util.Mapper;

@SpringBootTest
class AccountServiceApplicationTest {

	@Mock
	private IAccountRepository accountRepository;
	
	@Mock
	private Mapper mapper;
	
	private AccountService accountService;
	private RsAccountDto rsAccountDto1;
	private RqAccountDto rqAccountDto1;
	private AccountEntity accountEntity;
	private List<AccountEntity> listAccountEntity;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountService(accountRepository, mapper);
		
		rsAccountDto1 = new RsAccountDto("1001001","COP",100,1);				
		rqAccountDto1 = new RqAccountDto("1001001","COP",10000,1);		
		accountEntity = new AccountEntity(1,"1001001","COP",1000,1);
		
		listAccountEntity = new ArrayList<>();
		listAccountEntity.add(accountEntity);
		
		Mockito.when(mapper.toDto(accountEntity)).thenReturn(rsAccountDto1);
		Mockito.when(mapper.toEntity(rqAccountDto1)).thenReturn(accountEntity);
		
		Mockito.when(accountRepository.findByUserId(1)).thenReturn(listAccountEntity);
		Mockito.when(accountRepository.findAll()).thenReturn(listAccountEntity);
		Mockito.when(accountRepository.save(accountEntity)).thenReturn(accountEntity);
	}
	
	@Test
	public void getAllTest(){
		List<RsAccountDto> listRsAccount = accountService.getAll();
		Assertions.assertThat(!listRsAccount.isEmpty());
	}
	
	@Test
	public void getAccountByNumberAccountTest(){
		Mockito.when(accountRepository.findByNumberAccount("1001001")).thenReturn(accountEntity);
		RsAccountDto rsAccount = accountService.getAccountByNumberAccount("1001001");
		Assertions.assertThat(!rsAccount.equals(rsAccountDto1));
	}
	
	@Test
	public void saveTest(){
		Mockito.when(accountRepository.findByNumberAccount("1001001")).thenReturn(null);
		RsAccountDto rsAccount = accountService.save(rqAccountDto1);
		Assertions.assertThat(!rsAccount.equals(rsAccountDto1));
	}
	
	@Test
	public void getAccountByUserIdTest(){
		List<RsAccountDto> listRsAccount = accountService.getAccountByUserId(1);
		Assertions.assertThat(!listRsAccount.isEmpty());
	}
	
	@Test
	public void editTest(){
		Mockito.when(accountRepository.findByNumberAccount("1001001")).thenReturn(accountEntity);
		RsAccountDto rsAccount = accountService.edit(rqAccountDto1,"1001001");
		Assertions.assertThat(!rsAccount.equals(rsAccountDto1));
	}
	
}
