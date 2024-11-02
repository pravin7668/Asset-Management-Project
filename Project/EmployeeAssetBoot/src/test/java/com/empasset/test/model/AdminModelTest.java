package com.empasset.test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.empasset.model.Admin;
import com.empasset.model.Admin.Status;

class AdminModelTest {

	@Test
	void testGettersAndSetters() {
		Admin admin=new Admin();
		admin.setAdminId(1);
		admin.setFirstName("Pravin");
		admin.setLastName("K");
		admin.setAddress("Priyas's amrtha apartment");
		admin.setPhoneno("6379253389");
		admin.setEmail("pravin@gmail.com");
		admin.setPassword("pravin");
		admin.setStatus(Status.Active);
		assertEquals(1, admin.getAdminId());
		assertEquals("Pravin", admin.getFirstName());
		assertEquals("K", admin.getLastName());
		assertEquals("Priyas's amrtha apartment", admin.getAddress());
		assertEquals("6379253389", admin.getPhoneno());
		assertEquals("pravin@gmail.com", admin.getEmail());
		assertEquals("pravin", admin.getPassword());
		assertEquals(Status.Active, admin.getStatus());
		
	}
    @Test
    public void testToString() {
        Admin admin = new Admin();
        admin.setAdminId(1);
        admin.setFirstName("John");
        admin.setLastName("Doe");
        admin.setPhoneno("1234567890");
        admin.setAddress("123 Street");
        admin.setEmail("john.doe@example.com");
        admin.setPassword("password123");
        admin.setStatus(Status.Active);

        String expected = "Admin [adminId=1, firstName=John, lastName=Doe, phoneno=1234567890, address=123 Street, email=john.doe@example.com, password=password123, status=Active]";
        assertEquals(expected, admin.toString());
    }
    
    @Test
    public void testDefaultConstructor() {
        Admin admin = new Admin();

        assertNotNull(admin);
        assertEquals(0, admin.getAdminId());
        assertNull(admin.getFirstName());
        assertNull(admin.getLastName());
        assertNull(admin.getPhoneno());
        assertNull(admin.getAddress());
        assertNull(admin.getEmail());
        assertNull(admin.getPassword());
        assertNull(admin.getStatus());
    }
    
    @Test
    public void testParameterizedConstructor() {
        Admin admin = new Admin(1, "John", "Doe", "1234567890", "123 Main St", "john.doe@example.com", "password123",Status.Active);

        assertEquals(1, admin.getAdminId());
        assertEquals("John", admin.getFirstName());
        assertEquals("Doe", admin.getLastName());
        assertEquals("1234567890", admin.getPhoneno());
        assertEquals("123 Main St", admin.getAddress());
        assertEquals("john.doe@example.com", admin.getEmail());
        assertEquals("password123", admin.getPassword());
        assertEquals(Status.Active, admin.getStatus());
    }


}
