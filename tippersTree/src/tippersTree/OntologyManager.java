package tippersTree;

import java.util.*;
import tippersOntology.OntologyManager.*;

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
	/*
	public ArrayList<Stringeger> extractSen(SR SRRequest){
		ArrayList<Stringeger> temp = new ArrayList<Stringeger>();
		return temp;
	}
	
	public ArrayList<Stringeger> extractObs(SR SRRequest){
		ArrayList<Stringeger> temp = new ArrayList<Stringeger>();
		return temp;
	}
	
	public ArrayList<Stringeger> extractEnt(SR SRRequest){
		ArrayList<Stringeger> temp = new ArrayList<Stringeger>();
		return temp;
	}
	*/
	
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
	}
	
	public String findActuator(String Prop) {
		String act = "";
		Actuator Act = new Actuator();
		for(int i = 0 ; i < Act.acts.size() ; i++) {
			if(Prop.contains(Act.acts.get(i))) {
				act = Act.acts.get(i);
			}
		}
		return act;
	}
	
	public String findAct(String Prop) {
		String actProp = "";
		if(Prop.contains("Turn on")) {
			actProp = "TurnOnProp";
		}
		return actProp;
	}
	
	public ArrayList<String> findSensor(String Obs){
		/*ArrayList<String> sensors = new ArrayList<String>();
		sensors.add("Wifi1");
		sensors.add("Wifi2");
		sensors.add("wifi3");
		return sensors;*/
		return tippersOntology.OntologyManager.findSensor(Obs);
	}
	
	public String findObs(String prop) {
		/*String obsProp = "OccObs";
		return obsProp;*/
		return null;
	}
	
	public ArrayList<String> findInput(String sensor){
		ArrayList<String> inputs = new ArrayList<String>();
		inputs.add("False");
		inputs.add("Con2Occ");
		//inputs.add("Loc2Occ");
		return inputs;
	}
	
	public boolean isVS(String sensor) {
		//if(sensor.contains("wifi")) return false;
		//else return true;
		return tippersOntology.OntologyManager.isVS(sensor);
	}
	
	public ArrayList<String> getAptDevices(String sensor){
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("Wifi1");
		temp.add("Wifi2");
		temp.add("Wifi3");
		return temp;
	}
	
	public boolean checkCoverage(String sen, String ent) {
		if(sen == "wifi1" && ent == "room4") return false;
		return true;
	}
	
	public boolean checkAccess(String sen, String ent) {
		return true;
	}
	
	public boolean hasMultiInput(String sensor) {
		return true;
	}
}