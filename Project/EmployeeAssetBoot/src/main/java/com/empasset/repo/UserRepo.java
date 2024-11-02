package com.empasset.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empasset.model.User;
import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	public User findByEmail(String email);
	public User findByUserId(int userId);
	
}
