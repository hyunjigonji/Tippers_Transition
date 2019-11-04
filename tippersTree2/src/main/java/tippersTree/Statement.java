package tippersTree;

import java.util.ArrayList;

public class Statement {
	public String Sensor;
	public String Observation;
	public String Entity;
	public String Address;
	public ArrayList<Statement> Former = new ArrayList<Statement>(); 
	// public float Value;
	
	public Statement() {}
	
	public Statement(String newSensor, String newObservation,String newEntity, String newAddress) {
		Sensor = newSensor;
		Observation = newObservation;
		Entity = newEntity;
		Address = newAddress;
	}
	
	public Statement(String newSensor, String newObservation,String newEntity, String newAddress, ArrayList<Statement> newStatement) {
		Sensor = newSensor;
		Observation = newObservation;
		Entity = newEntity;
		Address = newAddress;
		Former = newStatement;
	}
}
