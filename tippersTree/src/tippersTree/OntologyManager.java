package tippersTree;

import java.util.*;

public class OntologyManager {
	/*
	 *  Modify it!!! Extract values from Domain Model (Ontology) !!!
	 */
	public ArrayList<String> extractEnt(UA UARequest) { // extract Entity from UA, M
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("room1");
		temp.add("room2");
		temp.add("room3");
		temp.add("room4");
		return temp; 
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
	public ArrayList<String> findSensor(String Obs){
		ArrayList<String> sensors = new ArrayList<String>();
		sensors.add("wifi1");
		sensors.add("wifi2");
		sensors.add("wifi3");
		return sensors;
	}
	
	public String findObs(String prop) {
		String obsProp = "OccObs";
		return obsProp;
	}
	
	public ArrayList<String> findInput(String sensor){
		ArrayList<String> inputs = new ArrayList<String>();
		inputs.add("False");
		inputs.add("Con2Occ");
		inputs.add("Loc2Occ");
		return inputs;
	}
	/*
	public boolean isVS(String sensor) {
		if(sensor%2 == 0) return true;
		else return false;
	}*/
	
	public ArrayList<String> getAptDevices(String sensor){
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("wifi1");
		temp.add("wifi2");
		temp.add("wifi3");
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
