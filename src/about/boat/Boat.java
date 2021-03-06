package about.boat;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

@Path("boat")
public class Boat {
	
	@GET
	@Path("/findBoatByName") 
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
	
	@GET
	@Path("/findBoatByName/{name}") 
	@Produces(MediaType.TEXT_XML)  
	public String findCourseByLecturerName(@PathParam("name") String name) {   
		
		DB db = new Connect().mongo();
		DBCollection table = db.getCollection("Boat");
		
		
		
		ArrayList orList = new ArrayList();
		orList.add(new BasicDBObject("type",name.toLowerCase()));
		orList.add(new BasicDBObject("name",name.toLowerCase()));
		orList.add(new BasicDBObject("boat_id",name.toLowerCase()));
		orList.add(new BasicDBObject("maxseat",name.toLowerCase()));
		BasicDBObject searchQuery = new BasicDBObject("$or", orList);
		
//		BasicDBObject searchQuery = new BasicDBObject();
//		searchQuery.put("name", name);
		
		//find lecturer id
//		DBObject dbObject = table.findOne(searchQuery);
//		String Id = dbObject.get("_id").toString();
//		
		// select table
//		table = db.getCollection("Boat");
		// search course by lecturer id
//		searchQuery = new BasicDBObject();
//		searchQuery.put("name", new ObjectId(name));
		
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

	

	@POST
	@Path("/create") 
	@Produces(MediaType.TEXT_PLAIN)  
	public String create(@FormParam("boat_id") String boat_id, 
						@FormParam("name") String name, 
						@FormParam("type") String type, 
						@FormParam("maxseat") String maxseat) {   
		
		DB db = new Connect().mongo();
		
		DBCollection collection = db.getCollection("Boat");
		
		System.out.println(boat_id);
		System.out.println(name);
		System.out.println(type);
		System.out.println(maxseat);

		
		BasicDBObject document = new BasicDBObject();

		document.put("boat_id", boat_id);
		document.put("name", name);
		document.put("type", type);
		document.put("maxseat", maxseat);


		collection.insert(document);
		
		return "true";  
	}
	
	@POST
	@Path("/update") 
	@Produces(MediaType.TEXT_PLAIN)  
	public String update(@FormParam("id") String id, 
						@FormParam("boat_id") String boat_id, 
						@FormParam("name") String name, 
						@FormParam("type") String type, 
						@FormParam("maxseat") String maxseat) {   
		
		DB db = new Connect().mongo();
		DBCollection collection = db.getCollection("Boat");
		
		BasicDBObject document = new BasicDBObject();
		
		document.put("boat_id", boat_id);
		document.put("name", name);
		document.put("type", type);
		document.put("maxseat", maxseat);
		
		BasicDBObject setQuery = new BasicDBObject();
        setQuery.put("$set", document);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", new ObjectId(id));

		collection.update(searchQuery, setQuery);
		
		return "true";  
	} 
	
	@GET
	@Path("/delete/{id}") 
	@Produces(MediaType.TEXT_PLAIN)  
	public String delete(@PathParam("id") String id) {   
		System.out.println(id);
		DB db = new Connect().mongo();
		DBCollection collection = db.getCollection("Boat");
		
		DBObject document = collection.findOne(new ObjectId(id));
		collection.remove(document);
		
		return "true";  
	}
	
	@GET
	@Path("/getUpdate/{id}") 
	@Produces(MediaType.TEXT_XML)  
	public String getUpdate(@PathParam("id") String id) {   
		
		DB db = new Connect().mongo();
		DBCollection table = db.getCollection("Boat");
		
		DBObject object = table.findOne(new ObjectId(id));
		
		// create xml
		String xml = "<?xml version=\"1.0\"?>";
		xml += "<return>";
		xml += "<boat>";
		xml += "<id>"+object.get("_id").toString()+"</id>";
		xml += "<boat_id>"+object.get("boat_id").toString()+"</boat_id>";
		xml += "<name>"+object.get("name").toString()+"</name>";
		xml += "<type>"+object.get("type").toString()+"</type>";
		xml += "<maxseat>"+object.get("maxseat").toString()+"</maxseat>";
	xml += "</boat>";
		xml += "</return>";
		
		return xml;  
	} 
	
	
}
