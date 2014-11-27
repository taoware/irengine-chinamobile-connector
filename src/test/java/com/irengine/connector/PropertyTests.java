package com.irengine.connector;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.irengine.connector.service.SmsHelper;

public class PropertyTests {

	@Test
	public void testGetFromPropertiesFile() throws IOException {
		String version = Constants.get("sample_version");
		String key = Constants.get("sample_key");
		
		assertEquals("1", version);
		assertEquals("87654321", key);
	}
	
	@Test
	public void testSend() {
		/*
		尊敬的客户，您在上海移动兑换的教育超市%d元电子券序列号：%s，密码：%s，(1/2)
		有效期至2015年6月30日，逾期作废。您可以凭券至指定教育超市门店领取精选商品。门店信息可查询教育超市官网。(2/2)
		*/
		String message_1 = String.format(Constants.get("message_1_2"), 50, "YHQ2014991008976", "123456");
		String message_2 = Constants.get("message_2_2");
		
		System.out.print(message_1);
		System.out.println(message_2);
		
		SmsHelper.send("13601711251", message_1);
		SmsHelper.send("13601711251", message_2);
	}
}
