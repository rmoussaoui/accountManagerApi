package fr.sg.kata.v1.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Account {
	@Id
	private String accountId;
	private BigDecimal balance;
	@ManyToOne
	private Client owner;
	
}
