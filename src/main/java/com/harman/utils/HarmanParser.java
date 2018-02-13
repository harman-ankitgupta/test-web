package com.harman.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.harman.Model.HarmanDeviceModel;
import com.harman.Model.PRODUCT_TYPE;
import com.harman.Model.AppModel.AppAnalyticModel;
import com.harman.Model.AppModel.BoomboxAppAnalyticsModel;
import com.harman.Model.AppModel.BoomboxDeviceAnalyticsModel;
import com.harman.Model.AppModel.DeviceAnalyticModel;
import com.harman.Model.AppModel.JBLExtremeCLAnalyticsModel;
import com.harman.Model.AppModel.JBLXtremeCLDeviceAnalyticsModel;

public class HarmanParser {

	public HarmanDeviceModel getParseHarmanDevice(JSONObject mHarmanDevice) throws JSONException {

		HarmanDeviceModel harmanModel = new HarmanDeviceModel();
		harmanModel.setAppVersion(mHarmanDevice.getString("AppVersion"));
		harmanModel.setFirmwareVersion(mHarmanDevice.getString("FirmwareVersion"));
		harmanModel.setColorName(mHarmanDevice.getString("colorName"));
		harmanModel.setProductName(mHarmanDevice.getString("productName"));
		harmanModel.setColorId(mHarmanDevice.getInt("colorId"));
		harmanModel.setProductId(mHarmanDevice.getInt("productId"));
		harmanModel.setMacAddress(mHarmanDevice.getString("macAddress"));

		return harmanModel;
	}

//	public DeviceAnalyticsModel getParseDeviceAnalyticsModel(JSONObject mAanaytics, String macaddress)
//			throws JSONException {
//
//		DeviceAnalyticsModel deviceAnalyticsModel = new DeviceAnalyticsModel();
//		String[] keys = deviceAnalyticsModel.getKeys();
//		for (String key : keys) {
//			if (!mAanaytics.isNull(key)) {
//				deviceAnalyticsModel.getmDeviceAnaModelList().put(key, mAanaytics.getInt(key));
//			}
//		}
//		deviceAnalyticsModel.setMacaddress(macaddress);
//		/*
//		 * deviceAnalyticsModel.setBroadcaster(mAanaytics.getInt("Broadcaster"))
//		 * ; deviceAnalyticsModel.setReceiver(mAanaytics.getInt("Receiver"));
//		 * deviceAnalyticsModel.setCriticalTemperatureShutDown(mAanaytics.getInt
//		 * ("CriticalTemperatureShutDown"));
//		 * deviceAnalyticsModel.setPowerBankUsage(mAanaytics.getInt(
//		 * "PowerBankUsage"));
//		 * deviceAnalyticsModel.setPowerOnOffCount(mAanaytics.getInt(
//		 * "PowerOnOffCount"));
//		 * 
//		 * JSONObject mEQSettings = mAanaytics.getJSONObject("EQSettings");
//		 * deviceAnalyticsModel.setEQSettings_Indoor(mEQSettings.getInt("Indoor"
//		 * )); deviceAnalyticsModel.setEQSettings_Outdoor(mEQSettings.getInt(
//		 * "Outdoor")); deviceAnalyticsModel.setMacaddress(macaddress);
//		 */
//		return deviceAnalyticsModel;
//
//	}
//	
	
	public DeviceAnalyticModel parseDeviceAnalyticsModel(JSONObject mAanaytics, String macaddress, long productId)
			throws JSONException {

		DeviceAnalyticModel deviceAnalyticsModel =null;
		if(productId == PRODUCT_TYPE.BOOMBOX) {
			deviceAnalyticsModel = new BoomboxDeviceAnalyticsModel();
		} else if(productId == PRODUCT_TYPE.JBL_XTREME_CL) {
			deviceAnalyticsModel = new JBLXtremeCLDeviceAnalyticsModel();
		}
		
		String[] keys = deviceAnalyticsModel.getKeys();
		for (String key : keys) {
			if (!mAanaytics.isNull(key)) {
				if(mAanaytics.get(key) instanceof Integer) 
					deviceAnalyticsModel.getmDeviceAnaModelList().put(key, mAanaytics.getInt(key));
				else if(mAanaytics.get(key) instanceof String) 
					deviceAnalyticsModel.getmDeviceAnaModelList().put(key, mAanaytics.getString(key));
				else 
					deviceAnalyticsModel.getmDeviceAnaModelList().put(key, mAanaytics.get(key));
//				deviceAnalyticsModel.getmDeviceAnaModelList().put(key, mAanaytics.getInt(key));
			}
		}
		deviceAnalyticsModel.setMacaddress(macaddress);
		return deviceAnalyticsModel;

	}
	
