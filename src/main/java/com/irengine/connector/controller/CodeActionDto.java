package com.irengine.connector.controller;

import org.apache.commons.lang3.StringUtils;

import com.irengine.connector.Constants;

/*
 * code_send, code_send_once, code_query
 */
public class CodeActionDto extends DtoBase {

	private String orderid;
	private String b_orderid;
	private String salessysid;
	
	@Override
	public String getDigestCode() {
		return StringUtils.join(new Object[] {getOrderid(), getSalessysid(), getB_orderid(), Constants.KEY});
//		return getOrderid() + getSalessysid() + getB_orderid() + Constants.KEY;
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

	public String getSalessysid() {
		return salessysid;
	}

	public void setSalessysid(String salessysid) {
		this.salessysid = salessysid;
	}
	
}
