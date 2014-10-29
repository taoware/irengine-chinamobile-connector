package com.irengine.connector.controller;

import org.apache.commons.lang3.StringUtils;

import com.irengine.connector.Constants;

/*
 * code_cancel
 */
public class CodeCancelDto extends DtoBase {

	private String orderId;
	private String b_orderId;
	private String itemId;
	private String cancel_msg;
	private String backup;
	private String salessysid;
	
	@Override
	public String getDigestCode() {
		return StringUtils.join(new Object[] {getOrderId(), getSalessysid(), getB_orderId(), getItemId(), Constants.KEY});
//		return getOrderId() + getSalessysid() + getB_orderId() + getItemId() + Constants.KEY;
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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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
