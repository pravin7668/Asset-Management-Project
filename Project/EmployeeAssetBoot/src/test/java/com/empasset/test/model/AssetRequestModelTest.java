package com.empasset.test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.empasset.model.AssetRequest;

class AssetRequestModelTest {

    @Test
    public void testDefaultConstructor() {
        AssetRequest assetRequest = new AssetRequest();
        
        assertNotNull(assetRequest);
        assertEquals(0, assetRequest.getRequestId());
        assertEquals(0, assetRequest.getUserId());
        assertEquals(0, assetRequest.getAssetId());
        assertNull(assetRequest.getStatus());
    }

    @Test
    public void testGettersAndSetters() {
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setRequestId(1);
        assetRequest.setUserId(1);
        assetRequest.setAssetId(100);
        assetRequest.setStatus(AssetRequest.Status.Pending);
        assertEquals(1, assetRequest.getRequestId());
        assertEquals(1, assetRequest.getUserId());
        assertEquals(100, assetRequest.getAssetId());
        assertEquals(AssetRequest.Status.Pending, assetRequest.getStatus());
    }

    @Test
    public void testToString() {
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setRequestId(1);
        assetRequest.setUserId(1);
        assetRequest.setAssetId(100);
        assetRequest.setStatus(AssetRequest.Status.Pending);

        String expected = "AssetRequest [requestId=1, userId=1, assetId=100, status=Pending]";
        assertEquals(expected, assetRequest.toString());
    }
    @Test
    public void testParameterizedConstructor() {
        AssetRequest assetRequest = new AssetRequest(
        	1,
            1,
            100,
            AssetRequest.Status.Approved
        );

        assertEquals(1, assetRequest.getUserId());
        assertEquals(100, assetRequest.getAssetId());
        assertEquals(AssetRequest.Status.Approved, assetRequest.getStatus());
    }

}
