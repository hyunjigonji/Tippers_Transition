package tippersTree;

import java.util.*;

public class Tree_Calculate extends Tree {
	public final static int MAX = 987654321; 
	
	public static ArrayList<Node> Leaves; // contains every leaf nodes
	public static ArrayList<Integer> Selected = new ArrayList<Integer>(); // contains all leaf node number to remain from tree
	
	public static Tree check(Tree myTree) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i = 0 ; i < URij.size() ; i++) {
			result = calcul(myTree, URij.get(i)); // calcul cost of each nodes
			System.out.println("UR " + URij.get(i).nodeNum + " cost " + result.get(0));
			result.remove(0); // result array[0] is cost, and the rest contains leaf node number of feasible branch
			Selected.addAll(result);
		}
		for(int i = 0 ; i < UAij.size() ; i++) {
			result = calcul(myTree, UAij.get(i)); // calcul cost of each nodes
			System.out.println("UA " + UAij.get(i).nodeNum + " cost " + result.get(0));
			result.remove(0); // result array[0] is cost, and the rest contains leaf node number of feasible branch
			Selected.addAll(result);
		}
		
		Leaves = findLeafNode(myTree);
		for(int i = 0 ; i < Leaves.size() ; i++) {
			Node nowNode = Leaves.get(i);
			if(!Selected.contains(nowNode.nodeNum)) remove(nowNode); // remove not feasible node from tree
		}
		
		return myTree;
	}
	
	public static ArrayList<Integer> calcul(Tree myTree, Node nowNode) {
		//System.out.println("calcul " + nowNode.nodeNum);
		if(nowNode.type == types.typeDA) { // if DA, it is final node, so get cost and return.
			SRNode nowDANode = findSRNode(myTree, nowNode.nodeNum);
			int nowCost = OntologyManager.getCost(nowDANode.values.Sensor);
			
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(nowCost);
			temp.add(nowNode.nodeNum);
			
			return temp;
		}
		
		if(nowNode.type == types.typeVSR) { // if VSR, it needs to add its cost.
			SRNode nowVSRNode = findSRNode(myTree, nowNode.nodeNum);
			int nowCost = OntologyManager.getCost(nowVSRNode.values.Sensor);
			
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp = calcul(myTree, nowNode.Children.get(0));
			
			int tempCost = temp.get(0);
			temp.remove(0);
			temp.add(0, nowCost+tempCost);
			
			return temp;
		}
		
		if(nowNode.type == types.typePSR || nowNode.type == types.typeAC) { // if PSR, or AC, it is in the middle, so skip.
			return calcul(myTree, nowNode.Children.get(0));
		}
		
		if(nowNode.type == types.typeX) { // if x node, it needs to pick one branch which has the cheapest cost.
			int minCost = MAX;
			ArrayList<Integer> minNodes = new ArrayList<Integer>();
			
			for(int i = 0 ; i < nowNode.Children.size() ; i++) {
				ArrayList<Integer> nowNodes = calcul(myTree, nowNode.Children.get(i));
				//System.out.println("minNodes " + minNodes + " nowNoes " + nowNodes);
				int nowCost = nowNodes.get(0);
				nowNodes.remove(0);
				
				if(nowCost < minCost) {
					minCost = nowCost;
					minNodes.clear();
					minNodes.add(minCost);
					minNodes.addAll(nowNodes);
				}
			}
			return minNodes;
		}
		
		if(nowNode.type == types.typePlus) { // if + node, it needs to combine all branches.
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp = calcul(myTree, nowNode.Children.get(0));
			
			for(int i = 1 ; i < nowNode.Children.size() ; i++) {
				ArrayList<Integer> nowNodes = calcul(myTree, nowNode.Children.get(i));
				int nowCost = nowNodes.get(0);
				nowNodes.remove(0);
				
				ArrayList<Integer> originNodes = temp;
				int originCost = temp.get(0);
				originNodes.remove(0);
				
				int newCost = nowCost + originCost;
				ArrayList<Integer> newNodes = new ArrayList<Integer>();
				newNodes.add(newCost);
				newNodes.addAll(nowNodes);
				newNodes.addAll(originNodes);
				
				temp = newNodes;
			}
			return temp;
		}
		
		if(nowNode.type == types.typeUR) { // if UR node, index 0 = xc, so return index 1.
			return calcul(myTree, nowNode.Children.get(1));
		}
		if(nowNode.type == types.typeUA) { // if UA node, return index 0.
			return calcul(myTree, nowNode.Children.get(0));
		}
		return null;
	}
	
	public static void remove(Node nowNode) { // similar to previous remove algorithm
		//System.out.println(nowNode.nodeNum);
		if(nowNode.type == types.typeUR || nowNode.type == types.typeUA) return; // if UR or UA, it is final node, so return.
		
		if(nowNode.type == types.typeX || nowNode.type == types.typeXc) { // if x node, there will be another chance.
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
			if(nowPar.type == types.typePlus) { 
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
