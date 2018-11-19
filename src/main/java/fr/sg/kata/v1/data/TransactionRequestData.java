package fr.sg.kata.v1.data;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import fr.sg.kata.v1.models.TransactionType;

public class TransactionRequestData {
	
	@NotNull(message="{transaction.amount.not.null}")
	@Positive(message="{transaction.amount.positive}")
	private BigDecimal amount;
	private TransactionType transactionType;
	private String details;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
