package com.irengine.connector.controller;

import com.irengine.connector.Constants;

/*
 * code_send, code_send_once, code_query
 */
public class CodeActionDto extends DtoBase {

	private String orderId;
	private String b_orderId;
	private String salessysid;
	
	public String getMD5() {
		return orderId + salessysid + b_orderId + Constants.KEY;
	}
	
}
