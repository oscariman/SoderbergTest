package com.test.soderberg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {
	
	private static final String CONFIG = "config.properties";
	
	/**
	 * Method to get properties from a config property file
	 * @param property the name of the property
	 * @return the property value
	 */
	public static String getProperty(String property) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(CONFIG)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String) prop.get(property);
	}
	
	
}
