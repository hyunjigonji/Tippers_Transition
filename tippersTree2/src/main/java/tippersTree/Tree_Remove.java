package tippersTree;

import java.util.ArrayList;

public class Tree_Remove extends Tree{
	// check feasibility of each node
	public static void check(SRNode nowSRNode) {
		
		String nowS = nowSRNode.values.Sensor;
		String nowO = nowSRNode.values.Observation;
		String nowE = nowSRNode.values.Entity;
		
		Node newXNode = newXNode();
		boolean connected = false;

		ArrayList<String> sensors = OntologyManager.getAptDevices(nowS);
		
		for(int i = 0 ; i < sensors.size() ; i++) {
			String nowSI = sensors.get(i);
			//System.out.println(nowSI + " " + nowE);
			if(OntologyManager.checkAccess(nowSI, nowE) && OntologyManager.checkCoverage(nowSI, nowE)){ // if available, add child
				//System.out.println("checking " + nowSI + " " + nowE);
				if(!connected) {
					appendChild(nowSRNode, newXNode);
					connected = true;
				}
				
				SR newSR = new SR(nowSI, nowO, nowE);
				SRNode newSRNode = newSRNode(newSR);
				newSRNode.type = types.typeDA;
				
				SRs.add(newSRNode);
				appendChild(newXNode, newSRNode);
			}
		}
		if(!connected) {
			System.out.println("remove " + nowS + " " + nowE);
			remove(nowSRNode);
		}
	}

	// remove recursively
	public static void remove(Node nowNode) {
		//System.out.println(nowNode.nodeNum);
		
		// if x node, there will be another chance.
		if(nowNode.type == types.typeX || nowNode.type == types.typeXc) { 
			if(!nowNode.Children.isEmpty()) return;
		}

		// remove nowNode from children' parents array
		ArrayList<Node> children = nowNode.Children;
		if(!children.isEmpty()) {
			for(int i = 0 ; i < children.size() ; i++) {
				Node nowChild = children.get(i);
				nowChild.Parents.remove(nowNode);
			}
		}
		
		// remove nowNode from parents' children array
		ArrayList<Node> parents = nowNode.Parents;
		for(int i = 0 ; i < parents.size() ; i++) {
			Node nowPar = parents.get(i);
			//System.out.println("nowPar " + nowPar.nodeNum);
			nowPar.Children.remove(nowNode);
			
			// if parent is + node, remove all children
			if(nowPar.type == types.typePlus || nowPar.type == types.typeUR) { 
				//System.out.println("nowPar " + nowPar.nodeNum);
				nowPar.Children.clear();
				nowPar.isLeaf = nowNode.isLeaf;
			}
			
			// if parent has empty children, remove parent
			if(nowPar.Children.isEmpty()) {
				//System.out.println("nowPar " + nowPar.nodeNum);
				nowPar.isLeaf = nowNode.isLeaf;
				remove(nowPar);
			}
		}
		return;
	}
}