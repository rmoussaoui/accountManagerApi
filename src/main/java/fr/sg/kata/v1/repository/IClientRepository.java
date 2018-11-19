package fr.sg.kata.v1.repository;

import org.springframework.data.repository.CrudRepository;

import fr.sg.kata.v1.models.Client;


public interface IClientRepository extends CrudRepository<Client,String> {
	
	Client findByUsername(String username);

}
