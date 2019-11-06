package tippersTree;
/*
enum senTypes {
	typeVir, typePhy,
};
*/
public class SR {
	//public senTypes senType;
	
	public String Sensor;
	public String Observation;
	public String Entity;
	
	public SR() { }
	
	public SR(String newSensor, String newObservation, String newEntity) {
		Sensor = newSensor;
		Observation = newObservation;
		Entity = newEntity;
	}
}