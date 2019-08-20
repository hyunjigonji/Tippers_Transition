package tippersTree;

import java.util.ArrayList;

public class Tree_Generator extends Tree {
	
	public static void URgenerator0(URNode nowNode) { // generate branches for sensors from UR node
		String nowE = nowNode.values.Entity;
		String nowC = nowNode.values.Condition;
		//System.out.println("URgenerator0 " + Integer.toString(nowNode.nodeNum)+ " " + nowE + " " + nowC);

		Node ConnectNode = newXNode();
		appendChild(nowNode, ConnectNode); // connect X node with UR node
		
		ArrayList<String> sens = OntologyManager.findSensor(nowC); // Condition -> Virtual/Physical Sensors
		
		for(int i = 0 ; i < sens.size(); i++) {
			String nowSen = sens.get(i);
			//System.out.println(i + " "+ nowSen);
			SR newSR = new SR(nowSen, nowC, nowE);
			SRNode newSRNode = newSRNode(newSR); 

			if(OntologyManager.isVS(nowSen)) newSRNode.type = types.typeVSR; // decide if it is Virtual or Physical sensor
			else newSRNode.type = types.typePSR; // set node's type 
			
			SRs.add(newSRNode);

			appendChild(ConnectNode,newSRNode); // connect SR node with X node
			generator1(newSRNode, nowE); // call other function once
		}
		return;
	}
	
	public static void UAgenerator0(UANode nowNode) { // generate branches for sensors from UA node
		String nowE = nowNode.values.Entity;
		String nowP = nowNode.values.Property;
		//System.out.println(Integer.toString(nowNode.nodeNum)+ " " + nowE + " " + nowP);

		XNode XNode = newXNode();
		appendChild(nowNode, XNode); // connect X node with UA node

		String nowAct = OntologyManager.findAction(nowP); // Property -> Action
		String nowActuator = OntologyManager.findActuator(nowP); // Property -> Actuator
		//System.out.println(nowActuator);

		SR newSR = new SR(nowActuator, nowAct, nowE);
		SRNode newSRNode = newSRNode(newSR);
		newSRNode.type = types.typeAC; // set node's type as AC
		
		SRs.add(newSRNode);
		appendChild(XNode,newSRNode); // connect SR node with X node
			
		return;
	}
	
	// generate from SRNode using recursive algorithm
	public static void generator1(SRNode nowNode, String nowEnt) {
		//System.out.println("generator1  " + nowNode.values.Observation + " " + nowNode.values.Sensor);
		if(nowNode.type == types.typePSR) return; // if PSR, then finish
		ArrayList<String> obs = OntologyManager.findInput(nowNode.values.Sensor);
		// decide if it requires multiple input
		Node ConnectNode;
		if(obs.size() == 1) ConnectNode = newXNode(); // if there is only one input, it needs X node
		else ConnectNode = newPlusNode(); // if there are two or more inputs, it needs + node
		appendChild(nowNode, ConnectNode); // connect this node to now SRnode
		
		for(int i = 0 ; i < obs.size() ; i++) {
			String nowObs = obs.get(i);
			
			ArrayList<String> sens = OntologyManager.findSensor(nowObs); // Observation -> Virtual/Physical Sensor
			
			for(int j = 0 ; j < sens.size() ; j++) {
				String nowSen = sens.get(j);
				SR newSR = new SR(nowSen, nowObs, nowEnt);
				SRNode newSRNode = newSRNode(newSR);

				if(OntologyManager.isVS(nowSen)) newSRNode.type = types.typeVSR; // decide if it is Virtual or Physical sensor
				else newSRNode.type = types.typePSR; // set node's type 
				
				SRs.add(newSRNode);
				
				appendChild(ConnectNode,newSRNode); // connect SR node with X/+ node
				generator1(newSRNode, nowEnt); // call recursive
			}
		}
		
		return;
	}
}
