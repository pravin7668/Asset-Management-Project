package com.empasset.model;

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
@Table(name = "asset_request")
public class AssetRequest {
	public enum RequestType{AssetRequest,AssetReturn}
	public enum Status {Pending,Approved,Rejected}
	@Id
	@Column(name = "request_id")
	private int requestId;
	@Column(name = "user_id")
	private int userId;
	@Column(name = "asset_id")
	private int assetId;
	@Enumerated(EnumType.STRING)
	@Column(name = "request_type")
	private RequestType requestType;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	public AssetRequest() {
	}
	public AssetRequest(int requestId, int userId, int assetId, Status status) {
		super();
		this.requestId = requestId;
		this.userId = userId;
		this.assetId = assetId;
		this.status = status;
	}



	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public RequestType getRequestType() {
		return requestType;
	}
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	@Override
	public String toString() {
		return "AssetRequest [requestId=" + requestId + ", userId=" + userId + ", assetId=" + assetId + ", requestType="
				+ requestType + ", status=" + status + "]";
	}
	
	









	

}
