package com.irengine.connector.controller;

import org.apache.commons.lang3.StringUtils;

import com.irengine.connector.Constants;

/*
 * code_send, code_send_once, code_query
 */
public class CodeActionDto extends DtoBase {

	private String orderId;
	private String b_orderId;
	private String salessysid;
	
	@Override
	public String getDigestCode() {
		return StringUtils.join(new Object[] {getOrderId(), getSalessysid(), getB_orderId(), Constants.KEY});
//		return getOrderId() + getSalessysid() + getB_orderId() + Constants.KEY;
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

	public String getSalessysid() {
		return salessysid;
	}

	public void setSalessysid(String salessysid) {
		this.salessysid = salessysid;
	}
	
}
