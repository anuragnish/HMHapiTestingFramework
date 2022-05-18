package com.hmh.apitesting.testcases;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ListQuotes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 
		Response response = given().contentType(ContentType.JSON).auth().oauth2("ffdbbce2f831b82f1d4edb689d2dca82").get("https://favqs.com/api/quotes");
		
		//response.prettyPrint();
		
		
		HashMap<String,String> map = new  HashMap<String,String>();
		
		map.put("", "");
		map.put("", "");
		
		
		String jsonResponse = response.asString();
		
		//System.out.println(jsonResponse);

		//Status Code
		
		System.out.println("Response Code -->" +response.statusCode());
		
	}

}
