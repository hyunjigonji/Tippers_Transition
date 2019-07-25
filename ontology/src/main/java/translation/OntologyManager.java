package translation;

import java.util.*;

public class OntologyManager {
	/*
	 *  Modify it!!! Extract values from Domain Model (Ontology) !!!
	 */
	public ArrayList<Integer> extractEnt(UA UARequest) { // extract Entity from UA, M
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(11);
		temp.add(12);
		temp.add(13);
		return temp; 
	} 

	public ArrayList<Integer> extractProp(UA UARequest) { // extract Property from UA, M
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(21);
		temp.add(22);
		temp.add(23);
		return temp; 
	} 
	
	public ArrayList<Integer> extractCond(UA UARequest) { // extract Condition from UA, M
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(31);
		temp.add(32);
		temp.add(33);
		return temp; 
	} 
	/*
	public ArrayList<Integer> extractSen(SR SRRequest){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		return temp;
	}
	
	public ArrayList<Integer> extractObs(SR SRRequest){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		return temp;
	}
	
	public ArrayList<Integer> extractEnt(SR SRRequest){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		return temp;
	}
	*/
	public ArrayList<Integer> findSensor(int Obs){
		ArrayList<Integer> sensors = new ArrayList<Integer>();
		sensors.add(111);
		sensors.add(222);
		sensors.add(333);
		return sensors;
	}
	
	public int findObs(int prop) {
		int obsProp = 444;
		return obsProp;
	}
	
	public ArrayList<Integer> findInput(int sensor){
		ArrayList<Integer> inputs = new ArrayList<Integer>();
		inputs.add(555);
		inputs.add(666);
		inputs.add(777);
		return inputs;
	}
}
