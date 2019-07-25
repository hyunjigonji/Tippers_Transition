package translation;
/*
enum senTypes {
	typeVir, typePhy,
};
*/
public class SR {
	//public senTypes senType;
	
	public int Sensor;
	public int Observation;
	public int Entity;
	
	public SR() { }
	
	public SR(int newSensor, int newObservation, int newEntity) {
		Sensor = newSensor;
		Observation = newObservation;
		Entity = newEntity;
	}
}
