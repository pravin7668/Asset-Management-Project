package com.empasset.jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.empasset.model.Login;
import com.empasset.model.User;

public class MyUserDetail implements UserDetails{
	
	private Login credentials;
	
	public MyUserDetail(Login credentials) {
		this.credentials=credentials;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_"+credentials.getRole().toString())) ;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return credentials.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return credentials.getEmail();
	}

}
