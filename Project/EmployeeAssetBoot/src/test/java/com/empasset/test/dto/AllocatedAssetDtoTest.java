package com.empasset.test.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.empasset.dto.AllocatedAssetDto;

class AllocatedAssetDtoTest {

    @Test
    public void testDefaultConstructor() {
        AllocatedAssetDto dto = new AllocatedAssetDto();

        assertNotNull(dto);
        assertEquals(0, dto.getRequestId());
        assertEquals(0, dto.getUserId());
        assertNull(dto.getFirstName());
        assertEquals(0, dto.getAssetId());
        assertNull(dto.getAssetName());
    }

    @Test
    public void testGettersAndSetters() {
        AllocatedAssetDto dto = new AllocatedAssetDto();
        
        dto.setRequestId(1);
        dto.setUserId(2);
        dto.setFirstName("John");
        dto.setAssetId(3);
        dto.setAssetName("Laptop");

        assertEquals(1, dto.getRequestId());
        assertEquals(2, dto.getUserId());
        assertEquals("John", dto.getFirstName());
        assertEquals(3, dto.getAssetId());
        assertEquals("Laptop", dto.getAssetName());
    }

    @Test
    public void testToString() {
        AllocatedAssetDto dto = new AllocatedAssetDto();
        
        dto.setRequestId(1);
        dto.setUserId(2);
        dto.setFirstName("John");
        dto.setAssetId(3);
        dto.setAssetName("Laptop");

        String expected = "AllocatedAssetDto [requestId=1, userId=2, firstName=John, assetId=3, assetName=Laptop]";
        assertEquals(expected, dto.toString());
    }
}


