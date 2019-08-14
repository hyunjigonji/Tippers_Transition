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
		UA myUA = new UA("meetingroom", "Turn on AC,Turn on Light", "Occupancy>50%Connectivity");
		
		myTree = Tree_Flattening.flattening(myUA);
		//Tree_Display.displayTree(myTree);
		for(int i = 0 ; i < Tree.URij.size() ; i++) {
			//System.out.println("hello");
			URNode now = Tree.URij.get(i);
			Tree_Generator.URgenerator0(now);
		}
		//Tree_Display.displayTree(myTree);
		for(int i = 0 ; i < Tree.UAij.size() ; i++) {
			UANode now = Tree.UAij.get(i);
			Tree_Generator.UAgenerator0(now);
		}
		//Tree_Display.displayTree(myTree);
		
		// print leaf nodes
		ArrayList<Node> leaves = Tree.findLeafNode(myTree);
		for(int i = 0 ; i < leaves.size() ; i++) {
			Node now = leaves.get(i);
			SRNode now2 = Tree.findSRNode(myTree, now.nodeNum);
			Tree_Remove.check(now2);
		}
		Tree_Display.displayTree(myTree);
		
		// find feasible plan
		Tree feasibleTree = new Tree(myUA);
		feasibleTree = Tree_Calculate.check(myTree);
		Tree_Display.displayTree(feasibleTree);
		
		
		//Generator
//		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
//
//		Generator gen = new Generator();
//		double countsAVG[] = new double[2];
//		int times = 500;
//		
//		for (int i=1; i<=times; i++) {
//			int counts[] = gen.generatePlan(8,4,2,true,false);
//			
//			countsAVG[0] = countsAVG[0] + counts[0];
//			countsAVG[1] = countsAVG[1] + counts[1];
//		}
//		
//		System.out.println("number of VS: "+Math.round(countsAVG[1]/times));
//		System.out.println("number of PS: "+Math.round(countsAVG[0]/times));
	}
}

/*
class Pair<F, S> {
	private F first;
	private S second;
	
	public Pair(F f, S s) {
		first = f;
		second = s;
	}
	
	public F getFirst() { return first; }
	public S getSecond() { return second; }
	
	public void setFirst(F f) { first = f; }
	public void setSecond(S s) { second = s; }
}

class tPair<F,S,T> {
	private F first;
	private S second;
	private T third;
	
	public tPair(F f, S s, T t) {
		first = f;
		second = s;
		third = t;
	}
	
	public F getFirst() { return first; }
	public S getSecond() { return second; }
	public T getThird() { return third; }
	
	public void setFirst(F f) { first = f; }
	public void setSecond(S s) { second = s; }
	public void setThird(T t) { third = t; }	
}
*/