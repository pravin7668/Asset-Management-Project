package com.empasset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.empasset.dto.ForgotPasswordDto;
import com.empasset.dto.LoginDto;
import com.empasset.jwt.JWTService;
import com.empasset.model.Admin;
import com.empasset.model.Login;
import com.empasset.model.User;
import com.empasset.repo.AdminRepo;
import com.empasset.repo.LoginRepo;
import com.empasset.repo.UserRepo;

@Service
public class AuthService {
	
	@Autowired
	private LoginRepo loginRepo;
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired 
	private UserRepo userRepo;	
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

	
//	public String loginUser(@RequestBody LoginDto login) {
//		Login credentials=loginRepo.findByEmail(login.getEmail());
//		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));
//		if(authentication.isAuthenticated()) {
//			return jwtService.generateToken(login.getEmail(),credentials.getRole().toString());
//		}
//		else {
//			return "Invalid Credentials";
//		}
//	}
	
	public Login findUserByEmail(String email) {
		return loginRepo.findByEmail(email);
	}
	
	public String forgotPasswordAdmin(ForgotPasswordDto forgotPasswordDto) {
		String newPassword=encoder.encode(forgotPasswordDto.getNewPassword());
		String cmd="update admin set password=? where email=?";
		String cmd1="update login set password=? where email=?";
		jdbcTemplate.update(cmd,new Object[] {newPassword,forgotPasswordDto.getEmail()});
		jdbcTemplate.update(cmd1,new Object[] {newPassword,forgotPasswordDto.getEmail()});
		return "Password Changed Successfully";
	}
	
	public String forgotPasswordUser(ForgotPasswordDto forgotPasswordDto) {
		String newPassword=encoder.encode(forgotPasswordDto.getNewPassword());
		String cmd="update user set password=? where email=?";
		String cmd1="update login set password=? where email=?";
		jdbcTemplate.update(cmd,new Object[] {newPassword,forgotPasswordDto.getEmail()});
		jdbcTemplate.update(cmd1,new Object[] {newPassword,forgotPasswordDto.getEmail()});
		return "Password Changed Successfully";
	}

	public Admin findAdminById(int adminId) {
		return adminRepo.findByAdminId(adminId);
	}

	public User findUserById(int userId) {
		return userRepo.findByUserId(userId);
	}
}
//"{\"token\":\""+
//jwtService.generateToken(email,user1.getRole().toString()) +"\"}"

//jwtService.generateToken(email,credentials.getRole().toString())