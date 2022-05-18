package com.hmh.apitesting.testcases;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.HashMap;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class FavQuotes {

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			
			String sessionId = get("https://favqs.com/").sessionId();
			System.out.println(sessionId);

			
			  HashMap<String,Object> map = new HashMap<String,Object>(); 
			
			HashMap<String,String> usercred = new HashMap<String,String>(); 
			usercred.put("login","anuragnish");
			usercred.put("password","Msdhoni@1");
			
			map.put("user", usercred);
			
			
			
			 // String bodyString ="{\"login\":\"anuragnish@gmail.com\",\"password\":\"Msdhoni@1\"}" ; 
			  //Response response = given().contentType(ContentType.JSON).log().all().auth().oauth2( "ffdbbce2f831b82f1d4edb689d2dca82").body(new File("./users.json")).post("https://favqs.com/api/users");
			  
			  Response response = given().contentType(ContentType.JSON).log().all().auth().oauth2( "ffdbbce2f831b82f1d4edb689d2dca82").body(map).post(
					  "https://favqs.com/api/session");
			  
			  response.prettyPrint();
			 
			//Status Code
			//System.out.println("Response Code -->" +response.statusCode());
			
		}

	}


