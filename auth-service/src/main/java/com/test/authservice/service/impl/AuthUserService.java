package com.test.authservice.service.impl;

import com.test.authservice.dto.UserDto;
import com.test.authservice.dto.NewUserDto;
import com.test.authservice.dto.RequestDto;
import com.test.authservice.dto.TokenDto;
import com.test.authservice.entity.UserEntity;
import com.test.authservice.exception.UnauthorizedException;
import com.test.authservice.repository.AuthUserRepository;
import com.test.authservice.security.JwtProvider;
import com.test.authservice.service.IAuthUserService;
import com.test.authservice.util.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements IAuthUserService{

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final Mapper mapper;
    
    @Autowired
	public AuthUserService(AuthUserRepository authUserRepository,PasswordEncoder passwordEncoder,JwtProvider jwtProvider,Mapper mapper) {
		this.authUserRepository = authUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.mapper = mapper;
	}

    /**
	 * Implementation save: save the new user
	 * @param dto object that contains the information of new user
	 * @return New user information
	 */
    @Override
    public NewUserDto save(NewUserDto dto) {
        UserEntity user = authUserRepository.findByNameUser(dto.getNameUser());
        NewUserDto newUserDto = null;
        if(user!=null)
        	throw new UnauthorizedException("");
        String password = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(password);
        user = authUserRepository.save(mapper.toEntity(dto));
        newUserDto = mapper.toDto(user);
        if(newUserDto==null) {
        	throw new UnauthorizedException("");
        }
        return newUserDto;
    }

    /**
	 * Implementation login: login the authorization system
	 * @param dto object that contains the information of user by login
	 * @return Information token
	 */
    @Override
    public TokenDto login(UserDto dto) {
        UserEntity user = authUserRepository.findByNameUser(dto.getNameUser());
        TokenDto tokenDto = null;
        if(user==null) {
        	throw new UnauthorizedException("");
    	}
        if(passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
        	tokenDto = new TokenDto(jwtProvider.createToken(user));
    	}
        if(tokenDto == null) {
        	throw new UnauthorizedException("");
        }
        return tokenDto;
    }

    /**
	 * Implementation validate: validate the information on token
	 * @param token text string with the token to validate
	 * @param dto object that contains the information by validate
	 * @return Information token
	 */
    @Override
    public TokenDto validate(String token, RequestDto dto) {
    	TokenDto tokenDto = null;
        if(!jwtProvider.validate(token,dto)) {
        	return null;
        } else {
        	String username = jwtProvider.getUserNameFromToken(token);
            if(authUserRepository.findByNameUser(username)==null) {
            	return null;
            } else {
            	tokenDto = new TokenDto(token);
            }
        }
        return tokenDto;
    }
}
