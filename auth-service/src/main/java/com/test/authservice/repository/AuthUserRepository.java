package com.test.authservice.repository;

import com.test.authservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<UserEntity, Integer> {
	
	/**
	 * findByUserName get the user information with filter by name
	 * @param username text string with user name
	 * @return The UserEntity object with the user information
	 */
    UserEntity findByNameUser(String username);
    
    /**
	 * save save the new user
	 * @param user object that contain user information
	 * @return The UserEntity object with the user information
	 */
    UserEntity save(UserEntity user);
}
