package tippersTree;

import java.util.ArrayList;

public class Statement {
	public String Sensor;
	public String Observation;
	public String Entity;
	public ArrayList<Statement> Former = new ArrayList<Statement>(); 
	
	public Statement() {}
	
	public Statement(String newSensor, String newObservation,String newEntity) {
		Sensor = newSensor;
		Observation = newObservation;
		Entity = newEntity;
	}
	
	public Statement(String newSensor, String newObservation,String newEntity, ArrayList<Statement> newStatement) {
		Sensor = newSensor;
		Observation = newObservation;
		Entity = newEntity;
		Former = newStatement;
	}
}
