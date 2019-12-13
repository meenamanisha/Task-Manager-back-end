package com.infy.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties; 

public class PropertiesReader {
	public static final String PROPERTIES_FILE = "application.properties";
	public static Properties properties = new Properties();
	

	static  {
		try 
		{
			InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);		
			if (inputStream != null) 
			{
				properties.load(inputStream);
			} 
		}
		catch (IOException e) {			
		}
		
	}	
}
