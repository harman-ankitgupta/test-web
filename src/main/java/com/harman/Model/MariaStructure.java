package com.harman.Model;

import java.sql.Connection;

public interface MariaStructure {

	public String getDeviceInformation(String macID);

	public String insertDeviceInformation(String device_id, String device_model, String operations_name,
			String operations_params, String fw_version, String sw_version, String connection);

	public int insertDeviceModel(HarmanDeviceModel mHarmanDeviceModel, Connection conn);
	public int insertDeviceAnalytics(DeviceAnalyticsModel mDeviceAnalyticsModel);
	public int insertAppAnalytics(AppAnalyticsModel mAppAnalyticsModel);
}
