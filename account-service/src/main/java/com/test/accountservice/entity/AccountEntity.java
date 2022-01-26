package com.test.accountservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="ACCOUNT")
public class AccountEntity {
	
	@Id
    @NotNull
    @Column(name = "ID_ACCOUNT")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAccount;
	
    @NotNull
    @Column(name = "NUMER_ACCOUNT")
	private String numberAccount;
	
    @NotNull
    @Column(name = "CURRENCY")
	private String currency;
	
    @NotNull
    @Column(name = "AMOUNT")
	private double amount;
	
    @NotNull
    @Column(name = "ID_USER")
	private int userId;
	
}
