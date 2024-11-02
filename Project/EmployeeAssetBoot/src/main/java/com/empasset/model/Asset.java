package com.empasset.model;

import java.sql.Date;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "asset")
public class Asset {
	public enum AssetCategory{Laptop,Furniture,Gadgets,Car}
	public enum Status{Available,Issued}
	@Id
	@Column(name = "asset_id")
	private int assetId;
	@Column(name = "asset_name")
	private String assetName;
	@Enumerated(EnumType.STRING)
	@Column(name = "asset_category")
	private AssetCategory assetCategory;
	@Column(name = "asset_model")
	private String assetModel;
	@Column(name = "asset_description")
	private String assetDescription;
	@Column(name = "asset_value")
	private double assetValue;
	@Column(name = "manufacturing_date")
	private Date manufacturingDate;
	@Column(name = "expiry_date")
	private Date expiryDate;
	@Column(name = "image_url")
	private String imageUrl;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	public Asset() {
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public AssetCategory getAssetCategory() {
		return assetCategory;
	}
	public void setAssetCategory(AssetCategory assetCategory) {
		this.assetCategory = assetCategory;
	}
	public String getAssetModel() {
		return assetModel;
	}
	public void setAssetModel(String assetModel) {
		this.assetModel = assetModel;
	}
	public String getAssetDescription() {
		return assetDescription;
	}
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	public double getAssetValue() {
		return assetValue;
	}
	public void setAssetValue(double assetValue) {
		this.assetValue = assetValue;
	}
	public Date getManufacturingDate() {
		return manufacturingDate;
	}
	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Asset(int assetId, String assetName, AssetCategory assetCategory, String assetModel, String assetDescription,
			double assetValue, Date manufacturingDate, Date expiryDate, String imageUrl, Status status) {
		super();
		this.assetId = assetId;
		this.assetName = assetName;
		this.assetCategory = assetCategory;
		this.assetModel = assetModel;
		this.assetDescription = assetDescription;
		this.assetValue = assetValue;
		this.manufacturingDate = manufacturingDate;
		this.expiryDate = expiryDate;
		this.imageUrl = imageUrl;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Asset [asset_id=" + assetId + ", assetName=" + assetName + ", assetCategory=" + assetCategory
				+ ", assetModel=" + assetModel + ", assetDescription=" + assetDescription + ", assetValue=" + assetValue
				+ ", manufacturingDate=" + manufacturingDate + ", expiryDate=" + expiryDate + ", imageUrl=" + imageUrl
				+ ", status=" + status + "]";
	}
	
	
}