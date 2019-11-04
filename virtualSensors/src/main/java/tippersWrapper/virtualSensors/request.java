package tippersWrapper.virtualSensors;

import com.google.gson.JsonObject;

public class request {
	public String destination;
	public String observation;
	public int duration;
	public int frequancy;

	public request(String dest, String obs) {
		this.destination = dest;
		this.observation = obs;
	}

	public request(String dest, String obs, int dur, int freq) {
		this.destination = dest;
		this.observation = obs;
		
		
		this.duration = dur;
		this.frequancy = freq;
	}

	public JsonObject getRequest() {
		JsonObject object = new JsonObject();

		object.addProperty("destination", destination);
		object.addProperty("observation", observation);

		return object;
	}
}
