package tippersTree;

import java.util.*;

import tippersOntology.OntologyManager;

/*
 Tree level
 
 1. UA = <E, P, C>
	UAi = <ei, P, C>
 2. UR = <E, C>
 	URij = <ei, Cj>
 3. UAj = <ei, p, c> <- computed c
 */
public class Main {

	public static void main(String args[]) {
		OntologyManager.startOntologyManager();

		Tree myTree = new Tree();
		UA myUA = new UA("meetingroom", "Turn on AC,Turn on Light", "Occupancy>0.5*Connectivity");


		myTree = Tree_Flattening.flattening(myUA);
		// Tree_Display.displayTree(myTree);
		for (int i = 0; i < Tree.URij.size(); i++) {
			// System.out.println("hello");
			URNode now = Tree.URij.get(i);
			Tree_Generator.URgenerator0(now);
		}
		// Tree_Display.displayTree(myTree);
		for (int i = 0; i < Tree.UAij.size(); i++) {
			UANode now = Tree.UAij.get(i);
			Tree_Generator.UAgenerator0(now);
		}
		// Tree_Display.displayTree(myTree);

		// print leaf nodes
		ArrayList<Node> leaves = Tree.findLeafNode(myTree.Root);
		for (int i = 0; i < leaves.size(); i++) {
			Node now = leaves.get(i);
			SRNode now2 = Tree.findSRNode(myTree, now.nodeNum);
			Tree_Remove.check(now2);
		}
		// Tree_Display.displayTree(myTree);
		for (int i = 0; i < Tree.URij.size(); i++) {
			ArrayList<Node> URLeaves = Tree.findLeafNode(Tree.URij.get(i));
			System.out.println(Tree.URij.get(i).nodeNum);
			for (int j = 0; j < URLeaves.size(); j++) {
				System.out.println(URLeaves.get(j).nodeNum);
			}
		}

		// find feasible plan
		Tree feasibleTree = new Tree(myUA);
		feasibleTree = Tree_Calculate.check(myTree);
		Tree_Display.displayTree(feasibleTree);

		// execute sensor data
		for (int i = 0; i < Tree.URij.size(); i++) {
			ArrayList<Node> URLeaf = Tree.findLeafNode(Tree.URij.get(i));
			for (int j = 0; j < URLeaf.size(); j++) {
				SRNode now = Tree.findSRNode(feasibleTree, URLeaf.get(j).nodeNum);
				Tree_Execute.executeTree(feasibleTree, now);
			}
		}

		// if comparison is True
		if (Condition.calculCond(myUA.Condition)) {
			// create statement of actuator
			for (int i = 0; i < Tree.UAij.size(); i++) {
				ArrayList<Node> UALeaf = Tree.findLeafNode(Tree.UAij.get(i));
				for (int j = 0; j < UALeaf.size(); j++) {
					SRNode now = Tree.findSRNode(feasibleTree, UALeaf.get(j).nodeNum);
					Tree_Execute.executeTree(feasibleTree, now);
				}
			}
		}
	}
}