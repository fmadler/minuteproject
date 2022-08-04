package net.sf.minuteProject.model.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.util.Arrays;

import org.bson.Document;

public class MongoDbTest {
	public static void main(String args[]) {
		try {
			// To connect to mongodb server
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			// Now connect to your databases
			DB db = mongoClient.getDB("northwind");
			System.out.println("Connect to database successfully");
			//boolean auth = db.authenticate(myUserName, myPassword);
			//System.out.println("Authentication: " + auth);
			DBCollection coll = db.getCollection("categories");
			System.out.println("Collection mycol selected successfully");
			DBObject myDoc = coll.findOne();
			System.out.println("doc "+myDoc.toString());
//			coll.
			//col1.remove(myDoc);
//			DBCursor cursor = coll.find("{ \"CategoryID\": 1}");
			DBCursor cursor = coll.find( new BasicDBObject ("CategoryID","1"));
			
			int i = 1;
			while (cursor.hasNext()) {
				System.out.println("Inserted Document: " + i);
				System.out.println(cursor.next());
				i++;
				
				System.out.println("cursor "+cursor.toString());
			}
			//System.out.println("Document deleted successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
