package com.hmh.apitesting.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		    Properties config = new Properties();
			FileInputStream fis = new FileInputStream("C:\\Users\\USER\\eclipse-workspace\\FavQuoteAPI\\src\\test\\resources\\properties\\config.properties");
			config.load(fis);
		
		}
	}


