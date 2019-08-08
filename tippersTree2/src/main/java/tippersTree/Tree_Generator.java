package tippersTree;

import java.util.ArrayList;

public class Tree_Generator extends Tree{
	
	static Tree myTree = new Tree();
	static OntologyManager OM = new OntologyManager();
	
	public static void URgenerator0(URNode nowNode) {
		String nowE = nowNode.values.Entity;
		String nowC = nowNode.values.Condition;
		//System.out.println(Integer.toString(nowNode.nodeNum)+ " " + nowE + " " + nowC);

		XNode XNode = myTree.newXNode();
		myTree.appendChild(nowNode, XNode);

		String nowObs = OM.findObs(nowC);
		ArrayList<String> sens = OM.findSensor(nowObs);

		for(int i = 0 ; i < sens.size(); i++) {
			String nowSen = sens.get(i);
			SR newSR = new SR(nowSen, nowObs, nowE);
			SRNode newSRNode = myTree.newSRNode(newSR);

			if(OM.isVS(nowSen)) newSRNode.type = types.typeVSR;
			else newSRNode.type = types.typePSR;

			//if(count++%2 == 0) newSRNode.type = types.typePSR;
			//else newSRNode.type = types.typeVSR;
			
			SRs.add(newSRNode);

			myTree.appendChild(XNode,newSRNode);
			generator1(newSRNode, nowE); // call other function once
		}
		return;
	}
	
	public static void UAgenerator0(UANode nowNode) {
		String nowE = nowNode.values.Entity;
		String nowP = nowNode.values.Property;
		//System.out.println(Integer.toString(nowNode.nodeNum)+ " " + nowE + " " + nowC);

		XNode XNode = myTree.newXNode();
		myTree.appendChild(nowNode, XNode);

		String nowObs = OM.findAct(nowP);
		ArrayList<String> sens = OM.findActuatorInd(nowP);

		for(int i = 0 ; i < sens.size(); i++) {
			String nowSen = sens.get(i);
			SR newSR = new SR(nowSen, nowObs, nowE);
			SRNode newSRNode = myTree.newSRNode(newSR);
			newSRNode.type = types.typeAC;
			
			SRs.add(newSRNode);
			myTree.appendChild(XNode,newSRNode);
		}
		return;
	}
	
	// generate from SRNode using recursive algorithm
	public static void generator1(SRNode nowNode, String nowEnt) {
		if(nowNode.type == types.typePSR) return;

		ArrayList<String> obs = OM.findInput(nowNode.values.Sensor);

		Node ConnectNode; 
		if(OM.hasMultiInput(obs)) { // multiple input -> + node
			ConnectNode = myTree.newPlusNode(); 
			myTree.appendChild(nowNode, ConnectNode);
		} 
		else { // not multiple input -> x node
			ConnectNode = myTree.newXNode();
			myTree.appendChild(nowNode, ConnectNode);
		}

		for(int i = 1 ; i < obs.size() ; i++) {
			String nowObs = obs.get(i);
			ArrayList<String> sens = OM.findSensor(nowObs);

			for(int j = 0 ; j < sens.size() ; j++) {
				String nowSen = sens.get(j);
				SR newSR = new SR(nowSen, nowObs, nowEnt);
				SRNode newSRNode = myTree.newSRNode(newSR);

				if(OM.isVS(nowSen)) newSRNode.type = types.typeVSR;
				else newSRNode.type = types.typePSR;

				//if(count%2==0 || count++>5) newSRNode.type = types.typePSR;
				//else newSRNode.type = types.typeVSR;
				
				SRs.add(newSRNode);

				if(OM.hasMultiInput(obs)) myTree.appendChild(ConnectNode, newSRNode);
				else myTree.appendChild(ConnectNode, newSRNode);
				generator1(newSRNode, nowEnt); // recursive
			}
		}
		return;
	}
}
