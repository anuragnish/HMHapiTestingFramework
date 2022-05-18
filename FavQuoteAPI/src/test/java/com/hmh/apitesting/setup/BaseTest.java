package com.hmh.apitesting.setup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;

public class BaseTest {
	
	public Properties config = new Properties();
	private FileInputStream fis;

	@BeforeSuite
	public void setUp() throws IOException {

		fis = new FileInputStream("C:\\Users\\USER\\eclipse-workspace\\FavQuoteAPI\\src\\test\\resources\\properties\\config.properties");
		config.load(fis);
		RestAssured.baseURI = config.getProperty("baseURI");
		RestAssured.basePath = config.getProperty("basePath");

	}

	@AfterSuite
	public void tearDown() {
	}
}
