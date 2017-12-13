package com.harman.Model;

public class AppAnalyticsModel {

	int SpeakerMode_Stereo, SpeakerMode_Party, SpeakerMode_Single, AppSettings_AppToneToggle_On,
			AppSettings_AppToneToggle_Off, AppSettings_AppMFBMode_VoiceAssist, AppSettings_AppMFBMode_PlayPause,
			AppSettings_AppHFPToggle_On, AppSettings_AppHFPToggle_Off, AppSettings_AppEQMode_Indoor,
			AppSettings_AppEQMode_Outdoor, AppSettings_AppDevMode_Indoor, AppSettings_AppDevMode_Outdoor,
			OTAStatus_Success, OTAStatus_Failure, OTAStatus_Duration;
	private String macaddress;
	public int getSpeakerMode_Stereo() {
		return SpeakerMode_Stereo;
	}

	public void setSpeakerMode_Stereo(int speakerMode_Stereo) {
		SpeakerMode_Stereo = speakerMode_Stereo;
	}

	public int getSpeakerMode_Party() {
		return SpeakerMode_Party;
	}

	public void setSpeakerMode_Party(int speakerMode_Party) {
		SpeakerMode_Party = speakerMode_Party;
	}

	public int getSpeakerMode_Single() {
		return SpeakerMode_Single;
	}

	public void setSpeakerMode_Single(int speakerMode_Single) {
		SpeakerMode_Single = speakerMode_Single;
	}

	public int getAppSettings_AppToneToggle_On() {
		return AppSettings_AppToneToggle_On;
	}

	public void setAppSettings_AppToneToggle_On(int appSettings_AppToneToggle_On) {
		AppSettings_AppToneToggle_On = appSettings_AppToneToggle_On;
	}

	public int getAppSettings_AppToneToggle_Off() {
		return AppSettings_AppToneToggle_Off;
	}

	public void setAppSettings_AppToneToggle_Off(int appSettings_AppToneToggle_Off) {
		AppSettings_AppToneToggle_Off = appSettings_AppToneToggle_Off;
	}

	public int getAppSettings_AppMFBMode_VoiceAssist() {
		return AppSettings_AppMFBMode_VoiceAssist;
	}

	public void setAppSettings_AppMFBMode_VoiceAssist(int appSettings_AppMFBMode_VoiceAssist) {
		AppSettings_AppMFBMode_VoiceAssist = appSettings_AppMFBMode_VoiceAssist;
	}

	public int getAppSettings_AppMFBMode_PlayPause() {
		return AppSettings_AppMFBMode_PlayPause;
	}

	public void setAppSettings_AppMFBMode_PlayPause(int appSettings_AppMFBMode_PlayPause) {
		AppSettings_AppMFBMode_PlayPause = appSettings_AppMFBMode_PlayPause;
	}

	public int getAppSettings_AppHFPToggle_On() {
		return AppSettings_AppHFPToggle_On;
	}

	public void setAppSettings_AppHFPToggle_On(int appSettings_AppHFPToggle_On) {
		AppSettings_AppHFPToggle_On = appSettings_AppHFPToggle_On;
	}

	public int getAppSettings_AppHFPToggle_Off() {
		return AppSettings_AppHFPToggle_Off;
	}

	public void setAppSettings_AppHFPToggle_Off(int appSettings_AppHFPToggle_Off) {
		AppSettings_AppHFPToggle_Off = appSettings_AppHFPToggle_Off;
	}

	public int getAppSettings_AppEQMode_Indoor() {
		return AppSettings_AppEQMode_Indoor;
	}

	public void setAppSettings_AppEQMode_Indoor(int appSettings_AppEQMode_Indoor) {
		AppSettings_AppEQMode_Indoor = appSettings_AppEQMode_Indoor;
	}

	public int getAppSettings_AppEQMode_Outdoor() {
		return AppSettings_AppEQMode_Outdoor;
	}

	public void setAppSettings_AppEQMode_Outdoor(int appSettings_AppEQMode_Outdoor) {
		AppSettings_AppEQMode_Outdoor = appSettings_AppEQMode_Outdoor;
	}

	public int getAppSettings_AppDevMode_Indoor() {
		return AppSettings_AppDevMode_Indoor;
	}

	public void setAppSettings_AppDevMode_Indoor(int appSettings_AppDevMode_Indoor) {
		AppSettings_AppDevMode_Indoor = appSettings_AppDevMode_Indoor;
	}

	public int getAppSettings_AppDevMode_Outdoor() {
		return AppSettings_AppDevMode_Outdoor;
	}

	public void setAppSettings_AppDevMode_Outdoor(int appSettings_AppDevMode_Outdoor) {
		AppSettings_AppDevMode_Outdoor = appSettings_AppDevMode_Outdoor;
	}

	public int getOTAStatus_Success() {
		return OTAStatus_Success;
	}

	public void setOTAStatus_Success(int oTAStatus_Success) {
		OTAStatus_Success = oTAStatus_Success;
	}

	public int getOTAStatus_Failure() {
		return OTAStatus_Failure;
	}

	public void setOTAStatus_Failure(int oTAStatus_Failure) {
		OTAStatus_Failure = oTAStatus_Failure;
	}

	public int getOTAStatus_Duration() {
		return OTAStatus_Duration;
	}

	public void setOTAStatus_Duration(int oTAStatus_Duration) {
		OTAStatus_Duration = oTAStatus_Duration;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}
}
