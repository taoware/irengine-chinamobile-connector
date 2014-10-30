package com.irengine.connector.controller;

import org.apache.commons.lang3.StringUtils;

import com.irengine.connector.Constants;

/*
 * code_cancel
 */
public class CodeCancelDto extends DtoBase {

	private String orderid;
	private String b_orderid;
	private String itemid;
	private String cancel_msg;
	private String backup;
	private String salessysid;
	
	@Override
	public String getDigestCode() {
		return StringUtils.join(new Object[] {getOrderid(), getSalessysid(), getB_orderid(), getItemid(), Constants.KEY});
//		return getOrderid() + getSalessysid() + getB_orderid() + getItemid() + Constants.KEY;
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

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getCancel_msg() {
		return cancel_msg;
	}

	public void setCancel_msg(String cancel_msg) {
		this.cancel_msg = cancel_msg;
	}

	public String getBackup() {
		return backup;
	}

	public void setBackup(String backup) {
		this.backup = backup;
	}

	public String getSalessysid() {
		return salessysid;
	}

	public void setSalessysid(String salessysid) {
		this.salessysid = salessysid;
	}
	
}
