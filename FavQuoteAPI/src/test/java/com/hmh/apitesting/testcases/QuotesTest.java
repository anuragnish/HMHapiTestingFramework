package com.hmh.apitesting.testcases;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
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
	public void TC001_HappyPath_listQuotesAPI() {

		Response response = given().contentType(ContentType.JSON).auth().oauth2(config.getProperty("ValidTokenKey"))
				.get(config.getProperty("ListQuotesAPIEndPoint"));
		// ExtentListeners.testReport.get().info(data.toString());
		response.prettyPrint();

		System.out.println(response.statusCode());

		Assert.assertEquals(response.statusCode(), 200);
		// Validate the response like who is the first author
		JsonPath json = response.jsonPath();
		System.out.println(json.get("quotes.author[0]"));

		// Count the total no of fields
		System.out.println(json.getMap("$").size());
		// Count the no of fields in quotes object

		System.out.println(json.getList("quotes").size());

		int actualPageId = json.get("page");
		Assert.assertEquals(actualPageId, 1, "Id not matching");

		// Validating key
		/*
		 * JSONObject jsonObject = new JSONObject(response.asString());
		 * System.out.println(jsonObject.has("last_page"));
		 * Assert.assertTrue(jsonObject.has("last_page")
		 * ,"last_page field is not present");
		 */

		// Optimised Code - Validating Key

		Assert.assertTrue(TestUtil.jsonHasKey(response.asString(), "last_page"), "last_page field is not present");

		// vALIDATING Value of Key
		/*
		 * String ActualId = jsonObject.get("page").toString();
		 * Assert.assertEquals(ActualId, "1", "Id not matching");
		 */
		String ActualId = TestUtil.getJsonKeyValue(response.asString(), "page");
		Assert.assertEquals(ActualId, "1", "Id not matching");

		System.out.println("last_page key Value is" + " " + TestUtil.getJsonKeyValue(response.asString(), "last_page"));
	}

	@Test
	public void TC002_InvalidInputToken_listQuotesAPI() {

		Response response1 = given().contentType(ContentType.JSON).auth().oauth2("InvalidTokenKey")
				.get("ListQuotesAPIEndPoint");
		response1.prettyPrint();

		System.out.println(response1.statusCode());

		Assert.assertEquals(response1.statusCode(), 200, "Status Code Error");

	}

	@Test
	public void TC003_listQuotesByTypeHead() {
		Response response = given().contentType(ContentType.JSON).auth().oauth2(config.getProperty("ValidTokenKey"))
				.get("/typeahead");
		response.prettyPrint();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), 200, "Status Code Error");

	}

	@Test
	public void TC004_Filter_listQuotesByAuthor() {

		Response response = given().contentType(ContentType.JSON).auth().oauth2(config.getProperty("ValidTokenKey"))
				.get(config.getProperty("ListQuotesAPIEndPoint") + "/?filter=Mark+Twain&type=author");

		response.prettyPrint();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), 200, "Status Code Error");

	}

	@Test
	public void TC005_listQuotesOtherStatusCodeValidation() {

		// 404 Validation
		Response response1 = given().contentType(ContentType.JSON).auth().oauth2("InvalidTokenKey")
				.get("ListQuotesAPIEndPoint" + "/a");
		response1.prettyPrint();

		System.out.println(response1.statusCode());

		Assert.assertEquals(response1.statusCode(), 404, "Status Code Error");

	}

	@Test
	public void TC006_listQuotesWithValidParameters() {

		Response response = given().contentType(ContentType.JSON).auth().oauth2(config.getProperty("ValidTokenKey"))
				.get(config.getProperty("ListQuotesAPIEndPoint") + "/?filter=Mark+Twain&type=author");

		response.prettyPrint();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), 200, "Status Code Error");
		JsonPath json = response.jsonPath();
		String ActualId = json.get("quotes.author[0]");
		Assert.assertEquals(ActualId, "Mark Twain", "Author not matching");

	}

	// Negative Scenario
	@Test
	public void TC007_listQuotesWithInValidParameters() {

		Response response = given().contentType(ContentType.JSON).auth().oauth2(config.getProperty("ValidTokenKey"))
				.get(config.getProperty("ListQuotesAPIEndPoint") + "/?fter=Mark+Twain&type=autho");

		response.prettyPrint();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), 200, "Status Code Error");
		JsonPath json = response.jsonPath();
		String ActualId = json.get("quotes.author[7]");
		// It will throw Author not matching exception
		Assert.assertEquals(ActualId, "Mark Twain", "Author not matching");

	}

	@Test
	public void TC008_FavouriteQuoteRequest() {

		// Sesssion ID - not working
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> usercred = new HashMap<String, String>();
		usercred.put("login", "118221455@umail.ucc.ie");
		usercred.put("password", "Lindan@1");

		map.put("user", usercred);

		Response response = given().contentType(ContentType.JSON).log().all().auth()
				.oauth2("ffdbbce2f831b82f1d4edb689d2dca82").body(map).post("ListQuotesAPIEndPoint"+"/session");

		File jsonData = new File("./favouriteQuote.json");
		Response favresponse = given()
				.header("User-Token",
						"T74bNMRfrCIC84fAp4OubCr3hd9KHja5dTwhppYj6rV2nTjDGIKf2F6vRah9uo0TTo1U7TrQhgY5zR3HBsTVBw==")
				.contentType(ContentType.JSON).log().all().auth().oauth2("ffdbbce2f831b82f1d4edb689d2dca82")
				.body(jsonData).put("ListQuotesAPIEndPoint"+"/quotes/:quote_id/fav");

		favresponse.prettyPrint();

		Assert.assertEquals(favresponse.statusCode(), 200, "Status Code Error");

	}

}
