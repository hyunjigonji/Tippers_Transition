package tippersTree;

import java.util.ArrayList;

public class Tree_Remove {
	// check feasibility of each node
	public static void checking(SRNode nowSRNode) {
		
		String nowS = nowSRNode.values.Sensor;
		String nowO = nowSRNode.values.Observation;
		String nowE = nowSRNode.values.Entity;
		
		Node newXNode = Tree.newXNode();
		boolean connected = false;

		ArrayList<String> sensors = OntologyManager.getAptDevices(nowS);
		
		for(int i = 0 ; i < sensors.size() ; i++) {
			String nowSI = sensors.get(i);
			//System.out.println(nowSI + " " + nowE);
			if(!OntologyManager.checkAccess(nowSI, nowE) && !OntologyManager.checkCoverage(nowSI, nowE)){ // if available, add child
				//System.out.println("checking " + nowSI + " " + nowE);
				if(!connected) {
					Tree.appendChild(nowSRNode, newXNode);
					connected = true;
				}
				
				SR newSR = new SR(nowSI, nowO, nowE);
				SRNode newSRNode = Tree.newSRNode(newSR);
				newSRNode.type = types.typeDA;
				
				Tree.SRs.add(newSRNode);
				Tree.appendChild(newXNode, newSRNode);
			}
		}
		if(!connected) {
			System.out.println("remove " + nowS + " " + nowE);
			removing(nowSRNode);
		}
	}

	// remove recursively
	public static void removing(Node nowNode) {
		System.out.println(nowNode.nodeNum);
		//if(nowNode.nodeNum == 33) temp = true;
		// if x node, there will be another chance.
		if(nowNode.type == types.typeX || nowNode.type == types.typeXc) { 
			if(!nowNode.Children.isEmpty()) return;
		}

		// remove from parents' children array
		ArrayList<Node> parents = nowNode.Parents;
//		Node firPar = parents.get(0);
//		if(firPar.type == types.typePlus || firPar.type == types.typeX || firPar.type == types.typeXc) {
//			parents = firPar.Parents;
//		}
		for(int i = 0 ; i < parents.size() ; i++) {
			Node nowPar = parents.get(i);

			nowPar.Children.remove(nowNode);
			// if nowPar has empty children, remove parent
			if(nowPar.Children.isEmpty()) {
				nowPar.isLeaf = nowNode.isLeaf;
				removing(nowPar);
			}
			if(nowPar.type == types.typePlus) { // if parent is + node, remove all children
				nowPar.Children.clear();
				nowPar.isLeaf = nowNode.isLeaf;
				removing(nowPar);
			}
		}
		return;
	}
}
