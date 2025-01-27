package com.empasset.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empasset.model.Admin;
import java.util.List;


@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer>{
	public Admin findByEmail(String email);
	public Admin findByAdminId(int adminId);
}
