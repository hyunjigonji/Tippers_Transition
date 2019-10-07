package tippersTree;

import java.util.*;

public class Tree_Execute extends Tree {

	public static void executeTree(Tree feasibleTree) {

		for (int i = 0; i < URij.size(); i++) {
			URNode nowURNode = findURNode(feasibleTree, URij.get(i).nodeNum); // get URNode
			Stack<Node> st = reverseNodes(nowURNode);

			while (!st.isEmpty()) {
				Node now = st.pop();
				int nowNum = now.nodeNum;

				if (now.type == types.typeX || now.type == types.typePlus) {
					System.out.println(now.type);
				} else if (now.type == types.typeDA) {
					SRNode now2 = findSRNode(feasibleTree, nowNum);
					System.out.println(now2.type + " <" + now2.nodeNum + "  " + now2.values.Entity + "  "
							+ now2.values.Observation + "  " + now2.values.Sensor + ">");
				} else if (now.type == types.typePSR || now.type == types.typeVSR) {
					SRNode now2 = findSRNode(feasibleTree, nowNum);
					System.out.println(now2.type + " <" + now2.nodeNum + "  " + now2.values.Entity + "  "
							+ now2.values.Observation + "  " + now2.values.Sensor + ">");
				} else if (now.type == types.typeUR) {
					URNode now2 = findURNode(feasibleTree, nowNum);
					System.out.println(now2.type + " <" + now2.nodeNum + "  " + now2.values.Entity + "  "
							+ now2.values.Condition + ">");
				}
			}
		}
	}

	public static Stack<Node> reverseNodes(URNode nowURNode) {

		Stack<Node> reverse = new Stack<Node>();
		Queue<Node> temp = new LinkedList<Node>();

		reverse.add(nowURNode); // add URNode to stack

		for (int j = 0; j < nowURNode.Children.size(); j++) {
			if (nowURNode.Children.get(j).type != types.typeXc)
				temp.add(nowURNode.Children.get(j));

			while (!temp.isEmpty()) {
				Node nowNode = temp.remove();

				temp.addAll(nowNode.Children);
				reverse.add(nowNode);
			}
		}
		System.out.println("nodes" + reverse); // print nodeNum

		return reverse;
	}
}
