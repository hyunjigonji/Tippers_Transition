package tippersTree;

import java.util.ArrayList;

public class Action {
	public ArrayList<String> actions;
	
	public Action() {
		actions = new ArrayList<String>();
		
		// TV, Light
		actions.add("Turn on");
		actions.add("Turn off");
		
		// Volume
		actions.add("Turn up");
		actions.add("Turn down");
		
		// Temperature
		actions.add("Raise up");
		actions.add("Reduce");
	}
}