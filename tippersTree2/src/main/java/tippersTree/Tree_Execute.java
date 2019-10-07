package tippersTree;

import java.util.*;

public class Tree_Execute extends Tree {

	public static void executeTree(Tree feasibleTree) {

		for (int i = 0; i < URij.size(); i++) {
			URNode nowURNode = findURNode(feasibleTree, URij.get(i).nodeNum); // get URNode
			Stack<Node> st = reverseNodes(nowURNode);

			Statement nowState = new Statement();
			ArrayList<Statement> temp = new ArrayList<Statement>();

			while (!st.isEmpty()) {
				Node now = st.pop();
				int nowNum = now.nodeNum;

				SRNode now2 = findSRNode(feasibleTree, nowNum);
				nowState = new Statement(now2.values.Sensor, now2.values.Observation, now2.values.Entity, temp);
				
				System.out.println(now2.type + " <" + nowState.Sensor + " " + nowState.Observation + " "
						+ nowState.Entity + " " + nowState.Former + ">");
//				System.out.println(now2.type + " <" + now2.nodeNum + "  " + now2.values.Entity + "  "
//						+ now2.values.Observation + "  " + now2.values.Sensor + ">");
				System.out.println(nowState);
				
				temp.add(nowState);
			}
		}
	}

	public static Stack<Node> reverseNodes(URNode nowURNode) {
		Stack<Node> reverse = new Stack<Node>();
		Queue<Node> temp = new LinkedList<Node>();

		// reverse.add(nowURNode); // add URNode to stack

		for (int j = 0; j < nowURNode.Children.size(); j++) {
			if (nowURNode.Children.get(j).type != types.typeXc)
				temp.add(nowURNode.Children.get(j));

			while (!temp.isEmpty()) {
				Node nowNode = temp.remove();
				temp.addAll(nowNode.Children);

				if (nowNode.type != types.typeX & nowNode.type != types.typePlus)
					reverse.add(nowNode);
			}
		}
		// System.out.println("nodes" + reverse); // print nodeNum
		return reverse;
	}
}
