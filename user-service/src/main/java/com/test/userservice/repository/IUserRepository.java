package com.test.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.userservice.entity.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer>{
	
	/**
	 * findByIdUser get the user information with filter by idUser
	 * @param idUser text string whit user id
	 * @return The UserEntity object with the user information
	 */
	UserEntity findByIdUser(int idUser);
	
	/**
	 * findByEmail get the user information with filter by email
	 * @param email text string with email
	 * @return The UserEntity object with the user information
	 */
	UserEntity findByEmail(String email);
	
	/**
	 * findByNameUser get the user information with filter by nameUser
	 * @param nameUser text string whit user name
	 * @return The UserEntity object with the user information
	 */
	UserEntity findByNameUser(String nameUser);
	
	/**
	 * save save the new user
	 * @param user object that contains the information of new user
	 * @return The UserEntity object with the user information
	 */
	UserEntity save(UserEntity user);
	
}
