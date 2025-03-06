package com.example.Book.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.Book.Entity.User;
import com.example.Book.Entity.UserPrincipal;
import com.example.Book.Repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	User user= repo.findByName(username);
	
	if (user==null) {
		System.out.println("User 404");
		throw new UsernameNotFoundException("User 404");
	}
		 return new UserPrincipal(user);
	}

}
