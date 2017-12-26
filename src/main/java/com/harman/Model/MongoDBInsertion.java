package com.harman.Model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import com.harman.controller.TestController;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import java.util.*;

import org.json.JSONObject;

public class MongoDBInsertion {

	static MongoDBInsertion testmodel = null;

	public static MongoDBInsertion getInstance() {
		if (testmodel == null)
			testmodel = new MongoDBInsertion();
		return testmodel;
	}

	public void insertinto(String requestBody) throws IOException {

		try{
			MongoClient mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("DEVICE_INFO_STORE");
			DBCollection col = db.getCollection("SmartAudioAnalytics");
			BasicDBObject dbObject = (BasicDBObject) JSON.parse(requestBody);						
			Date date = new Date();
			dbObject.append("date", date);
			col.insert(dbObject);
			
			
			
			BasicDBObject allQuery = new BasicDBObject();
			BasicDBObject fields = new BasicDBObject();
			
			//allQuery.put("AppAnalytics.SpeakerMode.Stereo",new BasicDBObject("$gt", 3));
			 	
			 
			 Date fromDate, toDate;
			 Calendar cal = Calendar.getInstance();
			 cal.set(Calendar.MONTH,11);
			 cal.set(Calendar.DATE, 18);
			 cal.set(Calendar.YEAR, 2017);
			 cal.set(Calendar.HOUR,0);
			 cal.set(Calendar.MINUTE,0);
			 cal.set(Calendar.SECOND,0);
			 fromDate = cal.getTime();

			 Calendar cal1 = Calendar.getInstance();
			 cal1.set(Calendar.MONTH, 11);
			 cal1.set(Calendar.DATE, 18);
			 cal1.set(Calendar.YEAR, 2017);
			 cal1.set(Calendar.HOUR,15);
			 cal1.set(Calendar.MINUTE,0);
			 cal1.set(Calendar.SECOND,0);
			 toDate = cal1.getTime();
			
			System.out.println(BasicDBObjectBuilder.start("$gte", fromDate).add("$lte", toDate).get());
			//allQuery.put("date", BasicDBObjectBuilder.start("$gte", fromDate).add("$lte", toDate).get());
			
			allQuery.put("date", new BasicDBObject("$gte", (new Date((new Date().getTime() - (15 * 24 * 60 * 60 * 1000))))));
			//fields.put("AppAnalytics.SpeakerMode.Stereo", 1);
			//fields.put("harmanDevice.productName", 1);
			
			
			
			//fields.put("_id", 0);
			//fields.put("date", 1);
			DBCursor results = col.find(allQuery, fields);
		
			//new BasicDBObject("Stereo",new BasicDBObject("$gt", 3))
			while(results.hasNext()) {
			    //System.out.println(results.next());
			    
			    JSONObject output = new JSONObject(JSON.serialize(results.next()));
			    TestController test = new TestController();
			    test.requestCMD(output.toString());
			}
			results.close();
			
		}
		catch (UnknownHostException e) {
			
		}catch (MongoException e) {
			e.printStackTrace();
		}
	}
}
