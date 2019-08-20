package tippersTree;

import java.util.*;

public class UA {
	public ArrayList<String> Entities;
	public ArrayList<String> Properties;
	public ArrayList<String> Conditions;
	
	public UA() { }
	
	public UA(ArrayList<String> newEntities, ArrayList<String> newProperties, ArrayList<String> newConditions) {
		Entities = extractEnt(newEntities);
		Properties = extractProp(newProperties);
		Conditions = extractCond(newConditions);
	}
	
	public UA(String newEntities, String newProperties, String newConditions) {
		Entities = extractEnt(parsing(newEntities));
		Properties = extractProp(parsing(newProperties));
		Conditions = extractCond(parsing(newConditions));
	}
	
	public static ArrayList<String> parsing(String str){
		ArrayList<String> temp = new ArrayList<String>();
		StringTokenizer ST = new StringTokenizer(str, ",");
		while(ST.hasMoreTokens()) {
			String nowToken = ST.nextToken();
			temp.add(nowToken);
		}
		return temp;
	}
	
	public static ArrayList<String> extractEnt(ArrayList<String> Entities) { // meetingroom, office -> meetingroom1,meetingroom2,office1,office2
		/*ArrayList<String> temp = new ArrayList<String>();
		temp.add("meetingroom1");
		return temp;*/
		ArrayList<String> allEntities = new ArrayList<String>();
		for(int i = 0 ; i < Entities.size() ; i++) {
			String nowEnt = Entities.get(i);
			allEntities.addAll(tippersOntology.OntologyManager.extractEnt(nowEnt));
		}
		return allEntities;
	} 

	public static ArrayList<String> extractProp(ArrayList<String> Properties) { // turn on AC, turn on Light -> turn on AC, turn on Light
		/*ArrayList<String> temp = new ArrayList<String>();
		String Prop = UARequest.Properties;
		
		StringTokenizer ST = new StringTokenizer(Prop, ",");
		while(ST.hasMoreTokens()) {
			String nowToken = ST.nextToken();
			temp.add(nowToken);
		}*/
		return Properties; 
	}
	
	public static ArrayList<String> extractCond(ArrayList<String> Conditions) { // Occupancy>50%Capacity -> Occupancy, Capacity
		ArrayList<String> temp = new ArrayList<String>();
		String Cond = Conditions;
		
		Condition conditions = new Condition();
		for(int i = 0 ; i < conditions.conds.size() ; i++) {
			String nowCon = conditions.conds.get(i);
			if(Cond.contains(nowCon)) {
				temp.add(nowCon);
			}
		}
		return temp; 
	}
}
