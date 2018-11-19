package fr.sg.kata.v1.repository;

import org.springframework.data.repository.CrudRepository;

import fr.sg.kata.v1.models.Account;

public interface IAccountRepository extends CrudRepository<Account, String> {


}
