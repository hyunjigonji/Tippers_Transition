package tippersTree;

import java.util.*;

public class Tree_Execute extends Tree {
	public static Stack<Node> nodes = new Stack<Node>();

	public static void executeTree(Tree feasibleTree) {

		for (int i = 0; i < URij.size(); i++) {
			URNode nowURNode = findURNode(feasibleTree, URij.get(i).nodeNum);
			
			for(int j = 0; j < nowURNode.Children.size(); j++) {
				System.out.println("힘들어  " + nowURNode.Children.get(j));
			}
			

		}
	}
}
