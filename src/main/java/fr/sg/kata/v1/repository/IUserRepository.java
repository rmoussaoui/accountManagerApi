package fr.sg.kata.v1.repository;

import fr.sg.kata.v1.models.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, String>{

	User findByUsername(String username);
}
