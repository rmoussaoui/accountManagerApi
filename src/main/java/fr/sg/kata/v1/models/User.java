package fr.sg.kata.v1.models;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="userType")
@DiscriminatorValue("user")
@Entity
@Table(name="user")
public class User {
	@Id
	private Long userId;
	private String username;
	private String password;
	private String role;
	//Collection<GrantedAuthority> authorities;
}
