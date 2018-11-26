package fr.sg.kata.v1.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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
