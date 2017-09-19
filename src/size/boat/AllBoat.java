package size.boat;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;

import com.connect.Connect;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class AllBoat {
	@GET
	@Path("/findBoat") 
	@Produces(MediaType.TEXT_XML)  
	public String allCourse() {   
		
		DB db = new Connect().mongo();
		DBCollection table = db.getCollection("Boat");
		
		DBCursor cursor = table.find();
		List<DBObject> myList = cursor.toArray();
		
		// create xml
		String xml = "<?xml version=\"1.0\"?>";
		xml += "<return>";
		for (DBObject object : myList) {
			xml += "<boat>";
				xml += "<id>"+object.get("_id").toString()+"</id>";
				xml += "<idboat>"+object.get("boat_id").toString()+"</idboat>";
				xml += "<name>"+object.get("name").toString()+"</name>";
				xml += "<type>"+object.get("type").toString()+"</type>";
				xml += "<maxseat>"+object.get("maxseat").toString()+"</maxseat>";
			xml += "</boat>";
		}
		xml += "</return>";
		
		System.out.println(xml);
		
		return xml;  
	} 
	
	@GET
	@Path("/findBoat/{maxseat}") 
	@Produces(MediaType.TEXT_XML)  
	
	public String findBoat(@PathParam("maxseat") String maxseat) {   
		
		DB db = new Connect().mongo();
		DBCollection table = db.getCollection("Boat");
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("maxseat", maxseat);
		
		//find boat id
		DBObject dbObject = table.findOne(searchQuery);
		String boatID = dbObject.get("_id").toString();
		
		// select table
		table = db.getCollection("Boat");
		
		// search course by lecturer id
		searchQuery = new BasicDBObject();
		searchQuery.put("boat_id", new ObjectId(boatID));
		
		DBCursor cursor = table.find(searchQuery);
		List<DBObject> myList = cursor.toArray();
		
		// create xml
		String xml = "<?xml version=\"1.0\"?>";
		xml += "<return>";
		for (DBObject object : myList) {
			xml += "<boat>";
				xml += "<id>"+object.get("_id").toString()+"</id>";
				xml += "<boat_id>"+object.get("boat_id").toString()+"</boat_id>";
				xml += "<name>"+object.get("name").toString()+"</name>";
				xml += "<type>"+object.get("type").toString()+"</type>";
				xml += "<maxseat>"+object.get("maxseat").toString()+"</maxseat>";
			xml += "</boat>";
		}
		xml += "</return>";
		
		System.out.println(xml);
		
		return xml;  

	}
}