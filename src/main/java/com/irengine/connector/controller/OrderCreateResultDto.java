package com.irengine.connector.controller;

import org.apache.commons.lang3.StringUtils;

import com.irengine.connector.Constants;

public class OrderCreateResultDto extends ResultDtoBase {

	private String orderid;
	private String b_orderid;
	
	@Override
	public String getDigestCode() {
		return StringUtils.join(new Object[] {getCode(), getOrderid(), getB_orderid(), Constants.KEY});
//		return getCode() + getOrderid() + getB_orderid() + Constants.KEY;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getB_orderid() {
		return b_orderid;
	}

	public void setB_orderid(String b_orderid) {
		this.b_orderid = b_orderid;
	}
	
}
