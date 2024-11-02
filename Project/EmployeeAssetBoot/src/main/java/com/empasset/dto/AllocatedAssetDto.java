package com.empasset.dto;

import java.sql.Date;

public class AllocatedAssetDto {
	private int requestId;
	private int adminId;
	private int userId;
	private String firstName;
	private int assetId;
	private String assetName;
	private Date issuedDate;
	private String requestType;
	private String status;
	private String auditStatus; 
	private String imageUrl; 
	private String assetDescription;
	
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


	public AllocatedAssetDto() {
	}
	
	
	public String getRequestType() {
		return requestType;
	}


	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getAuditStatus() {
		return auditStatus;
	}


	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}


	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public Date getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}
	
	
	
	
}
