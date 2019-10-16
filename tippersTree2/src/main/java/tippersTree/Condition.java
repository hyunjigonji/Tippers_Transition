package tippersTree;

import java.util.*;

public class Condition {
	public ArrayList<String> conds = new ArrayList<String>(Arrays.asList("Occupancy","Capacity","Location","Connectivity")); // contains condition property
	//public ArrayList<String> conjunction = new ArrayList<String>(Arrays.asList("if","when","where")); // contains conjunction like if, when, where
	//public ArrayList<String> connect = new ArrayList<String>(Arrays.asList("and","or")); // contains connective like and, or -> AND/OR
	public ArrayList<String> compare1 = new ArrayList<String>(Arrays.asList("is greater than", "is higher than", "is bigger than", "is better than", "is above", "is over")); // contains compare words which represents bigger left -> >
	public ArrayList<String> compare2 = new ArrayList<String>(Arrays.asList("is more than"));
	public ArrayList<String> compare3 = new ArrayList<String>(Arrays.asList("is smaller than", "is lower than", "is under", "is below")); // contains compare words which represents bigger right -> <
	public ArrayList<String> compare4 = new ArrayList<String>(Arrays.asList("is less than"));
	public ArrayList<String> compare5 = new ArrayList<String>(Arrays.asList("is same as", "is equal to")); // contains compare words which represents both same -> =
	//public ArrayList<String> rate = new ArrayList<String>(Arrays.asList("% of", "percent of", "half of", "quarter of")); // contains rate words -> N%
	
	String test = convertingCond("Occupancy is more than half of Capacity and Connectivity is lower than 10");
	
	public String convertingCond(String Cond) {
		//Cond = Cond.toLowerCase();
		String result = "";
		
		while(Cond.contains("and") || Cond.contains("or")) {
			int index = Cond.contains("and") ? Cond.indexOf("and") : Cond.indexOf("or");
			if(index == 0) index = Cond.length();
			String nowCond = Cond.substring(0, index);
			//System.out.println("now Cond111 = " + nowCond);
			if(nowCond.contains("and")) nowCond = nowCond.replaceAll("and", "AND");
			if(nowCond.contains("or")) nowCond = nowCond.replaceAll("or", "OR");
			//System.out.println("now Cond = " + nowCond);
			
			for(int i = 0 ; i < compare1.size() ; i++) { // check if it contains greater or higher
				if(nowCond.contains(compare1.get(i))) {
					nowCond = nowCond.replace(compare1.get(i), ">");
					break;
				}
			}
			for(int i = 0 ; i < compare2.size() ; i++) { // check if it contains greater or higher
				if(nowCond.contains(compare2.get(i))) {
					nowCond = nowCond.replace(compare2.get(i), ">=");
					break;
				}
			}
			for(int i = 0 ; i < compare3.size() ; i++) { // check if it contains smaller or lower
				if(nowCond.contains(compare3.get(i))) {
					nowCond = nowCond.replace(compare3.get(i), "<");
					break;
				}
			}
			for(int i = 0 ; i < compare4.size() ; i++) { // check if it contains greater or higher
				if(nowCond.contains(compare4.get(i))) {
					nowCond = nowCond.replace(compare4.get(i), "<=");
					break;
				}
			}
			for(int i = 0 ; i < compare5.size() ; i++) { // check if it contains same
				if(nowCond.contains(compare5.get(i))) {
					nowCond = nowCond.replace(compare5.get(i), "=");
					break;
				}
			}
			
			// convert rate to number
			int ind = 0; // index which points to word representing rate
			if(nowCond.contains("half of")) {
				nowCond = nowCond.replace("half of", "0.5 *");
			}
			else if(nowCond.contains("quarter of")) {
				nowCond = nowCond.replace("quarter of", "0.25 *");
			}
			else if(nowCond.contains("% of") || nowCond.contains("percent of")) {
				ind = nowCond.contains("% of")? nowCond.indexOf("% of") : nowCond.indexOf("percent of"); // value is index of % or p
				String left = nowCond.substring(0, ind);
				left = left.replaceAll("[^0-9]",""); // erase all letters except numbers
				//System.out.println(left);
				int leftNum = Integer.parseInt(left); 
				double realNum = 0.01*leftNum; // number of rate
				
				nowCond = nowCond.replaceAll(left, Double.toString(realNum)+" *");
				nowCond = nowCond.replace(nowCond.contains("% of")?"% of":"percent of", "");
			}
			result += nowCond;
			//System.out.println("result = " + result);
			
			Cond = Cond.substring(index, Cond.length());
			//System.out.println("Cond = " + Cond);
		}
		
		return result;
	}
}