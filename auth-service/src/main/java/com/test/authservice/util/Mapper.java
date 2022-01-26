package com.test.authservice.util;

import org.springframework.stereotype.Component;

import com.test.authservice.dto.NewUserDto;
import com.test.authservice.entity.UserEntity;

@Component
public class Mapper {
	
	/**
	 * toDto change the information Entity to Dto
	 * @param account object that contains the information to change
	 * @return Object Dto
	 */
    public NewUserDto toDto(UserEntity user) {
        return new NewUserDto(user.getNameUser(),user.getPassword(),user.getRole());
    }
    
    /**
	 * toEntity change the information Dto to Entity
	 * @param account object that contains the information to change
	 * @return Object Entity
	 */
    public UserEntity toEntity(NewUserDto dto) {
        return new UserEntity(0,dto.getNameUser(),null,0,null,dto.getPassword(),dto.getRole());
    }
}
