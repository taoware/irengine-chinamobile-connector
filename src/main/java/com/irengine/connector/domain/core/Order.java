package com.irengine.connector.domain.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

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
	public enum STATUS {
		Created(0), Not_Sent(1), Sent_Success(2), Sent_Failure(3), Canceled(9);
		
	    private final int value;
	    private STATUS(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	private STATUS status;
	private Set<OrderItemCode> itemCodes;
	
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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade=CascadeType.ALL)
	public Set<OrderItemCode> getItemCodes() {
		return itemCodes;
	}

	public void setItemCodes(Set<OrderItemCode> itemCodes) {
		this.itemCodes = itemCodes;
	}
	
	@Transient
	public String getItems() {
		List<String> items = new ArrayList<String>();
		for (OrderItemCode itemCode : itemCodes)
		{
			items.add(itemCode.getItemCode());
		}
		return StringUtils.join(items, ",");
	}

	@Transient
	public String getItemsStatus() {
		
		
		List<String> itemsStatus = new ArrayList<String>();
		for (OrderItemCode itemCode : itemCodes)
		{
			int itemStatus = 1;
			if (OrderItemCode.STATUS.Unused.equals(itemCode.getStatus())) {
				if (Order.STATUS.Sent_Success.equals(status))
					itemStatus = 1;
				else
					itemStatus = 4;
			}
			else if (OrderItemCode.STATUS.Canceled.equals(itemCode.getStatus())) {
				itemStatus = 2;
			}
			else if (OrderItemCode.STATUS.Canceled.equals(itemCode.getStatus())) {
				itemStatus = 3;
			}
			itemsStatus.add(itemCode.getItemCode() + "|" + itemStatus);
		}
		return StringUtils.join(itemsStatus, ",");
	}

}
