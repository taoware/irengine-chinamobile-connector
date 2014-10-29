package com.irengine.connector.controller;

import com.irengine.connector.Constants;

public class OrderCreateResultDto extends ResultDtoBase {

	private String orderId;
	private String b_orderId;
	
	@Override
	public String getDigestCode() {
		return getCode() + getOrderId() + getB_orderId() + Constants.KEY;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getB_orderId() {
		return b_orderId;
	}

	public void setB_orderId(String b_orderId) {
		this.b_orderId = b_orderId;
	}
	
}
