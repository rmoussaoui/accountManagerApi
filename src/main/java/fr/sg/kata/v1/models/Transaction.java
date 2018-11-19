package fr.sg.kata.v1.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Transaction {

	@Id
	@GeneratedValue
	private Long transactionId;
	private BigDecimal amount;
	private BigDecimal accountBalance;
	private TransactionType transactionType;
	private LocalDate transactionDate;
	private String details;
	@ManyToOne
	private Account account;
}
