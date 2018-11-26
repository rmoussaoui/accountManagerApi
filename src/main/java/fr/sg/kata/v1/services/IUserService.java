package fr.sg.kata.v1.services;

import fr.sg.kata.v1.models.User;

public interface IUserService {
	
	User getUserByUsername(String username);

}
