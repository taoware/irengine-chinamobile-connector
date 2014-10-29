package com.irengine.connector.controller;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

public class DtoBase {

	private String method;
	private String format;
	private String sign;
	private String sign_type;
	
	public String getDigestCode() { return StringUtils.EMPTY; };

	public String getDigest() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			md.update(this.getDigestCode().getBytes());
			
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			
			return sb.toString();

		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean isValid() {
		return StringUtils.equals(getDigest(), getSign());
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
}
