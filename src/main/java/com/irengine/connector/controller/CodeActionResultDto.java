package com.irengine.connector.controller;

import com.irengine.connector.Constants;

public class CodeActionResultDto extends ResultDtoBase {

	private String itemId;
	
	public String getMD5() {
		return code + itemId + Constants.KEY;
	}
	
}
