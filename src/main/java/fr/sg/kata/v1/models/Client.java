package fr.sg.kata.v1.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Client {
	
	@Id
	private String clientId;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
}
