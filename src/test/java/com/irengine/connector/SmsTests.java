package com.irengine.connector;

import java.util.Random;

import org.junit.Test;

import com.irengine.connector.service.SmsHelper;

public class SmsTests {
	
	@Test
	public void testSendCode() {
		String mobile = "13601711251";

		for(int i = 1; i <= 5; i++) {
			String code = getCoupon(50);
			System.out.println(code);
			
			String message_1 = String.format(Constants.get("message_1_2"), 50, code, "147369");
			String message_2 = Constants.get("message_2_2");

			SmsHelper.send(mobile, message_1);
			SmsHelper.send(mobile, message_2);
		}
	}
	
	private static Random rng = new Random();
	
	private static String Populate(int max, String prefix) {

		Integer next = rng.nextInt(max) + 1;
		return String.format("%s%04d", prefix, next);

	}
	
	public static String getCoupon(long amount) {
		if (50 == amount)
			return Populate(9999, "YHQ201403100");
		else if (300 == amount)
			return Populate(9999, "YHQ201404100");
		else
			return Populate(9999, "YHQ201499100");
	}
	
}
