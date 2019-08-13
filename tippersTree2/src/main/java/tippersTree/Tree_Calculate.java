package tippersTree;

import java.util.*;

/*
 * 배열 하나 만들어서 MAX로 초기화한 다음 각 노드당 최소값 리턴하기 
 */

public class Tree_Calculate extends Tree {
	public final static int MAX = 987654321;
	
	public final static int Wm = 1;
	public final static int Wt = 2;
	
	public int Costs[] = {MAX,};
	public static ArrayList<Node> Leaves;
	
	public static Tree check(Tree myTree) {
		Tree newTree = new Tree();
		Leaves = findLeafNode(myTree);
		
		for(int i = 0 ; i < Leaves.size() ; i++) {
			Node nowNode = Leaves.get(i);
			SRNode nowSRNode = findSRNode(myTree, nowNode.nodeNum);
			
			calcul(nowSRNode);
		}
		
		return newTree;
	}
	
	public static void calcul(SRNode nowNode) {
		
	}
}
