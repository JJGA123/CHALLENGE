package com.test.authservice.service;

import com.test.authservice.dto.AuthUserDto;
import com.test.authservice.dto.NewUserDto;
import com.test.authservice.dto.RequestDto;
import com.test.authservice.dto.TokenDto;
import com.test.authservice.entity.AuthUser;

public interface IAuthUserService {

	/**
	 * Specification save: save the new user
	 * @param dto object that contains the information of new user
	 * @return New user information
	 */
    public AuthUser save(NewUserDto dto);

    /**
	 * Specification login: login the authorization system
	 * @param dto object that contains the information of user by login
	 * @return Information token
	 */
    public TokenDto login(AuthUserDto dto);

    /**
	 * Specification validate: validate the information on token
	 * @param token text string with the token to validate
	 * @param dto object that contains the information by validate
	 * @return Information token
	 */
    public TokenDto validate(String token, RequestDto dto);
    
}
