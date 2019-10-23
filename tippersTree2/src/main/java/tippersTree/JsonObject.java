package tippersTree;

import org.json.simple.JSONObject;

public class JsonObject {
	public static JSONObject callWrapper(String observation) {
		JSONObject obj = new JSONObject();
		
		obj.put("count","54");
		obj.put("observation",observation);
		
		return obj;
	}
}
