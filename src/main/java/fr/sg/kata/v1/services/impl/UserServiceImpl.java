package fr.sg.kata.v1.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.sg.kata.v1.models.User;
import fr.sg.kata.v1.repository.IUserRepository;
import fr.sg.kata.v1.services.IUserService;


@Service
public class UserServiceImpl implements UserDetailsService, IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		User user = userRepository.findByUsername(username);
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
				
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}


	@Override
	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return user;
	}

}
