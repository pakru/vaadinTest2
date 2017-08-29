package com.test.testApp;

import java.awt.List;
import java.time.LocalDateTime;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class dbDataAccess {
	private String userLogin = "admin1";
	private String userPass = "1234";
	private String dbAddress = "192.168.118.37";
	private String dbPort = "27017";
	private String dbUserSource = "admin";
	private String dbVisits = "Visitors";
	private String totalVisitorsCollection = "allVisits";
	private String unicVisitsCollection = "unicVisits";
	private String totalVisitsCounterName = "totalVisits";
	private String unicVisitsCounterName = "unicVisits";
	private String countersCollectionName = "countersCollection";
	private MongoClient mongoDbClient;  //  mongodb://admin1:1234@192.168.118.37:27017/?authSource=admin
	private DB dbVisitors;
	
	dbDataAccess(){
		mongoDbClient = new MongoClient(new MongoClientURI("mongodb://" + userLogin + ":"+ userPass + "@" + dbAddress +":" + dbPort +
				"/?aurhSource="+dbUserSource));
		dbVisitors = mongoDbClient.getDB(dbVisits);
	}
	
	private void createCounter(String counterName, int initSeqNumber) {
		BasicDBObject counterTotal = new BasicDBObject();
		counterTotal.append("_id", counterName);
		counterTotal.append("seq", initSeqNumber);
		DBCollection collection = dbVisitors.getCollection(countersCollectionName);
		collection.insert(counterTotal);
		
	}
		
	public void addVisitorData(String ipAddr, String sessionID) {
		incClientCounterTotal();
		
		LocalDateTime timeStamp = LocalDateTime.now();
		
		BasicDBObject visitorData = new BasicDBObject("_id", getNextSequence("dataID"))
													.append("DateTime", timeStamp.toString())
													.append("IPaddress", ipAddr)
													.append("sessionID", sessionID);
		
		// here must be unic visitor check
		
		DBCollection collection = dbVisitors.getCollection(totalVisitorsCollection);		
		collection.insert(visitorData);
		System.out.println("Added visitor data to db");
		
	}
		
	public void incClientCounterTotal() {
		String totalCounterName = new String("totalVisits");
		DBCollection countersCollection = dbVisitors.getCollection(countersCollectionName);
		
		/*
		if (countersCollection.count() == 0) {
			createCounter(totalCounterName,0);
			
		} */
		
		BasicDBObject dbObjToFind = new BasicDBObject();
		BasicDBObject dbObjToUpdate = new BasicDBObject();
		
		dbObjToFind.put("_id", totalCounterName);
		
		if (!countersCollection.find(dbObjToFind).hasNext()) {
			createCounter(totalCounterName,0);
		}
				
		dbObjToUpdate.put("$inc", new BasicDBObject("seq", 1));
		
		DBObject obj = new BasicDBObject();
		obj = countersCollection.findAndModify(dbObjToFind, dbObjToUpdate);	
		
		
	}
	/*
	public void dbAccess() {
		
		
		MongoClient mongoClient = new MongoClient( new MongoClientURI("mongodb://admin1:1234@192.168.118.37:27017/?authSource=admin"));
		
		//mongoClient.
		
		DB database = mongoClient.getDB("Test2");
		
		//List<Integer> books = Arrays.asList(27464, 747854);
		
		
		DBObject person = new BasicDBObject("_id", getNextSequence("userid"))
		                            .append("name", "Jo Bloggs")
		                            .append("address", new BasicDBObject("street", "123 Fake St")
		                                                         .append("city", "Faketon")
		                                                         .append("state", "MA")
		                                                         .append("zip", 12345))
		                            .append("books", "Perfect books!"); 
				
		
		DBCollection collection = database.getCollection("people");
		
		

		collection.insert(person);
		System.out.println("Added data to db"); 
		
		//getNextSequence("userid");
				
	}
	*/
	//private void createCountersCollection
	
	
	
	public Object getNextSequence(String name){
		
		DBCollection countersCollection = dbVisitors.getCollection(countersCollectionName);
		
		
		BasicDBObject dbObjToFind = new BasicDBObject();
		BasicDBObject dbObjToUpdate = new BasicDBObject();
		
		dbObjToFind.put("_id", name);
		
		if (!countersCollection.find(dbObjToFind).hasNext()) {
			createCounter(name, 0);
		}
			
		dbObjToUpdate.put("$inc", new BasicDBObject("seq", 1));
		
		DBObject obj = new BasicDBObject();
		obj = countersCollection.findAndModify(dbObjToFind, dbObjToUpdate);
		
		//System.out.println("Collection: " + countersCollection.find().toArray().toString());
		//System.out.println("Collection: " + collection.find(dbObjToFind).toString());
		//collection.modify
		
		//System.out.println("Collection find and modify: " + obj.toString());
		//System.out.println("Found: " + obj.toString());  
			 		
		return obj.get("seq"); 				
	}
	
	public String getTotalVisits() {
		DBCollection countersCollection = dbVisitors.getCollection(countersCollectionName);		
		
		BasicDBObject dbObjToFind = new BasicDBObject();
		dbObjToFind.put("_id", totalVisitsCounterName);
		DBObject obj = new BasicDBObject();
		
		obj = countersCollection.findOne(dbObjToFind);
		
		return obj.get("seq").toString();
		
	}
	public String getUniqueVisits() {
		//DBCollection countersCollection = dbVisitors.getCollection(countersCollectionName);
		java.util.List sessionIDs = dbVisitors.getCollection(totalVisitorsCollection).distinct("sessionID");
		
		//System.out.println("Unique size: " + String.valueOf(sessionIDs.size()));
		//System.out.println("SessionIDS: " + sessionIDs.toString());
		
		return String.valueOf(sessionIDs.size());
		
		
	}

}
