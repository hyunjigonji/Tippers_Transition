package tippersWrapper.virtualSensors;

import com.google.gson.JsonObject;

public class request {
	public String destination;
	public String observation;
	public String duration;
	public String frequancy;

	public request(String dest, String obs) {
		this.destination = dest;
		this.observation = obs;
	}

	public request(String dest, String obs, String dur, String freq) {
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
