package com.test.authservice.repository;

import com.test.authservice.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
	
	/**
	 * findByUserName get the user information with filter by name
	 * @param username text string with user name
	 * @return The AuthUser object with the user information
	 */
    Optional<AuthUser> findByNameUser(String username);
}
