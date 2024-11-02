package com.empasset.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empasset.dto.ForgotPasswordDto;
import com.empasset.dto.LoginDto;
import com.empasset.dto.MailHeaders;
import com.empasset.model.Admin;
import com.empasset.model.Login;
import com.empasset.model.User;
import com.empasset.service.AuthService;
import com.empasset.service.UserService;
import com.empasset.util.EmailService;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@Autowired
	private EmailService emailService;
	
//	@PostMapping(value = "/loginUser")
//	public String loginUser(@RequestBody LoginDto login) {
//		return authService.loginUser(login);
//	}
	
	@GetMapping(value = "/findUserByEmail/{email}")
	public Login findUserByEmail(@PathVariable String email) {
		return authService.findUserByEmail(email);
	}
	
	@GetMapping(value = "/findAdminById/{adminId}")
	public Admin findAdminById(@PathVariable int adminId) {
		return authService.findAdminById(adminId);
	}
	
	@GetMapping(value = "/findUserById/{userId}")
	public User findUserById(@PathVariable int userId) {
		return authService.findUserById(userId);
	}
	
	@PostMapping(value = "/sendMail")
	public String sendMail(@RequestBody MailHeaders mailHeaders) {
		emailService.sendSimpleEmail(mailHeaders.getToEmail(), mailHeaders.getSubject(), mailHeaders.getBody());
		return "Mail Sent Successfully";
	}
	
	@PostMapping(value = "/forgotPasswordAdmin")
	public String forgotPasswordAdmin(@RequestBody ForgotPasswordDto forgotPasswordDto) {
		return authService.forgotPasswordAdmin(forgotPasswordDto);
	}
	
	@PostMapping(value = "/forgotPasswordUser")
	public String forgotPasswordUser(@RequestBody ForgotPasswordDto forgotPasswordDto) {
		return authService.forgotPasswordUser(forgotPasswordDto);
	}
}
