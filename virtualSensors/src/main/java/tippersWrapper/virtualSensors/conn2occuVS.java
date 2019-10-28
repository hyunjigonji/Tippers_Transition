package tippersWrapper.virtualSensors;

import java.lang.reflect.Array;
import java.util.List;

import org.restlet.data.*;
import org.restlet.resource.*;

import com.google.gson.*;

public class conn2occuVS {
	public conn2occuVS() {
		request re = new request("http://localhost:8081", "/getConn");
		
		// re.getRequest();		//Json Object
		
		ClientResource client = new ClientResource("http://localhost:8082/wrapper");
		
		client.post(re.getRequest());		// post request to wrapper
	}
	
//	public void countObservation(List<JsonObject> json) {
//		if(!json.isEmpty()) {
//			
//		}
//	}
}
