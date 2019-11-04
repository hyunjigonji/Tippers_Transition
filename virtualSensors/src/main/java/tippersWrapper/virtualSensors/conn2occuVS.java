package tippersWrapper.virtualSensors;

import org.restlet.resource.*;

public class conn2occuVS {
	public conn2occuVS() {
		request re = new request("http://IPofWrapper:8081", "/");
		
		// re.getRequest();		//Json Object
		
		ClientResource client = new ClientResource("http://localhost:8082/wrapper");
		
		client.post(re.getRequest());		// post request to wrapper
	}
}
