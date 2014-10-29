package com.irengine.connector.controller;

import com.irengine.connector.Constants;

/*
 * code_notice(output)
 */
public class CodeNoticeDto extends DtoBase {

	private String orderId;
	private String b_orderId;
	private String itemId;
	private String salessysid;
	
	@Override
	public String getDigestCode() {
		return orderId + salessysid + b_orderId + itemId + Constants.KEY;
	}
	
}
