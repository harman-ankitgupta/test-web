package com.harman.controller;

import java.sql.Connection;

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
import com.harman.utils.HarmanParser;

@RestController
@RequestMapping("/cloudApp")
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

	/*
	 * @RequestMapping(value = "/reqestcmd", method = RequestMethod.POST)
	 * public @ResponseBody String requestCMD(@RequestBody String requestBody) {
	 * 
	 * JSONObject response = new JSONObject(); try { JSONObject jsonObject = new
	 * JSONObject(requestBody); String cmd = jsonObject.getString("cmd"); int
	 * seq = jsonObject.getInt("seq"); JSONObject
	 * syncDeviceReq=jsonObject.getJSONObject("syncDeviceReq"); JSONObject
	 * harmanDevice = syncDeviceReq.getJSONObject("harmanDevice"); String
	 * macAddress = harmanDevice.getString("macAddress"); int productId =
	 * harmanDevice.getInt("productId");
	 * 
	 * String colorId = jsonObject.getString("colorId"); String productName =
	 * jsonObject.getString("productName"); String colorName =
	 * jsonObject.getString("colorName");
	 * 
	 * create response code
	 * 
	 * JSONObject deviceSession = new JSONObject();
	 * deviceSession.put("deviceId", macAddress); deviceSession.put("skey",
	 * HarmanUtils.generateSessionID(macAddress, productId));
	 * 
	 * JSONObject syncDeviceRes = new JSONObject();
	 * syncDeviceRes.put("deviceSession", deviceSession);
	 * 
	 * response.put("cmd", cmd); response.put("seq", seq);
	 * response.put("errorMsg", "Success"); response.put("ret", 0);
	 * response.put("syncDeviceRes", syncDeviceRes);
	 * 
	 * } catch (Exception e) { response.put("cmd", "SyncDeviceCMD");
	 * response.put("seq", 100); response.put("errorMsg", "Fail");
	 * response.put("ret", 0); response.put("syncDeviceRes", "");
	 * System.out.println("fail to parse"); } return response.toString(); }
	 */
	@RequestMapping(value = "/reqestcmd", method = RequestMethod.POST)
	public @ResponseBody String requestCMD(@RequestBody String requestBody) {

		JSONObject response = new JSONObject();
		try {
			JSONObject jsonObject = new JSONObject(requestBody);
			MariaModel mariaModel = MariaModel.getInstance();
			Connection connection = mariaModel.openConnection();
			HarmanParser harmanParser = new HarmanParser();
			HarmanDeviceModel deviceModel = harmanParser.getParseHarmanDevice(jsonObject.getJSONObject("harmanDevice"));
			int success = mariaModel.insertDeviceModel(deviceModel, connection);
			if (success != 0)
				System.out.println("inset into harmandevice");
			DeviceAnalyticsModel deviceAnalyticsModel = harmanParser.getParseDeviceAnalyticsModel(
					jsonObject.getJSONObject("DeviceAnalytics"), deviceModel.getMacAddress());
			success = mariaModel.insertDeviceAnalytics(deviceAnalyticsModel, connection);
			if (success != 0)
				System.out.println("inset into DeviceAnalytics");
			AppAnalyticsModel appAnalyticsModel = harmanParser
					.getParseAppAnalyticsModel(jsonObject.getJSONObject("AppAnalytics"), deviceModel.getMacAddress());
			success = mariaModel.insertAppAnalytics(appAnalyticsModel, connection);
			if (success != 0)
				System.out.println("inset into AppAnalytics");
			response.put("cmd", "UpdateSmartAudioAnalyticsRes");
			response.put("Status", success);
		} catch (Exception e) {
			response.put("cmd", "UpdateSmartAudioAnalyticsRes");
			response.put("Status", 0);
			System.out.println("fail to parse");
		} finally {
			MariaModel mariaModel = MariaModel.getInstance();
			mariaModel.closeConnection();
		}
		return response.toString();
	}

}
