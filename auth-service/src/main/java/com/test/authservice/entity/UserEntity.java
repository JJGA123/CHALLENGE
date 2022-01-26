package com.test.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name="USER")
public class UserEntity {

	@Id
    @NotNull
    @Column(name = "ID_USER")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int idUser;
	
    @Column(name = "NAMEUSER")
	private String nameUser;
	
    @Column(name = "EMAIL")
	private String email;
	
	@Column(name = "LIMIT_DAILY")
	private int limitDaily;
	
	@Column(name = "LAST_DATE")
	private LocalDate lastDate;
	
	@NotNull
    @Column(name = "PASSWORD")
	private String password;
	
	@NotNull
    @Column(name = "ROLE")
    private String role;//Can use the roles user or admin
    
}