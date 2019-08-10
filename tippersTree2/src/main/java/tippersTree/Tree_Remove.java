package tippersTree;

import java.util.ArrayList;

public class Tree_Remove {
	
	static Tree myTree = new Tree();
	static OntologyManager OM = new OntologyManager();
	
	// check feasibility of each node
	public static void checking(SRNode nowSRNode) {
		
		String nowS = nowSRNode.values.Sensor;
		String nowO = nowSRNode.values.Observation;
		String nowE = nowSRNode.values.Entity;

		ArrayList<String> sensors = OM.getAptDevices(nowS);
		
		boolean flag = false;
		for(int i = 0 ; i < sensors.size() ; i++) {
			String nowSI = sensors.get(i);
			//System.out.println(nowSI + " " + nowE);

			if(!OM.checkAccess(nowSI, nowE)) { // always return true
				removing(nowSRNode);
			}
			else if(!OM.checkCoverage(nowSI, nowE)) { // I don't know
				removing(nowSRNode);
			}
			// if available, add child
			else {
				//flag = true;
			}
		}
		if(flag) {
			// create a x node
			Node newXNode = myTree.newXNode();
			myTree.appendChild(nowSRNode, newXNode);

			// create a child node
			for(int i = 0 ; i < sensors.size() ; i++) {
				String nowSI = sensors.get(i);
				SR newSR = new SR(nowSI, nowO, nowE);
				Node newSRNode = myTree.newSRNode(newSR);

				myTree.appendChild(newXNode, newSRNode);
			}
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
