package tippersTree;

import java.util.*;

public class OntologyManager {
	/*
	 *  Modify it!!! Extract values from Domain Model (Ontology) !!!
	 */
	public ArrayList<String> extractEnt(UA UARequest) { // extract Entity from UA, M
		/*ArrayList<String> temp = new ArrayList<String>();
		temp.add("room1");
		temp.add("room2");
		return temp; */
		return tippersOntology.OntologyManager.extractEnt(UARequest.Entity);
	} 

	public ArrayList<String> extractProp(UA UARequest) { // extract Property from UA, M
		ArrayList<String> temp = new ArrayList<String>();
		String Prop = UARequest.Property;
		
		StringTokenizer ST = new StringTokenizer(Prop, ",");
		while(ST.hasMoreTokens()) {
			String nowToken = ST.nextToken();
			temp.add(nowToken);
		}
		
		return temp; 
	} 
	
	public ArrayList<String> extractCond(UA UARequest) { // extract Condition from UA, M
		ArrayList<String> temp = new ArrayList<String>();
		String Cond = UARequest.Condition;
		
		Condition conditions = new Condition();
		for(int i = 0 ; i < conditions.conds.size() ; i++) {
			String nowCon = conditions.conds.get(i);
			if(Cond.contains(nowCon)) {
				temp.add(nowCon);
			}
		}
		return temp; 
	}
	
	public ArrayList<String> findSensor(String Obs){
		ArrayList<String> sensors = new ArrayList<String>();
		if(Obs.equals("Occupancy")) {
			//System.out.println("findSen Occupancy");
			sensors.add("False");
			sensors.add("Con2Occ");
			sensors.add("Img2Occ");
			sensors.add("BB");
		}
		else if(Obs.equals("Connectivity")) {
			//System.out.println("findSen Location");
			sensors.add("True");
			sensors.add("Wifi");
			sensors.add("Loc2Con");
		}
		else if(Obs.equals("Image")) {
			//System.out.println("findSen Image");
			sensors.add("False");
			sensors.add("Camera");
		}
		else if(Obs.equals("Location")) {
			sensors.add("True");
			sensors.add("GPS");
		}
		//System.out.println(sensors);
		return sensors;
		//return tippersOntology.OntologyManager.findSensor(Obs);
	}
	
	public String findInput(String Sensor){
		//System.out.println("findInput " + Sensor);
		String inputObs = "";
		if(Sensor.equals("Loc2Con")) {
			//System.out.println("findInput Loc2Occ");
			inputObs = "Location";
		}
		if(Sensor.equals("Img2Occ")) {
			//System.out.println("findInput Img2Loc");
			inputObs = "Image";
		}
		if(Sensor.equals("Con2Occ")) {
			inputObs = "Connectivity";
		}
		return inputObs;
	}
	
	public boolean isVS(String Sensor) {
		if(Sensor.contains("Wifi") || Sensor.equals("GPS") || Sensor.equals("Camera") || Sensor.equals("BB")) return false;
		return true;
		
		//return tippersOntology.OntologyManager.isVS(sensor);
	}

	public boolean hasMultiInput(ArrayList<String> Sensors) {
		String determine = Sensors.get(0);
		if(determine.equals("True")) return true; // + node
		else return false; // x node
	}
	/*
	public ArrayList<String> findActuatorInd(String Prop) {
		Prop = findActuator(Prop);
		
		ArrayList<String> actuators = new ArrayList<String>();
		if(Prop.equals("TV")) {
			actuators.add("TV1");
			actuators.add("TV2");
		}
		if(Prop.equals("Light")) {
			actuators.add("Light1");
			actuators.add("Light2");
		}
		if(Prop.equals("AC")) {
			actuators.add("AC1");
			actuators.add("AC2");
		}
		return actuators;
	}*/
	
	public String findAction(String Prop) {
		String action = "";
		Action Action = new Action();
		for(int i = 0 ; i < Action.actions.size() ; i++) {
			String nowAction = Action.actions.get(i);
			if(Prop.contains(nowAction)) {
				action = nowAction;
			}
		}
		return action;
	}
	
	public String findActuator(String Prop) {
		String actuator = "";
		Actuator Acts = new Actuator();
		for(int i = 0 ; i < Acts.acts.size() ; i++) {
			String nowAct = Acts.acts.get(i);
			if(Prop.contains(nowAct)) {
				System.out.println(nowAct);
				actuator = nowAct;
			}
		}
		return actuator;
	}
	/*
	public ArrayList<String> findActInd(String Actuator) {
		ArrayList<String> acts = new ArrayList<String>();
		if(Actuator.equals("TV")) {
			acts.add("TV1");
			acts.add("TV2");
		}
		if(Actuator.equals("Light")) {
			acts.add("Light1");
		}
		if(Actuator.equals("AC")) {
			acts.add("AC1");
			acts.add("AC2");
		}
		return acts;
	}
	
	public String findAct(String Prop) {
		String actProp = "";
		if(Prop.contains("Turn on")) {
			actProp = "TurnOnProp";
		}
		return actProp;
	}*/
	
	public ArrayList<String> getAptDevices(String Sensor){
		ArrayList<String> devices = new ArrayList<String>();
		if(Sensor.equals("Wifi")) {
			devices.add("Wifi1");
			devices.add("Wifi2");
			devices.add("Wifi3");
		}
		if(Sensor.equals("GPS")) {
			devices.add("GPS1");
			devices.add("GPS2");
			devices.add("GPS3");
		}
		if(Sensor.equals("BB")) {
			devices.add("BB1");
			devices.add("BB2");
			devices.add("BB3");
		}
		return devices;
	}
	
	public boolean checkCoverage(String sen, String ent) {
		if(sen.valueOf(sen.length()-1).equals(ent.valueOf(ent.length()-1))) return true;
		return false;
	}
	
	public boolean checkAccess(String sen, String ent) {
		return true;
	}
}