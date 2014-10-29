package com.irengine.connector.controller;

import org.apache.commons.lang3.StringUtils;

import com.irengine.connector.Constants;

public class CodeActionResultDto extends ResultDtoBase {

	private String itemId;
	
	@Override
	public String getDigestCode() {
		return StringUtils.join(new Object[] {getCode(), getItemId(), Constants.KEY});
//		return getCode() + getItemId() + Constants.KEY;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}
