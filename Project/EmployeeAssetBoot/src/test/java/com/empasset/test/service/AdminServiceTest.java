//package com.empasset.test.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.empasset.exception.ResourceNotFoundException;
//import com.empasset.model.Admin;
//import com.empasset.model.Admin.Status;
//import com.empasset.service.AdminService;
//import com.util.EncryptPassword;
//
//class AdminServiceTest {
//
//	@Mock
//	AdminService adminServiceMock;
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//	}
//	
//	@Test
//	public void testShowAll() {
//		List<Admin> list=new ArrayList<Admin>();
//		list.add(new Admin(1, "Pravin", "K", "123456789", "apartment", "p@gmail.com", EncryptPassword.getCode("pravin"), Status.Active));
//		list.add(new Admin(1, "Barath", "SV", "123126789", "musuri", "b@gmail.com", EncryptPassword.getCode("barath"), Status.Active));
//		
//		when(adminServiceMock.showAll()).thenReturn(list);
//		
//		assertEquals(list.size(), adminServiceMock.showAll().size());
//	}
//	
//	@Test
//	public void testFindById() {
//		Admin a1=new Admin(1, "Pravin", "K", "123456789", "Apartment", "pravin@gmail.com", "pravin",Status.Active);
//		when(adminServiceMock.findById(1)).thenReturn(a1);
////		when(adminServiceMock.findById(2)).thenThrow(new ResourceNotFoundException("Cant Find Admin with ID 2"));
//		assertEquals(a1,adminServiceMock.findById(1));
////		assertEquals("Cant Find Admin with ID 2", adminServiceMock.findById(2));
//	}
//	
//	@Test 
//	public void testLogin() {
//		List<Admin> list=new ArrayList<Admin>();
//		list.add(new Admin(1, "Pravin", "K", "123456789", "apartment", "p@gmail.com", EncryptPassword.getCode("pravin"), Status.Active));
//		list.add(new Admin(1, "Barath", "SV", "123126789", "musuri", "b@gmail.com", EncryptPassword.getCode("barath"), Status.Active));
//		
//		when(adminServiceMock.login("p@gmail.com",EncryptPassword.getCode("pravin"))).thenReturn(1);
//		when(adminServiceMock.login("invalid@gmail.com",EncryptPassword.getCode("invalid"))).thenReturn(-1);
//		
//		assertEquals(1, adminServiceMock.login("p@gmail.com",EncryptPassword.getCode("pravin")));
//		assertEquals(-1, adminServiceMock.login("invalid@gmail.com",EncryptPassword.getCode("invalid")));
//
//	}
//	
//	
//
//}
