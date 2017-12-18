package com.harman.Model;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class MongoDBInsertion {
	static MongoDBInsertion mMongoDBInsertion = null;

    public static MongoDBInsertion getInstance() {
          if (mMongoDBInsertion == null)
        	  mMongoDBInsertion = new MongoDBInsertion();
          return mMongoDBInsertion;
    }

    public void insertinto(String requestBody) throws UnknownHostException {

          try{
                 MongoClient mongo = new MongoClient("localhost", 27017);
                 DB db = mongo.getDB("DEVICE_INFO_STORE");
                 DBCollection col = db.getCollection("SmartAudioAnalytics");
                 BasicDBObject dbObject = (BasicDBObject) JSON.parse(requestBody);                                    
                 Date date = new Date();
                 dbObject.append("date", date);
                 col.insert(dbObject);
          }
          catch (UnknownHostException e) {
                 
          }catch (MongoException e) {
                 e.printStackTrace();
          }
    }

}
