package com.irengine.connector.controller;

import com.irengine.connector.Constants;

public class OrderCreateResultDto extends ResultDtoBase {

	private String orderId;
	private String b_orderId;
	
	public String getMD5() {
		return code + orderId + b_orderId + Constants.KEY;
	}
	
}
