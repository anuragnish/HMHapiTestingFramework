package com.hmh.apitesting.testcases;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hmh.apitesting.listeners.ExtentListeners;
import com.hmh.apitesting.setup.BaseTest;
import com.hmh.apitesting.utilities.TestUtil;

public class QuotesTest extends BaseTest {

	@Test
	public void listQuotesAPI() {

		Response response = given().contentType(ContentType.JSON).auth().oauth2(config.getProperty("ValidTokenKey"))
				.get(config.getProperty("ListQuotesAPIEndPoint"));
		//ExtentListeners.testReport.get().info(data.toString());
		response.prettyPrint();

		System.out.println(response.statusCode());

		Assert.assertEquals(response.statusCode(), 200);
		//Validate the response like who is the first author
        JsonPath json = response.jsonPath();
		System.out.println(json.get("quotes.author[0]"));
		
		//Count the total no of fields
		System.out.println(json.getMap("$").size());
		//Count the no of fields in quotes object
		
		System.out.println(json.getList("quotes").size());
		
		int actualPageId = json.get("page");
		Assert.assertEquals(actualPageId, 1, "Id not matching");
		
		//Validating key
		/*
		 * JSONObject jsonObject = new JSONObject(response.asString());
		 * System.out.println(jsonObject.has("last_page"));
		 * Assert.assertTrue(jsonObject.has("last_page")
		 * ,"last_page field is not present");
		 */
		
		//Optimised Code - Validating Key
		
		Assert.assertTrue(TestUtil.jsonHasKey(response.asString(), "last_page"),"last_page field is not present");
		
		//vALIDATING Value of Key
		/*
		 * String ActualId = jsonObject.get("page").toString();
		 * Assert.assertEquals(ActualId, "1", "Id not matching");
		 */
		String ActualId = TestUtil.getJsonKeyValue(response.asString(), "page");
		Assert.assertEquals(ActualId, "1", "Id not matching");
		
		System.out.println("last_page key Value is"+" "+TestUtil.getJsonKeyValue(response.asString(), "last_page"));
	}

	@Test
	public void InvalidTokenlistQuotesAPI() {

		Response response1 = given().contentType(ContentType.JSON).auth().oauth2("InvalidTokenKey")
				.get("ListQuotesAPIEndPoint");
		response1.prettyPrint();

		System.out.println(response1.statusCode());

		Assert.assertEquals(response1.statusCode(), 200, "Status Code Error");

	}
    
	public void listQuotesWithIdAPI() {

		Response response = given().contentType(ContentType.JSON).auth().oauth2("ValidTokenKey")
				.get("ListQuotesAPIEndPoint");
		response.prettyPrint();

		System.out.println(response.statusCode());

		Assert.assertEquals(response.statusCode(), 200);

	}

}
