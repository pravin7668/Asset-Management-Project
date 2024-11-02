package com.empasset.model;

import java.sql.Date;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "asset_allocation")
public class AssetAllocation {
	@Id
	@Column(name = "asset_allocation_id")
	private int assetAllocationId;
	@Column(name = "request_id")
	private int requestId;
	@Column(name = "admin_id")
	private int adminId;
	@Column(name = "issued_date")
	private Date issuedDate;
	@Column(name = "returned_date")
	private Date returnedDate;
	
	public AssetAllocation() {
		
	}
	public AssetAllocation(int assetAllocationId, int requestId, int adminId, Date issuedDate, String comment) {
		super();
		this.assetAllocationId = assetAllocationId;
		this.requestId = requestId;
		this.adminId = adminId;
		this.issuedDate = issuedDate;
	}

	public int getAssetAllocationId() {
		return assetAllocationId;
	}
	public void setAssetAllocationId(int assetAllocationId) {
		this.assetAllocationId = assetAllocationId;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
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


	@Override
	public String toString() {
		return "AssetAllocation [assetAllocationId=" + assetAllocationId + ", requestId=" + requestId + ", adminId="
				+ adminId + ", issuedDate=" + issuedDate + ", returnedDate=" + returnedDate + "]";
	}


	
	
	
}
