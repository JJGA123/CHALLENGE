package com.test.userservice.util;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.test.userservice.dto.UserDto;
import com.test.userservice.entity.UserEntity;

@Component
public class Mapper {
	
	@Value("${validate.limitDaily}")
	private int limitDaily;
	
	/**
	 * toDto change the information Entity to Dto
	 * @param account object that contains the information to change
	 * @return Object Dto
	 */
    public UserDto toDto(UserEntity user) {
        return new UserDto(user.getNameUser(),user.getEmail(),user.getLimitDaily(),user.getLastDate(),user.getPassword(),user.getRole());
    }
    
    /**
	 * toEntity change the information Dto to Entity
	 * @param account object that contains the information to change
	 * @return Object Entity
	 */
    public UserEntity toEntity(UserDto user) {
        return new UserEntity(0,user.getNameUser(),user.getEmail(),limitDaily,LocalDate.now(),user.getPassword(),user.getRole());
    }

}
