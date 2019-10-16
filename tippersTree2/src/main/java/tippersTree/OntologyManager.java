package tippersTree;

import java.util.*;

public class OntologyManager {
	/*
	 *  Modify it!!! Extract values from Domain Model (Ontology) !!!
	 */
	public static ArrayList<String> extractEnt(UA UARequest) { // extract Entity from UA, M
		/*ArrayList<String> temp = new ArrayList<String>();
		temp.add("meetingroom1");
		return temp;*/
		return tippersOntology.OntologyManager.getIndividuals(UARequest.Entity);
	} 

	public static ArrayList<String> extractProp(UA UARequest) { // extract Property from UA, M
		ArrayList<String> temp = new ArrayList<String>();
		String Prop = UARequest.Property;
		
		StringTokenizer ST = new StringTokenizer(Prop, ",");
		while(ST.hasMoreTokens()) {
			String nowToken = ST.nextToken();
			temp.add(nowToken);
		}
		
		return temp; 
	}
	
	public static ArrayList<String> extractCond(UA UARequest) { // extract Condition from UA, M
		ArrayList<String> temp = new ArrayList<String>();
		String Cond = UARequest.Condition;
		
		Condition conditions = new Condition();
		System.out.println(conditions.test);
		for(int i = 0 ; i < conditions.conds.size() ; i++) {
			String nowCon = conditions.conds.get(i);
			if(Cond.contains(nowCon)) {
				temp.add(nowCon);
			}
		}
		return temp; 
	}
	
	public static Set<String> findSensor(String Obs){
		/*ArrayList<String> sensors = new ArrayList<String>();
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
			sensors.add("True");
			sensors.add("Camera");
		}
		else if(Obs.equals("Location")) {
			sensors.add("True");
			sensors.add("GPS");
		}
		//System.out.println(sensors);
		return sensors; */
		//System.out.println("Find sensor of " + Obs + " = " + tippersOntology.OntologyManager.findSensor(Obs));
		return tippersOntology.OntologyManager.findSensor(Obs);
	}
	
	public static ArrayList<String> findInput(String Sensor){
		/*ArrayList<String> inputObs = new ArrayList<String>();
		//System.out.println("findInput " + Sensor);
		if(Sensor.equals("Location2connectivity")) {
			//System.out.println("findInput Loc2Occ");
			inputObs.add("Location");
		}
		if(Sensor.equals("LocationImage2occupancy")) {
			//System.out.println("findInput Img2Loc");
			inputObs.add("Location");
			inputObs.add("Image");
		}
		if(Sensor.equals("Connectivity2occupancy")) {
			inputObs.add("Connectivity");
		}
		return inputObs;*/
		return tippersOntology.OntologyManager.findInput(Sensor);
	}
	
