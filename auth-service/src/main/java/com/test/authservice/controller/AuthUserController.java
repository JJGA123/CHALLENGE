package com.test.authservice.controller;

import com.test.authservice.dto.AuthUserDto;
import com.test.authservice.dto.NewUserDto;
import com.test.authservice.dto.RequestDto;
import com.test.authservice.dto.TokenDto;
import com.test.authservice.entity.AuthUser;
import com.test.authservice.service.IAuthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    IAuthUserService authUserService;
    
    /**
	 * PostMapping method to receive the request
	 * @param dto object that contains the user information
	 * @return The token requested
	 */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto dto){
        TokenDto tokenDto = authUserService.login(dto);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    /**
	 * PostMapping method to receive the request
	 * @param token text string with the token to validate
	 * @param dto object that contains the information of validation
	 * @return The token requested
	 */
    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token, @RequestBody RequestDto dto){
        TokenDto tokenDto = authUserService.validate(token,dto);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    /**
	 * PostMapping method to receive the request
	 * @param dto object that contains the information of creation
	 * @return The user information
	 */
    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody NewUserDto dto){
        AuthUser authUser = authUserService.save(dto);
        if(authUser == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authUser);
    }
}
