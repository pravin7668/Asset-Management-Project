package com.empasset.test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.empasset.model.User;
import com.empasset.model.User.Status;

class UserModelTest {

    @Test
    public void testDefaultConstructor() {
        User user = new User();

        assertNotNull(user);
        assertEquals(0, user.getUserId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getPhoneno());
        assertNull(user.getAddress());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getStatus());
    }

    @Test
    public void testGettersAndSetters() {
        User user = new User();
        
        user.setUserId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhoneno("1234567890");
        user.setAddress("123 Main St");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setStatus(Status.Active);

        assertEquals(1, user.getUserId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("1234567890", user.getPhoneno());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(Status.Active, user.getStatus());
    }

    @Test
    public void testToString() {
        User user = new User();
        
        user.setUserId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhoneno("1234567890");
        user.setAddress("123 Main St");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setStatus(Status.Active);

        String expected = "User [userId=1, firstName=John, lastName=Doe, phoneno=1234567890, address=123 Main St, email=john.doe@example.com, password=password123, status=Active]";
        assertEquals(expected, user.toString());
    }
    @Test
    public void testParameterizedConstructor() {
        User user = new User(
            1,
            "John",
            "Doe",
            "1234567890",
            "123 Elm Street",
            "john.doe@example.com",
            "securePassword",
            Status.Active
        );

        assertEquals(1, user.getUserId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("1234567890", user.getPhoneno());
        assertEquals("123 Elm Street", user.getAddress());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("securePassword", user.getPassword());
        assertEquals(Status.Active, user.getStatus());
    }

}
