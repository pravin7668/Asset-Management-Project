//package com.empasset.test.model;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.sql.Date;
//
//import org.junit.jupiter.api.Test;
//
//import com.empasset.model.AssetAllocation;
//
//class AssetAllocationModelTest {
//
//	@Test
//    public void testDefaultConstructor() {
//        AssetAllocation assetAllocation = new AssetAllocation();
//
//        assertNotNull(assetAllocation);
//        assertEquals(0, assetAllocation.getAssetAllocationId());
//        assertEquals(0, assetAllocation.getRequestId());
//        assertEquals(0, assetAllocation.getAdminId());
//        assertNull(assetAllocation.getIssuedDate());
//        assertNull(assetAllocation.getComment());
//    }
//
//    @Test
//    public void testGettersAndSetters() {
//        AssetAllocation assetAllocation = new AssetAllocation();
//        Date issuedDate = new Date(System.currentTimeMillis());
//        
//        assetAllocation.setAssetAllocationId(1);
//        assetAllocation.setRequestId(100);
//        assetAllocation.setAdminId(10);
//        assetAllocation.setIssuedDate(issuedDate);
//        assetAllocation.setComment("Initial allocation");
//
//        assertEquals(1, assetAllocation.getAssetAllocationId());
//        assertEquals(100, assetAllocation.getRequestId());
//        assertEquals(10, assetAllocation.getAdminId());
//        assertEquals(issuedDate, assetAllocation.getIssuedDate());
//        assertEquals("Initial allocation", assetAllocation.getComment());
//    }
//
//    @Test
//    public void testToString() {
//        AssetAllocation assetAllocation = new AssetAllocation();
//        Date issuedDate = new Date(System.currentTimeMillis());
//        
//        assetAllocation.setAssetAllocationId(1);
//        assetAllocation.setRequestId(100);
//        assetAllocation.setAdminId(10);
//        assetAllocation.setIssuedDate(issuedDate);
//        assetAllocation.setComment("Initial allocation");
//
//        String expected = "AssetAllocation [assetAllocationId=1, requestId=100, adminId=10, issuedDate=" + issuedDate + ", comment=Initial allocation]";
//        assertEquals(expected, assetAllocation.toString());
//    }
//    @Test
//    public void testParameterizedConstructor() {
//        AssetAllocation assetAllocation = new AssetAllocation(
//            1,
//            1001,
//            2002,
//            new Date(1234567890L * 1000),
//            "Comment about allocation"
//        );
//
//        assertEquals(1, assetAllocation.getAssetAllocationId());
//        assertEquals(1001, assetAllocation.getRequestId());
//        assertEquals(2002, assetAllocation.getAdminId());
//        assertEquals(new Date(1234567890L * 1000), assetAllocation.getIssuedDate());
//        assertEquals("Comment about allocation", assetAllocation.getComment());
//    }
//
//}
