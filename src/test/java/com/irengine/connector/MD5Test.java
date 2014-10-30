package com.irengine.connector;

import static org.junit.Assert.assertEquals;

import java.security.MessageDigest;

import org.junit.Test;

import com.irengine.connector.controller.OrderCreateResultDto;


public class MD5Test {
	
	private String getDigest(String code) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			md.update(code.getBytes());
			
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
	
	@Test
	public void testMD5() {
		OrderCreateResultDto dto = new OrderCreateResultDto();
		dto.setCode("1000");
		dto.setOrderid("1430263860");
		dto.setB_orderid("CM-1430263860");
		
		assertEquals(dto.getDigest(), getDigest("10001430263860CM-143026386012345678"));
		
	}
}
