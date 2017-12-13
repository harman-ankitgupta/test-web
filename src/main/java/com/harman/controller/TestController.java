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
import com.harman.Model.DeviceAnalyticsModel;
import com.harman.Model.HarmanDeviceModel;
import com.harman.Model.MariaModel;
import com.harman.utils.ErrorType;
import com.harman.utils.HarmanParser;

@RestController
@RequestMapping("/Analytics")
public class TestController {

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
			JSONObject jsonObject = new JSONObject(requestBody);
			String device_id = jsonObject.getString("device_id");
			String device_model = jsonObject.getString("device_model");
			String operations_name = jsonObject.getString("operations_name");
			String operations_params = jsonObject.getString("operations_params");
			String fw_version = jsonObject.getString("fw_version");
			String sw_version = jsonObject.getString("sw_version");
			String connection = jsonObject.getString("connection");
			MariaModel mariaModel = new MariaModel();
			String response = mariaModel.insertDeviceInformation(device_id, device_model, operations_name,
					operations_params, fw_version, sw_version, connection);
			System.out.println(response);
			retunResponse.put("status", response);
			if (response.equals("1"))
				retunResponse.put("message", "Data record inserted successfully!");
			else
				retunResponse.put("message", "Data record insertion failed!");
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
				deviceModel = harmanParser.getParseHarmanDevice(jsonObject.getJSONObject("harmanDevice"));
				errorType = mariaModel.insertDeviceModel(deviceModel, connection);
				System.out.println(errorType.name());
			} catch (JSONException e) {
				errorType = ErrorType.INVALID_JSON;
			}

			try {
				DeviceAnalyticsModel deviceAnalyticsModel = harmanParser.getParseDeviceAnalyticsModel(
						jsonObject.getJSONObject("DeviceAnalytics"), deviceModel.getMacAddress());
				errorType = mariaModel.insertDeviceAnalytics(deviceAnalyticsModel, connection);
				System.out.println(errorType.name());
			} catch (JSONException e) {
				errorType = ErrorType.INVALID_JSON;
			}

			try {
				AppAnalyticsModel appAnalyticsModel = harmanParser.getParseAppAnalyticsModel(
						jsonObject.getJSONObject("AppAnalytics"), deviceModel.getMacAddress());
				errorType = mariaModel.insertAppAnalytics(appAnalyticsModel, connection);
				System.out.println(errorType.name());
			} catch (JSONException e) {
				errorType = ErrorType.INVALID_JSON;
			}

			response.put("cmd", "UpdateSmartAudioAnalyticsRes");
			switch (errorType) {
			case NO_ERROR:
				response.put("Status", 1);
				break;

			default:
				response.put("Status", 0);
				break;
			}

		} catch (Exception e) {
			response.put("cmd", "UpdateSmartAudioAnalyticsRes");
			response.put("Status", 0);
			System.out.println("fail to parse");
		} finally {
			MariaModel mariaModel = MariaModel.getInstance();
			mariaModel.closeConnection();
		}
		System.out.println(errorType.name());
		return response.toString();
	}

}
