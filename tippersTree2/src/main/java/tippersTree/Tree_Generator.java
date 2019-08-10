package tippersTree;

import java.util.ArrayList;

public class Tree_Generator extends Tree{
	
	static Tree myTree = new Tree();
	static OntologyManager OM = new OntologyManager();
	
	public static void URgenerator0(URNode nowNode) {
		String nowE = nowNode.values.Entity;
		String nowC = nowNode.values.Condition;
		System.out.println("URgenerator0 " + Integer.toString(nowNode.nodeNum)+ " " + nowE + " " + nowC);

		Node ConnectNode;
		ArrayList<String> sens = OM.findSensor(nowC);
		
		if(OM.hasMultiInput(sens)) { // multiple input -> + node
			ConnectNode = myTree.newPlusNode(); 
			myTree.appendChild(nowNode, ConnectNode);
		} 
		else { // not multiple input -> x node
			ConnectNode = myTree.newXNode();
			myTree.appendChild(nowNode, ConnectNode);
		}
		
		for(int i = 1 ; i < sens.size(); i++) {
			String nowSen = sens.get(i);
			//System.out.println(i + " "+ nowSen);
			SR newSR = new SR(nowSen, nowC, nowE);
			SRNode newSRNode = myTree.newSRNode(newSR);

			if(OM.isVS(nowSen)) newSRNode.type = types.typeVSR;
			else newSRNode.type = types.typePSR;
			
			SRs.add(newSRNode);

			myTree.appendChild(ConnectNode,newSRNode);
			generator1(newSRNode, nowE); // call other function once
		}
		return;
	}
	
	public static void UAgenerator0(UANode nowNode) {
		String nowE = nowNode.values.Entity;
		String nowP = nowNode.values.Property;
		//System.out.println(Integer.toString(nowNode.nodeNum)+ " " + nowE + " " + nowP);

		XNode XNode = myTree.newXNode();
		myTree.appendChild(nowNode, XNode);

		String nowObs = OM.findAction(nowP);
		String nowActuator = OM.findActuator(nowP);
		System.out.println(nowActuator);

		SR newSR = new SR(nowActuator, nowObs, nowE);
		SRNode newSRNode = myTree.newSRNode(newSR);
		
		newSRNode.type = types.typeAC;
		
		SRs.add(newSRNode);
		myTree.appendChild(XNode,newSRNode);
			
		return;
	}
	
	// generate from SRNode using recursive algorithm
	public static void generator1(SRNode nowNode, String nowEnt) {
		System.out.println("generator1  " + nowNode.values.Observation + " " + nowNode.values.Sensor);
		if(nowNode.type == types.typePSR) return;
		String nowObs = OM.findInput(nowNode.values.Sensor);
		// decide if it requires multiple input
		Node ConnectNode; 
		ArrayList<String> sens = OM.findSensor(nowObs);
		
		if(OM.hasMultiInput(sens)) { // multiple input -> + node
			ConnectNode = myTree.newPlusNode(); 
			myTree.appendChild(nowNode, ConnectNode);
		} 
		else { // not multiple input -> x node
			ConnectNode = myTree.newXNode();
			myTree.appendChild(nowNode, ConnectNode);
		}

		for(int j = 1 ; j < sens.size() ; j++) {
			String nowSen = sens.get(j);
			SR newSR = new SR(nowSen, nowObs, nowEnt);
			SRNode newSRNode = myTree.newSRNode(newSR);

			if(OM.isVS(nowSen)) newSRNode.type = types.typeVSR;
			else newSRNode.type = types.typePSR;
			
			SRs.add(newSRNode);
			myTree.appendChild(ConnectNode,newSRNode);
			generator1(newSRNode, nowEnt); // recursive
		}
		
		return;
	}
}
