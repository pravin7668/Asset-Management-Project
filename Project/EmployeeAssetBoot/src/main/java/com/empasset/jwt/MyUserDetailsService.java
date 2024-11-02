package com.empasset.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.empasset.model.Login;
import com.empasset.model.User;
import com.empasset.repo.LoginRepo;
import com.empasset.repo.UserRepo;

public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private LoginRepo loginRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Login credentials=loginRepo.findByEmail(username);
		if(credentials==null) {
			System.out.println("User Not Found");
			throw new UsernameNotFoundException("User Not Found");
		}
		return new MyUserDetail(credentials);
	}

}
