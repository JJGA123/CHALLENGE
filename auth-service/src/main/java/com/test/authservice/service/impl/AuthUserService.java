package com.test.authservice.service.impl;

import com.test.authservice.dto.AuthUserDto;
import com.test.authservice.dto.NewUserDto;
import com.test.authservice.dto.RequestDto;
import com.test.authservice.dto.TokenDto;
import com.test.authservice.entity.AuthUser;
import com.test.authservice.repository.AuthUserRepository;
import com.test.authservice.security.JwtProvider;
import com.test.authservice.service.IAuthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService implements IAuthUserService{

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    /**
	 * Implementation save: save the new user
	 * @param dto object that contains the information of new user
	 * @return New user information
	 */
    @Override
    public AuthUser save(NewUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByNameUser(dto.getNameUser());
        if(user.isPresent())
            return null;
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .nameUser(dto.getNameUser())
                .password(password)
                .role(dto.getRole())
                .build();
        return authUserRepository.save(authUser);
    }

    /**
	 * Implementation login: login the authorization system
	 * @param dto object that contains the information of user by login
	 * @return Information token
	 */
    @Override
    public TokenDto login(AuthUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByNameUser(dto.getNameUser());
        if(!user.isPresent())
            return null;
        if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword()))
            return new TokenDto(jwtProvider.createToken(user.get()));
        return null;
    }

    /**
	 * Implementation validate: validate the information on token
	 * @param token text string with the token to validate
	 * @param dto object that contains the information by validate
	 * @return Information token
	 */
    @Override
    public TokenDto validate(String token, RequestDto dto) {
        if(!jwtProvider.validate(token,dto))
            return null;
        String username = jwtProvider.getUserNameFromToken(token);
        if(!authUserRepository.findByNameUser(username).isPresent())
            return null;
        return new TokenDto(token);
    }
}
