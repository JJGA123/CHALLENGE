package com.test.transactionservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="TRANSACTION")
public class TransactionEntity {
	
	@Id
	@NotNull
    @Column(name = "ID_TRANSACTION")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTransaction;
	
    @NotNull
    @Column(name = "AMOUNT")
	private double amount;
	
    @NotNull
    @Column(name = "CURRENCY")
	private String currency;
	
    @NotNull
    @Column(name = "NUMBER_ORIGIN")
	private String numberOrigin;
	
    @NotNull
    @Column(name = "NUMBER_DESTINATION")
	private String numberDestination;
	
    @NotNull
    @Column(name = "DESCRIPTION")
	private String description;
    
    @NotNull
    @Column(name = "ID_USER")
	private int userId;
	
}
