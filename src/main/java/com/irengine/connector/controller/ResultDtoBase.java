package com.irengine.connector.controller;

import java.security.MessageDigest;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResultDtoBase {

	private String code;
	private String msg;
	private String sign;
	private String sign_type;
	
	public ResultDtoBase() {
		this.sign_type = "MD5";
	}

	@JsonIgnore
	public String getDigestCode() { return getCode(); };

	@JsonIgnore
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
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
