package com.harman.controller;

import java.sql.Connection;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.harman.Model.AppAnalyticsModel;
import com.harman.Model.DBkeys;
import com.harman.Model.DeviceAnalyticsModel;
import com.harman.Model.HarmanDeviceModel;
import com.harman.Model.MariaModel;
import com.harman.Model.MongoDBInsertion;
import com.harman.utils.ErrorType;
import com.harman.utils.HarmanParser;

@RestController
@RequestMapping("/Analytics")
public class TestController implements DBkeys {

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String read(String device_id) {
		System.out.println("******************************");
		MariaModel mariaModel = MariaModel.getInstance();
		String responseResult = mariaModel.getDeviceInformation(device_id);
		return responseResult;
	}

	@RequestMapping(value = "/saveData", method = RequestMethod.POST)
	public @ResponseBody String saveData(@RequestBody String requestBody) {
		JSONObject retunResponse = new JSONObject();
		try {
			MongoDBInsertion mMongoDBInsertion = MongoDBInsertion.getInstance();
			mMongoDBInsertion.insertinto(requestBody);
			retunResponse.put("status", "1");
			retunResponse.put("message", "Data record inserted successfully!");
		} catch (Exception e) {
			retunResponse.put("status", "0");
			retunResponse.put("message", "Invalid json format received.");
			System.out.println("fail to parse");
		}
		return retunResponse.toString();
	}

	@RequestMapping(value = "/UpdateAnalytics", method = RequestMethod.POST)
	public @ResponseBody String requestCMD(@RequestBody String requestBody) {
		ErrorType errorType = ErrorType.NO_ERROR;
		JSONObject response = new JSONObject();
		try {
			JSONObject jsonObject = new JSONObject(requestBody);
			MariaModel mariaModel = MariaModel.getInstance();
			Connection connection = mariaModel.openConnection();
			HarmanParser harmanParser = new HarmanParser();
			HarmanDeviceModel deviceModel = null;
			try {
				deviceModel = harmanParser.getParseHarmanDevice(jsonObject.getJSONObject(harmanDevice));
				errorType = mariaModel.insertDeviceModel(deviceModel, connection);
				System.out.println(errorType.name());
			} catch (JSONException e) {
				errorType = ErrorType.INVALID_JSON;
			}

			try {
				DeviceAnalyticsModel deviceAnalyticsModel = harmanParser.getParseDeviceAnalyticsModel(
						jsonObject.getJSONObject(DeviceAnalytics), deviceModel.getMacAddress());
				errorType = mariaModel.insertDeviceAnalytics(deviceAnalyticsModel, connection);
				System.out.println(errorType.name());
			} catch (JSONException e) {
				errorType = ErrorType.INVALID_JSON;
			}

			try {
				AppAnalyticsModel appAnalyticsModel = harmanParser
						.getParseAppAnalyticsModel(jsonObject.getJSONObject(AppAnalytics), deviceModel.getMacAddress());
				errorType = mariaModel.insertAppAnalytics(appAnalyticsModel, connection);
				System.out.println(errorType.name());
			} catch (JSONException e) {
				errorType = ErrorType.INVALID_JSON;
			}

			switch (errorType) {
			case NO_ERROR:
				response.put("Status", 1);
				break;

			default:
				response.put("Status", 0);
				break;
			}
			response.put("cmd", "UpdateSmartAudioAnalyticsRes");
		} catch (Exception e) {
			response.put("Status", 0);
			response.put("cmd", "UpdateSmartAudioAnalyticsRes");
			System.out.println("fail to parse");
		} finally {
			MariaModel mariaModel = MariaModel.getInstance();
			mariaModel.closeConnection();
		}
		System.out.println(errorType.name());
		return response.toString();
	}

}
