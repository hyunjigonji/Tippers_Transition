package tippersTree;

import java.util.ArrayList;

public class Tree_Execute extends Tree {
	public static ArrayList<Node> Leaves;
	
	public static void executeTree(Tree feasibleTree) {
		
		
		Leaves = findLeafNode(feasibleTree);
		for(int i = 0; i <Leaves.size(); i++) {
			Node nowNode = Leaves.get(i);
			
			
		}
		
	}
}
