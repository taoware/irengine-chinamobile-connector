package com.irengine.connector.controller;

import com.irengine.connector.Constants;

/*
 * code_notice(output)
 */
public class CodeNoticeDto extends DtoBase {

	private String orderid;
	private String b_orderid;
	private String itemid;
	private String salessysid;
	
	@Override
	public String getDigestCode() {
		return orderid + salessysid + b_orderid + itemid + Constants.KEY;
	}
	
}
