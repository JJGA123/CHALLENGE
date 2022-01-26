package com.test.authservice;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.test.authservice.dto.NewUserDto;
import com.test.authservice.dto.RequestDto;
import com.test.authservice.dto.TokenDto;
import com.test.authservice.dto.UserDto;
import com.test.authservice.entity.UserEntity;
import com.test.authservice.repository.AuthUserRepository;
import com.test.authservice.security.JwtProvider;
import com.test.authservice.service.impl.AuthUserService;
import com.test.authservice.util.Mapper;

@SpringBootTest
class AuthServiceApplicationTests {

	private AuthUserService authUserService;
	
	@Mock
	private AuthUserRepository authUserRepository;
	
	@Mock
    private PasswordEncoder passwordEncoder;
	
	@Mock
    private JwtProvider jwtProvider;
	
	@Mock
    private Mapper mapper;
	
	private NewUserDto newUserDto;
	private UserEntity userEntity;
	private UserDto userDto;
	private RequestDto requestDto;
	private String token;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		authUserService = new AuthUserService(authUserRepository,passwordEncoder,jwtProvider,mapper);
		newUserDto = new NewUserDto("admin","admin","admin");
		userEntity = new UserEntity(1,"admin","jhongalvis2@gmail.com",3,LocalDate.now(),"admin","admin");
		userDto = new UserDto("admin", "admin");
		requestDto = new RequestDto("api/v1/users","GET");
		token = "SSDFSDSDFS6DF54.6546DS54FSDFSDF.FFGDFG654";
		
		Mockito.when(mapper.toDto(userEntity)).thenReturn(newUserDto);
		Mockito.when(mapper.toEntity(newUserDto)).thenReturn(userEntity);
		
		Mockito.when(passwordEncoder.encode("admin")).thenReturn("ASDASDFFSDFSD6546");
		Mockito.when(passwordEncoder.matches(newUserDto.getPassword(),userEntity.getPassword())).thenReturn(true);
		
		Mockito.when(jwtProvider.createToken(userEntity)).thenReturn(token);
		Mockito.when(jwtProvider.validate(token,requestDto)).thenReturn(true);
		Mockito.when(jwtProvider.getUserNameFromToken(token)).thenReturn("admin");
		
		Mockito.when(authUserRepository.save(userEntity)).thenReturn(userEntity);
	}
	
	@Test()
    public void saveTest() {
    	Mockito.when(authUserRepository.findByNameUser("admin")).thenReturn(null);
    	NewUserDto userDto = authUserService.save(newUserDto);
		Assertions.assertThat(userDto!=null);
	}
    
	@Test()
    public void loginTest() {
    	Mockito.when(authUserRepository.findByNameUser("admin")).thenReturn(userEntity);
    	TokenDto tokenDto = authUserService.login(userDto);
		Assertions.assertThat(tokenDto!=null);
	}
	
	@Test()
    public void validateTest(){
		Mockito.when(authUserRepository.findByNameUser("admin")).thenReturn(userEntity);
    	TokenDto tokenDto = authUserService.validate(token,requestDto);
		Assertions.assertThat(tokenDto!=null);
	}
	
}
