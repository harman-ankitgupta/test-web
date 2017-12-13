package com.harman.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class MariaModel implements MariaStructure, DBkeys {

	static MariaModel mariaModel;

	public static MariaModel getInstance() {
		if (mariaModel == null)
			mariaModel = new MariaModel();
		return mariaModel;
	}

	Connection connn = null;

	public Connection openConnection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			connn = DriverManager.getConnection("jdbc:mariadb://localhost/DEVICE_INFO_STORE", "root", "");
			connn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1/device_info_store", "root", "abcd123");
			System.out.println("Connected database successfully...");
		} catch (SQLException e) {
			System.out.println("Failed to connect db");
		} catch (Exception e) {
			System.out.println("Failed to connect db");
		}
		return connn;
	}

	public void closeConnection() {
		try {
			if (connn != null) {
				connn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public String getDeviceInformation(String device_id) {
		Connection conn = null;
		Statement stmt = null;
		JSONArray jsonArray = new JSONArray();
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.mariadb.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost/DEVICE_INFO_STORE", "root", "");
			System.out.println("Connected database successfully...");
			stmt = conn.createStatement();
			ResultSet rs;
			String query;
			if (device_id != null && !device_id.equalsIgnoreCase("")) {
				query = "SELECT * FROM device_registration WHERE device_id=" + device_id;
			} else
				query = "SELECT * FROM device_registration";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("device_id", rs.getString("device_id"));
				jsonObject.put("device_model", rs.getString("device_model"));
				jsonObject.put("operations_name", rs.getString("operations_name"));
				jsonObject.put("operations_params", rs.getString("operations_params"));
				jsonObject.put("fw_version", rs.getString("fw_version"));
				jsonObject.put("sw_version", rs.getString("sw_version"));
				jsonObject.put("connection", rs.getString("connection"));
				jsonArray.put(jsonObject);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		if (jsonArray.length() == 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "Record are not found in tables.");
			return jsonObject.toString();
		} else
			return jsonArray.toString();
	}

	public String insertDeviceInformation(String device_id, String device_model, String operations_name,
			String operations_params, String fw_version, String sw_version, String connection) {
		Connection conn = null;
		Statement stmt = null;
		int response = 0;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.mariadb.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost/DEVICE_INFO_STORE", "root", "");
			System.out.println("Connected database successfully...");
			// STEP 4: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();

			Statement statement = conn.createStatement();
			String query = "INSERT INTO `device_info_table`(device_id,device_model,operations_name,operations_params,fw_version,sw_version,connection) VALUE ('"
					+ device_id + "','" + device_model + "','" + operations_name + "','" + operations_params + "','"
					+ fw_version + "','" + sw_version + "','" + connection + "')";
			response = statement.executeUpdate(query);

		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLException " + se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQLException " + e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
				System.out.println("SQLException while closing data");
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return String.valueOf(response);
	}

	public int insertDeviceModel(HarmanDeviceModel mHarmanDeviceModel, Connection conn) {
		int response = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String query = "select * from " + harmanDevice + " where " + macAddress + " = " + "'"
					+ mHarmanDeviceModel.getMacAddress() + "'";
			ResultSet ifExistsResponse = stmt.executeQuery(query);
			ifExistsResponse.last();
			if (ifExistsResponse.getRow() == 0) {
				String queryInsertNewRow = "INSERT INTO " + harmanDevice + "(" + macAddress + "," + productId + ","
						+ colorId + "," + productName + "," + colorName + "," + FirmwareVersion + "," + AppVersion
						+ ") VALUE ('" + mHarmanDeviceModel.getMacAddress() + "','" + mHarmanDeviceModel.getProductId()
						+ "','" + mHarmanDeviceModel.getColorId() + "','" + mHarmanDeviceModel.getProductName() + "','"
						+ mHarmanDeviceModel.getColorName() + "','" + mHarmanDeviceModel.getFirmwareVersion() + "','"
						+ mHarmanDeviceModel.getAppVersion() + "')";
				response = stmt.executeUpdate(queryInsertNewRow);
			} else {
				String queryUpdate = "update " + harmanDevice + " set " + FirmwareVersion + "= '"
						+ mHarmanDeviceModel.getFirmwareVersion() + "'," + AppVersion + " = '"
						+ mHarmanDeviceModel.getAppVersion() + "' where " + macAddress + " = '"
						+ mHarmanDeviceModel.getMacAddress() + "'";
				response = stmt.executeUpdate(queryUpdate);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLException " + se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQLException " + e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se) {
				System.out.println("SQLException while closing data");
			}
		}
		return response;
	}

	public int insertDeviceAnalytics(DeviceAnalyticsModel mDeviceAnalyticsModel, Connection conn) {
		int response = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String query = "select * from " + DeviceAnalytics + " where harmanDevice_Id = " + "'"
					+ mDeviceAnalyticsModel.getMacaddress() + "'";
			ResultSet ifExistsResponse = stmt.executeQuery(query);
			ifExistsResponse.last();
			if (ifExistsResponse.getRow() == 0) {
				String queryInsertNewRow = "INSERT INTO " + DeviceAnalytics + "(harmanDevice_Id," + Broadcaster + ","
						+ Receiver + "," + CriticalTemperatureShutDown + "," + PowerOnOffCount + "," + EQSettings_Indoor
						+ "," + EQSettings_Outdoor + "," + PowerBankUsage + ") VALUE ('"
						+ mDeviceAnalyticsModel.getMacaddress() + "','" + mDeviceAnalyticsModel.getBroadcaster() + "','"
						+ mDeviceAnalyticsModel.getReceiver() + "','"
						+ mDeviceAnalyticsModel.getCriticalTemperatureShutDown() + "','"
						+ mDeviceAnalyticsModel.getPowerOnOffCount() + "','"
						+ mDeviceAnalyticsModel.getEQSettings_Indoor() + "','"
						+ mDeviceAnalyticsModel.getEQSettings_Outdoor() + "','"
						+ mDeviceAnalyticsModel.getPowerBankUsage() + "')";
				response = stmt.executeUpdate(queryInsertNewRow);
			} else {
				int broadcastercount = ifExistsResponse.getInt(Broadcaster) + mDeviceAnalyticsModel.getBroadcaster();
				int Receivercount = ifExistsResponse.getInt(Receiver) + mDeviceAnalyticsModel.getReceiver();
				int CriticalTemperatureShutDowncount = ifExistsResponse.getInt(CriticalTemperatureShutDown)
						+ mDeviceAnalyticsModel.getCriticalTemperatureShutDown();
				int PowerOnOffCountcount = ifExistsResponse.getInt(PowerOnOffCount)
						+ mDeviceAnalyticsModel.getPowerOnOffCount();
				int EQSettings_Indoorcount = ifExistsResponse.getInt(EQSettings_Indoor)
						+ mDeviceAnalyticsModel.getEQSettings_Indoor();
				int EQSettings_Outdoorcount = ifExistsResponse.getInt(EQSettings_Outdoor)
						+ mDeviceAnalyticsModel.getEQSettings_Outdoor();
				int PowerBankUsagecount = ifExistsResponse.getInt(PowerBankUsage)
						+ mDeviceAnalyticsModel.getPowerBankUsage();

				String queryUpdate = "update " + DeviceAnalytics + " set " + Broadcaster + "=" + broadcastercount + ","
						+ Receiver + "=" + Receivercount + "," + CriticalTemperatureShutDown + "="
						+ CriticalTemperatureShutDowncount + "," + PowerOnOffCount + "=" + PowerOnOffCountcount + ","
						+ EQSettings_Indoor + "=" + EQSettings_Indoorcount + "," + EQSettings_Outdoor + "="
						+ EQSettings_Outdoorcount + "," + PowerBankUsage + "=" + PowerBankUsagecount
						+ " where harmanDevice_Id = '" + mDeviceAnalyticsModel.getMacaddress() + "'";
				response = stmt.executeUpdate(queryUpdate);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLException " + se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQLException " + e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se) {
				System.out.println("SQLException while closing data");
			}
		}
		return response;
	}

	public int insertAppAnalytics(AppAnalyticsModel mAppAnalyticsModel, Connection conn) {
		int response = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String query = "select * from " + AppAnalytics + " where harmanDevice_Id = " + "'"
					+ mAppAnalyticsModel.getMacaddress() + "'";
			ResultSet ifExistsResponse = stmt.executeQuery(query);
			ifExistsResponse.last();
			if (ifExistsResponse.getRow() == 0) {
				String queryInsertNewRow = "INSERT INTO " + AppAnalytics + "(harmanDevice_Id," + SpeakerMode_Stereo
						+ "," + SpeakerMode_Party + "," + SpeakerMode_Single + "," + AppSettings_AppToneToggle_On + ","
						+ AppSettings_AppToneToggle_Off + "," + AppSettings_AppMFBMode_VoiceAssist + ","
						+ AppSettings_AppMFBMode_PlayPause + "," + AppSettings_AppHFPToggle_On + ","
						+ AppSettings_AppHFPToggle_Off + "," + AppSettings_AppEQMode_Indoor + ","
						+ AppSettings_AppEQMode_Outdoor + "," + AppSettings_AppDevMode_Indoor + ","
						+ AppSettings_AppDevMode_Outdoor
						+ ",AppSettings_OTAStatus_Success,AppSettings_OTAStatus_Failure,AppSettings_OTAStatus_Duration"
						+ ") VALUE ('" + mAppAnalyticsModel.getMacaddress() + "',"
						+ mAppAnalyticsModel.getSpeakerMode_Stereo() + "," + mAppAnalyticsModel.getSpeakerMode_Party()
						+ "," + mAppAnalyticsModel.getSpeakerMode_Single() + ","
						+ mAppAnalyticsModel.getAppSettings_AppToneToggle_On() + ","
						+ mAppAnalyticsModel.getAppSettings_AppToneToggle_Off() + ","
						+ mAppAnalyticsModel.getAppSettings_AppMFBMode_VoiceAssist() + ","
						+ mAppAnalyticsModel.getAppSettings_AppMFBMode_PlayPause() + ","
						+ mAppAnalyticsModel.getAppSettings_AppHFPToggle_On() + ","
						+ mAppAnalyticsModel.getAppSettings_AppHFPToggle_Off() + ","
						+ mAppAnalyticsModel.getAppSettings_AppEQMode_Indoor() + ","
						+ mAppAnalyticsModel.getAppSettings_AppEQMode_Outdoor() + ","
						+ mAppAnalyticsModel.getAppSettings_AppDevMode_Indoor() + ","
						+ mAppAnalyticsModel.getAppSettings_AppDevMode_Outdoor() + ","
						+ mAppAnalyticsModel.getOTAStatus_Success() + "," + mAppAnalyticsModel.getOTAStatus_Failure()
						+ "," + mAppAnalyticsModel.getOTAStatus_Duration() + ")";
				response = stmt.executeUpdate(queryInsertNewRow);
			} else {
				int AppSettings_AppToneToggle_OnCount = ifExistsResponse.getInt(AppSettings_AppToneToggle_On)
						+ mAppAnalyticsModel.getAppSettings_AppToneToggle_On();
				int SpeakerMode_StereoCount = ifExistsResponse.getInt(SpeakerMode_Stereo)
						+ mAppAnalyticsModel.getSpeakerMode_Stereo();
				int SpeakerMode_PartyCount = ifExistsResponse.getInt(SpeakerMode_Party)
						+ mAppAnalyticsModel.getSpeakerMode_Party();

				String queryUpdate = "update " + AppAnalytics + " set " + AppSettings_AppToneToggle_On + "= "
						+ AppSettings_AppToneToggle_OnCount + "," + SpeakerMode_Stereo + " = " + SpeakerMode_StereoCount
						+ "," + SpeakerMode_Party + "= " + SpeakerMode_PartyCount + " where harmanDevice_Id = '"
						+ mAppAnalyticsModel.getMacaddress() + "'";
				response = stmt.executeUpdate(queryUpdate);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLException " + se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQLException " + e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se) {
				System.out.println("SQLException while closing data");
			}
		}
		return response;
	}

	public int insertDeviceAnalytics(DeviceAnalyticsModel mDeviceAnalyticsModel) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insertAppAnalytics(AppAnalyticsModel mAppAnalyticsModel) {
		// TODO Auto-generated method stub
		return 0;
	}

}
