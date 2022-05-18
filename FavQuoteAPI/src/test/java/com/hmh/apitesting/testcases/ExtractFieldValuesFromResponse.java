package com.hmh.apitesting.testcases;

import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ExtractFieldValuesFromResponse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Response response = given().contentType(ContentType.JSON).auth().oauth2("ValidTokenKey")
				.get("ListQuotesAPIEndPoint");
		response.prettyPrint();

		System.out.println(response.statusCode());

		Assert.assertEquals(response.statusCode(), 200);
		
JsonPath json = response.jsonPath();
		
		System.out.println(json.get("id"));

	}

}
