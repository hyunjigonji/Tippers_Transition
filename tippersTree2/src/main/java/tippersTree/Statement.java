package tippersTree;

public class Statement {
	public String Sensor;
	public String Observation;
	public String Entity;
	
	public Statement() {}
	
	public Statement(String newSeneor, String newObservation, String newEntity) {
		Sensor = newSeneor;
		Observation = newObservation;
		Entity = newEntity;
	}
	
	public Statement(String newSeneor, String newObservation, String newEntity, Statement newStatement) {
		Sensor = newSeneor;
		Observation = newObservation;
		Entity = newEntity;

	}
}
