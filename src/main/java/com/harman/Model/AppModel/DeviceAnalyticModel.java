package com.harman.Model.AppModel;

import java.util.LinkedHashMap;

public abstract class DeviceAnalyticModel {
	private String macaddress;
	private LinkedHashMap<String, Object> mDeviceAnaModelList = new LinkedHashMap<String, Object>();

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

	public abstract long getDeviceType();

	public abstract String[] getKeys();

}
