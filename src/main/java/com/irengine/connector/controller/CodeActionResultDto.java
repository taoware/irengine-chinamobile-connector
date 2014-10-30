package com.irengine.connector.controller;

import org.apache.commons.lang3.StringUtils;

import com.irengine.connector.Constants;

public class CodeActionResultDto extends ResultDtoBase {

	private String itemid;
	
	@Override
	public String getDigestCode() {
		return StringUtils.join(new Object[] {getCode(), getItemid(), Constants.KEY});
//		return getCode() + getItemid() + Constants.KEY;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	
}
