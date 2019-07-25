package translation;

import java.util.*;

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
		Tree Test = new Tree();
		UA TestUA = new UA(1,2,3);
		
		Test = Test.flattening(TestUA);
		
		for(int i = 0 ; i < Test.URij.size() ; i++) {
			URNode now = Test.URij.get(i);
			
			UA UARequest = new UA(now.values.Entity, now.values.Condition);
			UANode now2 = new UANode(UARequest);
			now2.nodeNum = now.nodeNum;
			now2.Children = now.Children;
			now2.Parents = now.Parents;
			now2.isLeaf = now.isLeaf;
			now2.isRoot = now.isRoot;
			now2.type = now.type;
			
			Test.generator0(now2);
		}
		//Test.displayTree(Test);
		for(int i = 0 ; i < Test.UAij.size() ; i++) {
			UANode now = Test.UAij.get(i);
			Test.generator0(now);
		}
		Test.displayTree(Test);
		
		// print leaf nodes
		ArrayList<Node> leaves = Test.findLeafNode(Test);
		for(int i = 0 ; i < leaves.size() ; i++) {
			Node now = leaves.get(i);
		}
		
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
