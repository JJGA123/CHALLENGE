package com.test.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.userservice.entity.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer>{
	
	/**
	 * findByEmail get the user information with filter by email
	 * @param username text string whit user name
	 * @return The UserEntity object with the user information
	 */
	Optional<UserEntity> findByEmail(String email);
	
	/**
	 * findByNameUser get the user information with filter by nameUser
	 * @param nameUser text string whit user name
	 * @return The UserEntity object with the user information
	 */
	Optional<UserEntity> findByNameUser(String nameUser);
}
