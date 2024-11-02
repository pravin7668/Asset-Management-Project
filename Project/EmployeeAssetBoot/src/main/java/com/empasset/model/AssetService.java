package com.empasset.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AssetService {
	public enum ServiceType{Malfunction,Repair,Lost}
	public enum Status {Pending,Serviced}
	@Id
	@Column(name = "service_id")
	private int serviceId;
	@Column(name = "request_id")
	private int requestId;
	@Column(name = "admin_id")
	private int adminId;
	@Column(name = "ervice_type")
	private ServiceType serviceType;
	@Column(name = "status")
	private Status status;
	public AssetService() {
	}
	
	public AssetService(int serviceId, int requestId, int adminId, ServiceType serviceType, Status status) {
		super();
		this.serviceId = serviceId;
		this.requestId = requestId;
		this.adminId = adminId;
		this.serviceType = serviceType;
		this.status = status;
	}

	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
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
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AssetService [serviceId=" + serviceId + ", requestId=" + requestId + ", adminId=" + adminId
				+ ", serviceType=" + serviceType + ", status=" + status + "]";
	}
	
	
	
	
	
}
