package fr.sg.kata.v1.data;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import fr.sg.kata.v1.models.TransactionType;
import lombok.Data;

@Data
public class TransactionRequestData {
	
	@NotNull(message="{transaction.amount.not.null}")
	@Positive(message="{transaction.amount.positive}")
	private BigDecimal amount;
	private TransactionType transactionType;
	private String details;
	
}
