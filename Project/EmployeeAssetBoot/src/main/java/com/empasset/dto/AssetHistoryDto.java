package com.empasset.dto;

import java.sql.Date;

public class AssetHistoryDto {
	
	private String assetName;
	private String assetCategory;;
	private Date issuedDate;
	private Date returnedDate;
	private String status;
	private String assetDescription;
	private String imageUrl;
	
	public String getAssetDescription() {
		return assetDescription;
	}
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public AssetHistoryDto() {
		super();
	}
	public AssetHistoryDto(String assetName, String assetCategory, Date issuedDate, Date returnedDate, String status) {
		super();
		this.assetName = assetName;
		this.assetCategory = assetCategory;
		this.issuedDate = issuedDate;
		this.returnedDate = returnedDate;
		this.status = status;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getAssetCategory() {
		return assetCategory;
	}
	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}
	public Date getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}
	public Date getReturnedDate() {
		return returnedDate;
	}
	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
