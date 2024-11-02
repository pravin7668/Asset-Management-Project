package com.empasset.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empasset.model.Login;

public interface LoginRepo extends JpaRepository<Login, Integer>{
	public Login findByEmail(String email);
}
