package com.empasset.test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.empasset.model.Asset;

class AssetModelTest {

    @Test
    public void testDefaultConstructor() {
        Asset asset = new Asset();

        assertNotNull(asset);
        assertEquals(0, asset.getAssetId());
        assertNull(asset.getAssetName());
        assertNull(asset.getAssetCategory());
        assertNull(asset.getAssetModel());
        assertNull(asset.getAssetDescription());
        assertEquals(0.0, asset.getAssetValue());
        assertNull(asset.getManufacturingDate());
        assertNull(asset.getExpiryDate());
        assertNull(asset.getImageUrl());
        assertNull(asset.getStatus());
    }

    @Test
    public void testGettersAndSetters() {
        Asset asset = new Asset();
        Date manufacturingDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis());
        
        asset.setAssetId(1);
        asset.setAssetName("Laptop");
        asset.setAssetCategory(Asset.AssetCategory.Laptop);
        asset.setAssetModel("Dell XPS 13");
        asset.setAssetDescription("A high-end laptop.");
        asset.setAssetValue(1200.00);
        asset.setManufacturingDate(manufacturingDate);
        asset.setExpiryDate(expiryDate);
        asset.setImageUrl("http://example.com/image.jpg");
        asset.setStatus(Asset.Status.Available);

        assertEquals(1, asset.getAssetId());
        assertEquals("Laptop", asset.getAssetName());
        assertEquals(Asset.AssetCategory.Laptop, asset.getAssetCategory());
        assertEquals("Dell XPS 13", asset.getAssetModel());
        assertEquals("A high-end laptop.", asset.getAssetDescription());
        assertEquals(1200.00, asset.getAssetValue());
        assertEquals(manufacturingDate, asset.getManufacturingDate());
        assertEquals(expiryDate, asset.getExpiryDate());
        assertEquals("http://example.com/image.jpg", asset.getImageUrl());
        assertEquals(Asset.Status.Available, asset.getStatus());
    }

    @Test
    public void testToString() {
        Asset asset = new Asset();
        Date manufacturingDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis());
        
        asset.setAssetId(1);
        asset.setAssetName("Laptop");
        asset.setAssetCategory(Asset.AssetCategory.Laptop);
        asset.setAssetModel("Dell XPS 13");
        asset.setAssetDescription("A high-end laptop.");
        asset.setAssetValue(1200.00);
        asset.setManufacturingDate(manufacturingDate);
        asset.setExpiryDate(expiryDate);
        asset.setImageUrl("http://example.com/image.jpg");
        asset.setStatus(Asset.Status.Available);

        String expected = "Asset [asset_id=1, assetName=Laptop, assetCategory=Laptop, assetModel=Dell XPS 13, assetDescription=A high-end laptop., assetValue=1200.0, manufacturingDate=" + manufacturingDate + ", expiryDate=" + expiryDate + ", imageUrl=http://example.com/image.jpg, status=Available]";
        assertEquals(expected, asset.toString());
    }
    

    @Test
    public void testParameterizedConstructor() {
        // When
        Asset asset = new Asset(
            1,                                  
            "Dell XPS 13",                      
            Asset.AssetCategory.Laptop,         
            "XPS 13",                           
            "High-performance laptop",          
            1200.00,                            
            new Date(1220227200L * 1000),        
            new Date(1672531200L * 1000),       
            "http://example.com/laptop.jpg",    
            Asset.Status.Available              
        );

        // Then
        assertEquals(1, asset.getAssetId());
        assertEquals("Dell XPS 13", asset.getAssetName());
        assertEquals(Asset.AssetCategory.Laptop, asset.getAssetCategory());
        assertEquals("XPS 13", asset.getAssetModel());
        assertEquals("High-performance laptop", asset.getAssetDescription());
        assertEquals(1200.00, asset.getAssetValue());
        assertEquals(new Date(1220227200L * 1000), asset.getManufacturingDate());
        assertEquals(new Date(1672531200L * 1000), asset.getExpiryDate());
        assertEquals("http://example.com/laptop.jpg", asset.getImageUrl());
        assertEquals(Asset.Status.Available, asset.getStatus());
    }

}
