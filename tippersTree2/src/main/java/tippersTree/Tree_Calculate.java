package tippersTree;

import java.util.*;

public class Tree_Calculate extends Tree {
	public final static int MAX = 987654321;
	
	public final static int Wm = 1;
	public final static int Wt = 2;
	
	public static ArrayList<Node> Leaves;
	
	public static int Costs[] = new int[Tree.totalNum+1];
	public static ArrayList<Integer> LeafNode[] = new ArrayList[Tree.totalNum+1];
	public static ArrayList<Integer> Selected = new ArrayList<Integer>();
	
	public static Tree check(Tree myTree) {
		for(int i = 1 ; i <= Tree.totalNum ; i++) { // initialize the array with MAX value
			Costs[i] = MAX;
			LeafNode[i] = new ArrayList<Integer>();
		}
		
		//Tree newTree = new Tree(); // How to generate newTree !!!
		Leaves = findLeafNode(myTree);
		
		for(int i = 0 ; i < Leaves.size() ; i++) {
			Node nowNode = Leaves.get(i);
			calcul(myTree, nowNode, MAX, nowNode.nodeNum, new ArrayList<Integer>());
		}
		
		for(int i = 0 ; i < URij.size() ; i++) {
			for(int j = 0 ; j < LeafNode[URij.get(i).nodeNum].size() ; j++) {
				Selected.add(LeafNode[URij.get(i).nodeNum].get(j));
			}
		}
		for(int i = 0 ; i < UAij.size() ; i++) {
			for(int j = 0 ; j < LeafNode[UAij.get(i).nodeNum].size() ; j++) {
				Selected.add(LeafNode[UAij.get(i).nodeNum].get(j));
			}
		}
		System.out.println(Selected);
		//Selected.remove(Selected.indexOf(43));
		//Selected.add(47);
		//Selected.add(49);
		
		for(int i = 0 ; i < Leaves.size() ; i++) {
			Node nowNode = Leaves.get(i);
			if(!Selected.contains(nowNode.nodeNum)) remove(nowNode);
		}
		
		return myTree;
	}
	
	public static void calcul(Tree myTree, Node nowNode, int nowCost, int leafNum, ArrayList<Integer> leafNodeNum) {
		//System.out.println("now node : " + nowNode.nodeNum + " now cost: " + nowCost + " now leaf : " + leafNodeNum);
		if(nowNode.type == types.typeDA) { // if DA, it is leaf and get cost.
			SRNode nowDANode = findSRNode(myTree, nowNode.nodeNum);
			int nowMoney = OntologyManager.getMoney(nowDANode.values.Sensor);
			int nowTime = OntologyManager.getTime(nowDANode.values.Sensor);
			
			nowCost = nowMoney*Wm + nowTime*Wt;
			
			Costs[nowNode.nodeNum] = nowCost;
			
			leafNodeNum.add(leafNum);
			LeafNode[nowNode.nodeNum] = leafNodeNum;
			//System.out.println("calcul: now num " + nowNode.nodeNum + ", nowCost = " + nowCost);
			calcul(myTree, nowNode.Parents.get(0), nowCost, leafNum, leafNodeNum);
		}
		
		if(nowNode.type == types.typePSR || nowNode.type == types.typeVSR || nowNode.type == types.typeAC) { // if PSR or VSR, it is just middle of way, so update and continue.
			Costs[nowNode.nodeNum] = nowCost;
			LeafNode[nowNode.nodeNum] = leafNodeNum;
			//System.out.println("calcul: now num " + nowNode.nodeNum + ", nowCost = " + nowCost);
			calcul(myTree, nowNode.Parents.get(0), nowCost, leafNum, leafNodeNum);
		}
		
		if(nowNode.type == types.typeX) {
			int xCost = Costs[nowNode.nodeNum];
			if(nowCost < xCost) {
				Costs[nowNode.nodeNum] = nowCost;
				LeafNode[nowNode.nodeNum] = leafNodeNum;
			}
			else {
				nowCost = xCost;
			}
			//System.out.println("calcul: now num " + nowNode.nodeNum + ", nowCost = " + nowCost);
			calcul(myTree, nowNode.Parents.get(0), nowCost, leafNum, leafNodeNum);
		}
		
		if(nowNode.type == types.typePlus) { // if + node, it has to integrate each branch.
			int plusCost = Costs[nowNode.nodeNum];
			
			if(plusCost >= MAX) {
				Costs[nowNode.nodeNum] = nowCost;
				LeafNode[nowNode.nodeNum] = leafNodeNum;
			}
			else {
				Costs[nowNode.nodeNum] = plusCost + nowCost;
				nowCost = plusCost + nowCost;
				LeafNode[nowNode.nodeNum].add(leafNum);
				leafNodeNum = LeafNode[nowNode.nodeNum];
			}
			//System.out.println("calcul: now num " + nowNode.nodeNum + ", nowCost = " + nowCost);
			calcul(myTree, nowNode.Parents.get(0), nowCost, leafNum, leafNodeNum);
		}
		
		if(nowNode.type == types.typeUR || nowNode.type == types.typeUA) { // if UR node, it is final node of this recursive function, so it has to find the cheapest one.
			int URCost = Costs[nowNode.nodeNum];
			if(nowCost < URCost) {
				Costs[nowNode.nodeNum] = nowCost;
				LeafNode[nowNode.nodeNum] = leafNodeNum;
			}
			else {
				nowCost = URCost;
			}
			//System.out.println("feasible UR = " + nowNode.nodeNum + " " + LeafNode[nowNode.nodeNum]);
			//System.out.println("calcul: now num " + nowNode.nodeNum + ", nowCost = " + nowCost);
			return;
		}
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
