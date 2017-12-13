package com.harman.Model;

public class DeviceAnalyticsModel {

	private int Broadcaster, Receiver, CriticalTemperatureShutDown, PowerOnOffCount, EQSettings_Indoor,
			EQSettings_Outdoor, PowerBankUsage;
	private String macaddress;

	public int getBroadcaster() {
		return Broadcaster;
	}

	public void setBroadcaster(int broadcaster) {
		Broadcaster = broadcaster;
	}

	public int getReceiver() {
		return Receiver;
	}

	public void setReceiver(int receiver) {
		Receiver = receiver;
	}

	public int getCriticalTemperatureShutDown() {
		return CriticalTemperatureShutDown;
	}

	public void setCriticalTemperatureShutDown(int criticalTemperatureShutDown) {
		CriticalTemperatureShutDown = criticalTemperatureShutDown;
	}

	public int getPowerOnOffCount() {
		return PowerOnOffCount;
	}

	public void setPowerOnOffCount(int powerOnOffCount) {
		PowerOnOffCount = powerOnOffCount;
	}

	public int getEQSettings_Indoor() {
		return EQSettings_Indoor;
	}

	public void setEQSettings_Indoor(int eQSettings_Indoor) {
		EQSettings_Indoor = eQSettings_Indoor;
	}

	public int getEQSettings_Outdoor() {
		return EQSettings_Outdoor;
	}

	public void setEQSettings_Outdoor(int eQSettings_Outdoor) {
		EQSettings_Outdoor = eQSettings_Outdoor;
	}

	public int getPowerBankUsage() {
		return PowerBankUsage;
	}

	public void setPowerBankUsage(int powerBankUsage) {
		PowerBankUsage = powerBankUsage;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

}
