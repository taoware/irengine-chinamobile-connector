package com.irengine.connector.controller;

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
		return orderId + salessysid + b_orderId + itemId + Constants.KEY;
	}
	
}
