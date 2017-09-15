
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

@Path("Boat")
public class SeeBoat {
	@GET
	@Path("/findBoat/{name}") 
	@Produces(MediaType.TEXT_XML)  
	
	public String getBoat(@PathParam("name") String name) {
		DB db = new Connect().mongo();
		DBCollection table = db.getCollection("Boat");
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("maxseat", name);
		
		DBCursor cursor = table.find(searchQuery);
		List<DBObject> myList = cursor.toArray();
		
		String xml = "<?xml version=\"1.0\"?>";
		xml += "<return>";
		for (DBObject object : myList) {
			xml += "<boatDetailed>";
				xml += "<boat_id>"+object.get("boat_id").toString()+"</boat_id>";
				xml += "<name>"+object.get("name").toString()+"</name>";
				xml += "<type>"+object.get("type").toString()+"</type>";
				xml += "<maxseat>"+object.get("maxseat").toString()+"</maxseat>";
				
			xml += "</boatDetailed>";
		}
		xml += "</return>";
		
		System.out.println(xml);
		
		return xml;  
	}
	
	
}