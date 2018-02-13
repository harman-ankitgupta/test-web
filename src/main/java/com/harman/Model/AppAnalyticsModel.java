package com.harman.Model;

import java.util.LinkedHashMap;

public class AppAnalyticsModel {
	private LinkedHashMap<String, Object> mDeviceAnaModelList = new LinkedHashMap<String, Object>();
	private String[] keys = { "SpeakerMode_Stereo", "SpeakerMode_Party", "SpeakerMode_Single", "AppToneToggle",
			"AppMFBMode", "AppHFPToggle", "AppEQMode" };
	private String macaddress;
	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public LinkedHashMap<String, Object> getmDeviceAnaModelList() {
		return mDeviceAnaModelList;
	}

	public void setmDeviceAnaModelList(LinkedHashMap<String, Object> mDeviceAnaModelList) {
		this.mDeviceAnaModelList = mDeviceAnaModelList;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}
}
