package com.irengine.connector;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class Constants {

	public static String KEY = "12345678";
	private static Properties props;
	private static final Logger logger = LoggerFactory.getLogger(Constants.class);

	
	static {
		Resource resource = new ClassPathResource("/connector.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			logger.error("fail to load connector.properties.");
		}
	}
	
	public static String get(String key) {
		return props.getProperty(key);
	}
}