	public AppAnalyticModel parseAppAnalyticsModel(JSONObject mAppAnalytics, String macaddress, long productId) throws JSONException  {
		AppAnalyticModel model = null;
		if (productId == PRODUCT_TYPE.BOOMBOX) {// Boombox
			model = new BoomboxAppAnalyticsModel();
		} else if (productId == PRODUCT_TYPE.JBL_XTREME_CL) {// JBL Xtreme2 CL
			model = new JBLExtremeCLAnalyticsModel();
		}
		String[] keys = model.getKeys();
		for (String key : keys) {
			if (!mAppAnalytics.isNull(key)) {
				if(mAppAnalytics.get(key) instanceof Integer) 
					model.getmDeviceAnaModelList().put(key, mAppAnalytics.getInt(key));
				else if(mAppAnalytics.get(key) instanceof String) 
					model.getmDeviceAnaModelList().put(key, "'"+mAppAnalytics.getString(key)+"'");
				else 
					model.getmDeviceAnaModelList().put(key, mAppAnalytics.get(key));
				

			}
		}

		if (!mAppAnalytics.isNull("SpeakerMode")) {

			JSONObject jsonObject = mAppAnalytics.getJSONObject("SpeakerMode");

			if (!jsonObject.isNull("Stereo")) {
				model.getmDeviceAnaModelList().put("SpeakerMode_Stereo", jsonObject.getInt("Stereo"));
			}
			if (!jsonObject.isNull("Party")) {
				model.getmDeviceAnaModelList().put("SpeakerMode_Party", jsonObject.getInt("Party"));
			}
			if (!jsonObject.isNull("Single")) {
				model.getmDeviceAnaModelList().put("SpeakerMode_Single", jsonObject.getInt("Single"));
			}

		}
		model.setMacaddress(macaddress);
		
		return model;
	}

//	public AppAnalyticsModel getParseAppAnalyticsModel(JSONObject mAppAnalytics, String macaddress)
//			throws JSONException {
//
//		AppAnalyticsModel analyticsModel = new AppAnalyticsModel();
//
//		String[] keys = analyticsModel.getKeys();
//		for (String key : keys) {
//			if (!mAppAnalytics.isNull(key))
//				analyticsModel.getmDeviceAnaModelList().put(key, mAppAnalytics.get(key));
//		}
//
//		if (!mAppAnalytics.isNull("SpeakerMode")) {
//
//			JSONObject jsonObject = mAppAnalytics.getJSONObject("SpeakerMode");
//
//			if (!jsonObject.isNull("Stereo")) {
//				analyticsModel.getmDeviceAnaModelList().put("SpeakerMode_Stereo", jsonObject.getInt("Stereo"));
//			}
//			if (!jsonObject.isNull("Party")) {
//				analyticsModel.getmDeviceAnaModelList().put("SpeakerMode_Party", jsonObject.getInt("Party"));
//			}
//			if (!jsonObject.isNull("Single")) {
//				analyticsModel.getmDeviceAnaModelList().put("SpeakerMode_Single", jsonObject.getInt("Single"));
//			}
//
//		}
//		analyticsModel.setMacaddress(macaddress);
//		/*
//		 * JSONObject jSpeakerMode = mAppAnalytics.getJSONObject("SpeakerMode");
//		 * analyticsModel.setSpeakerMode_Stereo(jSpeakerMode.getInt("Stereo"));
//		 * analyticsModel.setSpeakerMode_Party(jSpeakerMode.getInt("Party"));
//		 * analyticsModel.setSpeakerMode_Single(jSpeakerMode.getInt("Single"));
//		 * 
//		 * JSONObject jAppSettings = mAppAnalytics.getJSONObject("AppSettings");
//		 * 
//		 * JSONObject jAppToneToggle =
//		 * jAppSettings.getJSONObject("AppToneToggle");
//		 * analyticsModel.setAppSettings_AppToneToggle_On(jAppToneToggle.getInt(
//		 * "On"));
//		 * analyticsModel.setAppSettings_AppToneToggle_Off(jAppToneToggle.getInt
//		 * ("Off"));
//		 * 
//		 * JSONObject jAppMFBMode = jAppSettings.getJSONObject("AppMFBMode");
//		 * analyticsModel.setAppSettings_AppMFBMode_VoiceAssist(jAppMFBMode.
//		 * getInt("VoiceAssist"));
//		 * analyticsModel.setAppSettings_AppMFBMode_PlayPause(jAppMFBMode.getInt
//		 * ("PlayPause"));
//		 * 
//		 * JSONObject jAppHFPToggle =
//		 * jAppSettings.getJSONObject("AppHFPToggle");
//		 * analyticsModel.setAppSettings_AppHFPToggle_On(jAppHFPToggle.getInt(
//		 * "On"));
//		 * analyticsModel.setAppSettings_AppHFPToggle_Off(jAppHFPToggle.getInt(
//		 * "Off"));
//		 * 
//		 * JSONObject jAppEQMode = jAppSettings.getJSONObject("AppEQMode");
//		 * analyticsModel.setAppSettings_AppEQMode_Indoor(jAppEQMode.getInt(
//		 * "Indoor"));
//		 * analyticsModel.setAppSettings_AppEQMode_Outdoor(jAppEQMode.getInt(
//		 * "Outdoor"));
//		 * 
//		 * JSONObject jAppDevMode = jAppSettings.getJSONObject("AppDevMode");
//		 * analyticsModel.setAppSettings_AppDevMode_Indoor(jAppDevMode.getInt(
//		 * "Indoor"));
//		 * analyticsModel.setAppSettings_AppDevMode_Outdoor(jAppDevMode.getInt(
//		 * "Outdoor"));
//		 * 
//		 * JSONObject jOTAStatus = mAppAnalytics.getJSONObject("OTAStatus");
//		 * analyticsModel.setAppSettings_OTAStatus_Success(jOTAStatus.getInt(
//		 * "Success"));
//		 * analyticsModel.setAppSettings_OTAStatus_Failure(jOTAStatus.getInt(
//		 * "Failure"));
//		 * analyticsModel.setAppSettings_OTAStatus_Duration(jOTAStatus.getInt(
//		 * "Duration"));
//		 */
//
//		analyticsModel.setMacaddress(macaddress);
//
//		return analyticsModel;
//
//	}
}
