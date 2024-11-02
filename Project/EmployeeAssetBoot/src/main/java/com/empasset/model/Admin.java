package com.empasset.model;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
	public enum Status {Active,Inactive}
	@Id
	@Column(name = "admin_id")
	private int adminId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "phoneno")
	private String phoneno;
	@Column(name = "address")
	private String address;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;


public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Admin() {
	}
	
	
	public Admin(int adminId, String firstName, String lastName, String phoneno, String address, String email,
			String password, Status status) {
		this.adminId = adminId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneno = phoneno;
		this.address = address;
		this.email = email;
		this.password = password;
		this.status = status;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneno="
				+ phoneno + ", address=" + address + ", email=" + email + ", password=" + password + ", status="
				+ status + "]";
	}
	
	

}
