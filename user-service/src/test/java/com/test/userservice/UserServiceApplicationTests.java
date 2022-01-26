package com.test.userservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.userservice.dto.AccountDto;
import com.test.userservice.dto.ResponseDto;
import com.test.userservice.dto.TransactionDto;
import com.test.userservice.dto.UserDto;
import com.test.userservice.entity.UserEntity;
import com.test.userservice.feignclient.IAccountFeignClient;
import com.test.userservice.feignclient.ITransactionFeignClient;
import com.test.userservice.repository.IUserRepository;
import com.test.userservice.service.impl.UserService;
import com.test.userservice.util.Mapper;

@SpringBootTest
class UserServiceApplicationTests {

	private UserService userService;
	
	@Mock
	private IUserRepository userRepository;
	
	@Mock
	private IAccountFeignClient accountFeignClient;
	
	@Mock
	private ITransactionFeignClient transactionFeignClient;
	
	@Mock
	private Mapper mapper;
	
	private UserDto userDto;
	private UserEntity userEntity;
	private List<UserEntity> listUserEntity;
	private AccountDto accountDto;
	private List<AccountDto> listAccountDto;
	private TransactionDto transactionDto;
	private List<TransactionDto> listTransactionDto;
	private ResponseDto responseDto;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		userService = new UserService(userRepository,accountFeignClient,transactionFeignClient,mapper);
		userDto = new UserDto("admin","jhongalvis2@gmail.com",3,LocalDate.now(),"admin","admin");
		userEntity = new UserEntity(1,"admin","jhongalvis2@gmail.com",3,LocalDate.now(),"admin","admin");
		listUserEntity = new ArrayList<>();
		listUserEntity.add(userEntity);
		
		accountDto = new AccountDto("1001001","COP",10000,1);
		listAccountDto = new ArrayList<>();
		listAccountDto.add(accountDto);
		
		transactionDto = new TransactionDto(10,"USD","1001001","1001002","Test");
		listTransactionDto = new ArrayList<>();
		listTransactionDto.add(transactionDto);
		
		Mockito.when(mapper.toDto(userEntity)).thenReturn(userDto);
		Mockito.when(mapper.toEntity(userDto)).thenReturn(userEntity);
		
		Mockito.when(userRepository.findAll()).thenReturn(listUserEntity);
		Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);
		Mockito.when(userRepository.findByIdUser(1)).thenReturn(userEntity);
		
		responseDto = new ResponseDto("OK", null, "Successful operation.", accountDto);
		Mockito.when(accountFeignClient.getAccounts(1)).thenReturn(listAccountDto);
		Mockito.when(accountFeignClient.save(accountDto)).thenReturn(responseDto);
		
		responseDto = new ResponseDto("OK", null, "Successful operation.", transactionDto);		
		Mockito.when(transactionFeignClient.getTransactionByNumberOrigin("1001001")).thenReturn(listTransactionDto);
		Mockito.when(transactionFeignClient.sendTransaction(transactionDto)).thenReturn(responseDto);
	}
	
	@Test()
    public void getAllTest() {
		List<UserDto> listUser = userService.getAll();
		Assertions.assertThat(!listUser.isEmpty());
	}
	
	@Test()
    public void getUserByIdTest() {
		UserDto userDto = userService.getUserById(1);
		Assertions.assertThat(userDto!=null);
	}
	
	@Test()
    public void getUserByEmailTest() {
		Mockito.when(userRepository.findByEmail("jhongalvis2@gmail.com")).thenReturn(userEntity);
		UserDto userDto = userService.getUserByEmail("jhongalvis2@gmail.com");
		Assertions.assertThat(userDto!=null);
	}
	
	@Test()
    public void saveTest() {
		Mockito.when(userRepository.findByEmail("jhongalvis2@gmail.com")).thenReturn(null);
		Mockito.when(userRepository.findByIdUser(1)).thenReturn(null);
		UserDto user = userService.save(userDto);
		Assertions.assertThat(user!=null);
	}
	
	@Test
	public void editTest(){
		Mockito.when(userRepository.findByEmail("jhongalvis2@gmail.com")).thenReturn(userEntity);
		UserDto user = userService.edit(userDto);
		Assertions.assertThat(user!=null);
	}
	
	@Test
	public void getAccountsTest() {
		List<AccountDto> listAccount = userService.getAccounts(1);
		Assertions.assertThat(!listAccount.isEmpty());
	}
	
	@Test
	public void getTransactionsTest() {
		List<TransactionDto> listTransaction = userService.getTransactions(1);
		Assertions.assertThat(!listTransaction.isEmpty());
	}
	
	@Test
	public void saveAccountTest(){
		ResponseDto response = userService.saveAccount(1,accountDto);
		Assertions.assertThat(response!=null);
	}
	
	@Test
	public void sendTransactionTest(){
		ResponseDto response = userService.sendTransaction(transactionDto);
		Assertions.assertThat(response!=null);
	}
	
	@Test
	public void getUserAllTest() {
		Map<String, Object> result = userService.getUserAll(1);
		Assertions.assertThat(result!=null);
	}
	
}