	public static boolean isVS(String Sensor) {
		/*if(Sensor.contains("Wifi") || Sensor.equals("GPS") || Sensor.equals("Camera") || Sensor.equals("BB")) return false;
		return true;*/
		System.out.println(tippersOntology.OntologyManager.isVS(Sensor));
		return tippersOntology.OntologyManager.isVS(Sensor);
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
	
	public static String findAction(String Prop) {
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
	
	public static String findActuator(String Prop) {
		String actuator = "";
		Actuator Acts = new Actuator();
		for(int i = 0 ; i < Acts.acts.size() ; i++) {
			String nowAct = Acts.acts.get(i);
			if(Prop.contains(nowAct)) {
				//System.out.println(nowAct);
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
	
	public static ArrayList<String> getAptDevices(String Sensor){
		/*ArrayList<String> devices = new ArrayList<String>();
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
		if(Sensor.equals("Camera")) {
			devices.add("Camera1");
			devices.add("Camera2");
			devices.add("Camera3");
		}
		if(Sensor.equals("TV")) {
			devices.add("TV1");
			devices.add("TV2");
			devices.add("TV3");
		}
		if(Sensor.equals("Light")) {
			devices.add("Light1");
			devices.add("Light2");
			devices.add("Light3");
		}
		if(Sensor.equals("AC")) {
			devices.add("AC1");
			devices.add("AC2");
			devices.add("AC3");
		}
		return devices;*/
		return tippersOntology.OntologyManager.getAptDevice(Sensor);
	}
	
	public static boolean checkCoverage(String Sen, String Ent) {
		/*if(sen.contains("Wifi1")) return false;
		if((sen.contains("1") && ent.contains("1")) || (sen.contains("2") && ent.contains("2")) || (sen.contains("3") && ent.contains("3"))) return true;
		return false;*/
		//System.out.println("check " +sen + " " + ent + " " + tippersOntology.OntologyManager.checkCoverage(sen, ent));
		return tippersOntology.OntologyManager.checkCoverage(Sen, Ent);
	}
	
	public static boolean checkAccess(String Sen, String Ent) {
		return true;
	}
	
	public static int getMoney(String Sen) {
//		if(Sen.equals("GPS1")) return 1;
//		if(Sen.equals("GPS2")) return 2;
//		if(Sen.equals("GPS3")) return 3;
//		if(Sen.equals("BB1")) return 4;
//		if(Sen.equals("BB2")) return 5;
//		if(Sen.equals("BB3")) return 6;
//		if(Sen.equals("Camera1")) return 7;
//		if(Sen.equals("Camera2")) return 8;
//		if(Sen.equals("Camera3")) return 9;
//		if(Sen.equals("Wifi1")) return 8;
//		if(Sen.equals("Wifi2")) return 7;
//		if(Sen.equals("Wifi3")) return 6;
//		if(Sen.equals("Thermometer1")) return 5;
//		if(Sen.equals("Thermometer2")) return 4;
//		if(Sen.equals("Thermometer3")) return 3;
//		
//		if(Sen.equals("AC1")) return 2;
//		if(Sen.equals("AC2")) return 1;
//		if(Sen.equals("AC3")) return 2;
//		if(Sen.equals("TV1")) return 3;
//		if(Sen.equals("TV2")) return 4;
//		if(Sen.equals("TV3")) return 5;
//		if(Sen.equals("Light1")) return 6;
//		if(Sen.equals("Light2")) return 7;
//		if(Sen.equals("Light3")) return 8;
//		
//		return 10;
		return tippersOntology.OntologyManager.getMoney(Sen);
	}
	
	public static int getTime(String Sen) {
//		if(Sen.equals("GPS1")) return 2;
//		if(Sen.equals("GPS2")) return 3;
//		if(Sen.equals("GPS3")) return 4;
//		if(Sen.equals("BB1")) return 5;
//		if(Sen.equals("BB2")) return 6;
//		if(Sen.equals("BB3")) return 7;
//		if(Sen.equals("Camera1")) return 8;
//		if(Sen.equals("Camera2")) return 9;
//		if(Sen.equals("Camera3")) return 8;
//		if(Sen.equals("Wifi1")) return 7;
//		if(Sen.equals("Wifi2")) return 6;
//		if(Sen.equals("Wifi3")) return 5;
//		if(Sen.equals("Thermometer1")) return 4;
//		if(Sen.equals("Thermometer2")) return 3;
//		if(Sen.equals("Thermometer3")) return 2;
//		
//		if(Sen.equals("AC1")) return 1;
//		if(Sen.equals("AC2")) return 2;
//		if(Sen.equals("AC3")) return 3;
//		if(Sen.equals("TV1")) return 4;
//		if(Sen.equals("TV2")) return 5;
//		if(Sen.equals("TV3")) return 6;
//		if(Sen.equals("Light1")) return 7;
//		if(Sen.equals("Light2")) return 8;
//		if(Sen.equals("Light3")) return 9;
//		
//		return 10;
		return tippersOntology.OntologyManager.getTime(Sen);
	}
}