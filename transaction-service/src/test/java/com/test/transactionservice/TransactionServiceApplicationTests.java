package com.test.transactionservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.transactionservice.dto.AccountDto;
import com.test.transactionservice.dto.TransactionDto;
import com.test.transactionservice.dto.UserDto;
import com.test.transactionservice.entity.TransactionEntity;
import com.test.transactionservice.feignclient.IAccountFeignClient;
import com.test.transactionservice.feignclient.IUserFeignClient;
import com.test.transactionservice.repository.ITransactionRepository;
import com.test.transactionservice.service.impl.TransactionService;
import com.test.transactionservice.util.Mapper;

@SpringBootTest
class TransactionServiceApplicationTests {

	private TransactionService transactionService;
	
	@Mock
	private ITransactionRepository transactionRepository;
	
	@Mock
	private Mapper mapper;
	
	@Mock
	private IAccountFeignClient accountFeignClient;
	
	@Mock
	private IUserFeignClient userFeignClient;
	
	private TransactionEntity transactionEntity;
	private List<TransactionEntity> listTransactionEntity;
	private TransactionDto transactionDto;
	private AccountDto originAccountDto;
	private AccountDto destinationAccountDto;
	private UserDto userDto;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		transactionService = new TransactionService(transactionRepository,mapper,accountFeignClient,userFeignClient);
		transactionEntity = new TransactionEntity(1,10,"USD","1001001","1001002","Test");
		listTransactionEntity = new ArrayList<>();
		listTransactionEntity.add(transactionEntity);
		transactionDto = new TransactionDto(10,"USD","1001001","1001002","Test");
		
		originAccountDto = new AccountDto("1001001","USD",10000,1);
		destinationAccountDto = new AccountDto("1001002","USD",10000,1);
		
		userDto = new UserDto("admin","jhongalvis2@gmail.com",3,LocalDate.now(),"admin","admin");
		
		
		Mockito.when(mapper.toDto(transactionEntity)).thenReturn(transactionDto);
		Mockito.when(mapper.toEntity(transactionDto)).thenReturn(transactionEntity);
		
		Mockito.when(transactionRepository.findAll()).thenReturn(listTransactionEntity);
		Mockito.when(transactionRepository.findByNumberOrigin("1001001")).thenReturn(listTransactionEntity);
		Mockito.when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
		
		Mockito.when(accountFeignClient.getByNumberAccount(transactionDto.getNumberOrigin())).thenReturn(originAccountDto);
		Mockito.when(accountFeignClient.getByNumberAccount(transactionDto.getNumberDestination())).thenReturn(destinationAccountDto);
		
		Mockito.when(userFeignClient.getById(1)).thenReturn(userDto);
		
		
	}
	
	@Test
    public void getAllTest() {
		List<TransactionDto> listTransaction = transactionService.getAll();
		Assertions.assertThat(!listTransaction.isEmpty());
	}
	
	@Test
    public void getTransactionByNumberOriginTest(){
		List<TransactionDto> listTransaction = transactionService.getTransactionByNumberOrigin("1001001");
		Assertions.assertThat(!listTransaction.isEmpty());
	}
	
	@Test
	public void saveTest() {
		TransactionDto transaction = transactionService.save(transactionDto);
		Assertions.assertThat(transaction!=null);
	}
}
