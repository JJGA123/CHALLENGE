package com.test.userservice.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import com.sun.istack.NotNull;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER")
public class UserEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @Column(name = "ID_USER")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int idUser;
	
    @NotNull
    @Column(name = "NAMEUSER")
	private String nameUser;
	
    @NotNull
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
