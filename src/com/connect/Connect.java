package com.connect;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class Connect {
	
	public DB mongo() {
		MongoClient mongo = new MongoClient("localhost",27017);
		DB db = mongo.getDB("marineford");
		return db;
	}
}
