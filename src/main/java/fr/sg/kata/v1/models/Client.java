package fr.sg.kata.v1.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="client")
@DiscriminatorValue("client")
public class Client extends User{
	private String firstname;
	private String lastname;
}
