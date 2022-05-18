package com.hmh.apitesting.utilities;

import org.json.JSONObject;

import com.hmh.apitesting.listeners.ExtentListeners;

public class TestUtil {
	
	public static boolean jsonHasKey(String json, String key) {
		JSONObject jsonObject = new JSONObject(json);
		ExtentListeners.testReport.get().info("Validating the Presence of Key");
		return jsonObject.has(key);
	}
	
	public static String getJsonKeyValue(String json, String key) {
		JSONObject jsonObject = new JSONObject(json);
		ExtentListeners.testReport.get().info("Validating value of Key");
		return jsonObject.get(key).toString();
		
	}

}
