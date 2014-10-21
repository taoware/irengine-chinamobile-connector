package com.irengine.connector.controller;

import com.irengine.connector.Constants;

public class OrderCreateDto extends DtoBase {

	private String orderId;
	private String userId;
	private Long amount;
	private Long nums;
	private Long create_time;
	private String mobile;
	private String businessid;
	private String goodid;
	private String salessysid;
	
	public String getMD5() {
		return orderId + salessysid + businessid + userId + goodid + nums + amount + Constants.KEY;
	}
	
}
