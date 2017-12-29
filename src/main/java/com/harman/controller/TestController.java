package com.harman.controller;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.util.concurrent.BlockingQueue;

//import javax.annotation.PostConstruct;

//import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.harman.utils.ErrorType;
import com.harman.utils.HarmanParser;
//import com.harman.utils.HarmanUtils;
import com.harman.Model.*;

@RestController
@RequestMapping("/Analytics")
public class TestController implements DBkeys {
	//Logger logger = null;

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String read(String device_id) {
		System.out.println("******************************");
		MariaModel mariaModel = MariaModel.getInstance();
		String responseResult = mariaModel.getDeviceInformation(device_id);
		return responseResult;
	}

	@RequestMapping(value = "/mongoDB", method = RequestMethod.POST)
	public @ResponseBody String saveData(@RequestBody String requestBody) {
		//if(logger==null)
		//logger = HarmanUtils.returnLogObject(this);
		JSONObject retunResponse = new JSONObject();
		try {
			//MongoDBInsertion mMongoDBInsertion = MongoDBInsertion.getInstance();
			//mMongoDBInsertion.insertinto(requestBody);
			
			TCPStreamingSparkServer sparkServer = TCPStreamingSparkServer.getInstance();
			Socket activeSocket = sparkServer.getSocket();
			
			//logger.debug("My first log through logging");
			if (activeSocket != null)
			{
				/* Get main queue */
				StartUpInit start = new StartUpInit();
				BlockingQueue<AppMessage> msgQueue = start.getQueue();
				synchronized (msgQueue){
					if (msgQueue.size() < 100)
					{
						AppMessage e = new AppMessage(requestBody+ "\n");
						msgQueue.put(e);
						retunResponse.put("status", "1");
						retunResponse.put("message", "JSON sent to queue");
						System.out.println("JSON sent to queue");
						msgQueue.notify();
					}
					else {
						retunResponse.put("status", "0");
						retunResponse.put("message", "Queue is full");
						System.out.println("Queue is full");
					}	

				}

			}
			else {
				retunResponse.put("status", "0");
				retunResponse.put("message", "No active connection");
				System.out.println("No active connection available");
			}

			//retunResponse.put("status", "1");
			//retunResponse.put("message", "Data record inserted successfully in Mongo DB!");
		} catch (Exception e) {
			retunResponse.put("status", "0");
			retunResponse.put("message", "Invalid json format received for Mongo DB");
			System.out.println("Invalid json failed to parse");
		}
		
		return retunResponse.toString();
	}

	@RequestMapping(value = "/UpdateAnalytics", method = RequestMethod.POST)
	public @ResponseBody String requestCMD(@RequestBody String requestBody) throws IOException {

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
