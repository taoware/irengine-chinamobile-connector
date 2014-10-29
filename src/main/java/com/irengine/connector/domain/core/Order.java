package com.irengine.connector.domain.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ss_order")
public class Order extends EntityBase {

	private String orderId;
	private String vendorOrderId;
	private Date orderTime;
	private String userId;
	private String userMobile;
	private String itemId;
	private Long itemCount;
	private Long totalPrice;
	private String vendorId;
	private String applicationId;
	
	@Column(nullable = false, unique=true)
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(nullable = false)
	public String getVendorOrderId() {
		return vendorOrderId;
	}

	public void setVendorOrderId(String vendorOrderId) {
		this.vendorOrderId = vendorOrderId;
	}

	@Column(nullable = false)
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(nullable = false)
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	@Column(nullable = false)
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(nullable = false)
	public Long getItemCount() {
		return itemCount;
	}

	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}

	@Column(nullable = false)
	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	@Column(nullable = false)
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
}
