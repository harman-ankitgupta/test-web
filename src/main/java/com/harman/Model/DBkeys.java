package com.harman.Model;

public interface DBkeys {

	public String harmanDevice = "harmanDevice";
	public String macAddress = "macAddress", productId = "productId", colorId = "colorId", productName = "productName",
			colorName = "colorName", FirmwareVersion = "FirmwareVersion", AppVersion = "AppVersion";

	public String DeviceAnalytics = "DeviceAnalytics";
	public String Broadcaster = "Broadcaster", Receiver = "Receiver",
			CriticalTemperatureShutDown = "CriticalTemperatureShutDown", PowerOnOffCount = "PowerOnOffCount",
			EQSettings_Indoor = "EQSettings_Indoor", EQSettings_Outdoor = "EQSettings_Outdoor",
			PowerBankUsage = "PowerBankUsage";

	public String AppAnalytics = "AppAnalytics";
	public String SpeakerMode_Stereo = "SpeakerMode_Stereo", SpeakerMode_Party = "SpeakerMode_Party",
			SpeakerMode_Single = "SpeakerMode_Single";
	public String AppSettings_AppToneToggle_On = "AppSettings_AppToneToggle_On",
			AppSettings_AppToneToggle_Off = "AppSettings_AppToneToggle_Off";
	public String AppSettings_AppMFBMode_VoiceAssist = "AppSettings_AppMFBMode_VoiceAssist",
			AppSettings_AppMFBMode_PlayPause = "AppSettings_AppMFBMode_PlayPause";

	public String AppSettings_AppHFPToggle_On = "AppSettings_AppHFPToggle_On",
			AppSettings_AppHFPToggle_Off = "AppSettings_AppHFPToggle_Off";

	public String AppSettings_AppEQMode_Indoor = "AppSettings_AppEQMode_Indoor",
			AppSettings_AppEQMode_Outdoor = "AppSettings_AppEQMode_Outdoor";

	public String AppSettings_AppDevMode_Indoor = "AppSettings_AppDevMode_Indoor",
			AppSettings_AppDevMode_Outdoor = "AppSettings_AppDevMode_Outdoor";

	public String OTAStatus_Success = "", OTAStatus_Failure = "OTAStatus_Failure",
			OTAStatus_Duration = "OTAStatus_Duration";

}
