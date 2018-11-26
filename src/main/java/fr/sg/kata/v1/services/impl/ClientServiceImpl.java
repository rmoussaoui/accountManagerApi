package fr.sg.kata.v1.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.sg.kata.v1.models.Client;
import fr.sg.kata.v1.models.User;
import fr.sg.kata.v1.services.IClientService;
import fr.sg.kata.v1.services.IUserService;

@Service
public class ClientServiceImpl implements IClientService {
	
	@Autowired
	private IUserService userService;

	@Override
	public boolean isClient(String username) {
		
		User user = userService.getUserByUsername(username);
		return user instanceof Client;
	}

}
