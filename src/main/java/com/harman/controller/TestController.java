package com.harman.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.harman.Model.DBkeys;
import com.harman.Model.HarmanDeviceModel;
import com.harman.Model.MariaModel;
import com.harman.Model.AppModel.AppAnalyticModel;
import com.harman.Model.AppModel.DeviceAnalyticModel;
import com.harman.utils.ErrorType;
import com.harman.utils.HarmanParser;
import com.harman.utils.HarmanUtils;


@RestController
@RequestMapping("/Analytics")
public class TestController implements DBkeys {
	Logger logger = HarmanUtils.returnLogObject(this);
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String read(String device_id) {
		MariaModel mariaModel = MariaModel.getInstance();
		String responseResult = mariaModel.getDeviceInformation(device_id);
		return responseResult;
	}
	
	public static String getClientIpAddress(HttpServletRequest request) {
	    String xForwardedForHeader = request.getHeader("X-Forwarded-For");
	    if (xForwardedForHeader == null) {
	        return request.getRemoteAddr();
	    } else {
	        // As of https://en.wikipedia.org/wiki/X-Forwarded-For
	        // The general format of the field is: X-Forwarded-For: client, proxy1, proxy2 ...
	        // we only want the client
	        return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
	    }
	}

	@RequestMapping(value = "/mongoDB", method = RequestMethod.POST)
	public @ResponseBody String saveData(@RequestBody String requestBody){ //, HttpServletRequest context) {
		//String ipAddress = getClientIpAddress(context);
		//logger.info("Client Ip is:"  + ipAddress);
		//CountryCodeTable country_code = CountryCodeTable.getInstance();
		//long numericIp = country_code.getNumericValue(ipAddress);
		//Connection con = null;
		
		//String query = "select from countrycodetable where startRange <= "+numericIp+" and endRange >= numericIp";
		
		//MariaModel mariaModel = MariaModel.getInstance();
        //con = mariaModel.openConnection();
		//String code = mariaModel.getCountryCode(con, query);
		//try {
			//con.close();
		//} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		//}
		
		JSONObject retunResponse = new JSONObject();
		JSONObject jsonReq = new JSONObject(requestBody);
		//jsonReq.put("CountryCode", code);
		requestBody = jsonReq.toString();
		try {
						
			MainServer mainServer = MainServer.getInstance();
			int idx = mainServer.getActiveSubServer();
			if (idx >= 0)
			{				
				StartUpInit start = StartUpInit.getStartUIntance();
				BlockingQueue<AppMessage> msgQueue = start.getQueue();
				synchronized (msgQueue){
					if (msgQueue.size() >= 75) {
						
						logger.warn("Incoming request has reached Queue-threshhold ");
					}
					if (msgQueue.size() < 100) {
						AppMessage e = new AppMessage(requestBody+ "\n");
						msgQueue.put(e);
						retunResponse.put("status", "1");
						retunResponse.put("message", "JSON sent to queue");
						System.out.println("JSON sent to queue");
						logger.info("Json sent to queue");
						msgQueue.notifyAll();
					} else {
						retunResponse.put("status", "0");
						retunResponse.put("message", "Queue is full");
						logger.fatal("Queue is full");
					}	
				}
			}else {
				retunResponse.put("status", "0");
				retunResponse.put("message", "No active connection available with TCP server");	
				logger.info("No active connection available with TCP server");
			}
		} catch (Exception e) {
			logger.error("Json Eroor, Message:"+ e.getMessage());
			retunResponse.put("status", "0");
			retunResponse.put("message", "Invalid json format received to webservice");
			logger.error("Invalid json format received on webservice");
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
				DeviceAnalyticModel deviceAnalyticsModel = harmanParser.parseDeviceAnalyticsModel(
						jsonObject.getJSONObject(DeviceAnalytics), deviceModel.getMacAddress(), deviceModel.getProductId());
				errorType = mariaModel.insertDeviceAnalytics(deviceAnalyticsModel, connection);
				System.out.println(errorType.name());
			} catch (JSONException e) {
				errorType = ErrorType.INVALID_JSON;
			}

			try {
				AppAnalyticModel appAnalyticsModel = harmanParser
						.parseAppAnalyticsModel(jsonObject.getJSONObject(AppAnalytics), deviceModel.getMacAddress(), deviceModel.getProductId());
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
