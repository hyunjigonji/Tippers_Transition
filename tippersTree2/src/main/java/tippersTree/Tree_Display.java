package tippersTree;

import java.util.LinkedList;
import java.util.Queue;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Tree_Display extends Tree {
	// to display tree structure using BFS algorithm
	public static void displayTree(Tree TreeUA) {
		Graph graph = new SingleGraph("test");

		boolean[] visit = new boolean[10000];
		Queue<Node> qu = new LinkedList<Node>();

		Node temp = TreeUA.Root;
		qu.add(temp);
		visit[temp.nodeNum] = true;
		Node parent = temp;
		//graph.addNode("Root");

		while(!qu.isEmpty()) { // BFS using Queue interface
			Node now = qu.poll();

			String str = "";
			if(now.type == types.typeUA) {
				UANode now2 = findUANode(TreeUA, now.nodeNum);
				str = "UA"+ now2.nodeNum + "<" + now2.values.Entity + "," + now2.values.Property + "," + now2.values.Condition + ">";
			}
			else if(now.type == types.typeUR) {
				URNode now2 = findURNode(TreeUA, now.nodeNum);
				str = "UR" + now2.nodeNum + "<" + now2.values.Entity + "," + now2.values.Condition + ">";
			}
			else if(now.type == types.typePlus) str = "+" + now.nodeNum;
			else if(now.type == types.typeX) str = "x" + now.nodeNum;
			else if(now.type == types.typeXc) str = "Xc" + now.nodeNum;
			else if(now.type == types.typePSR) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "PSR" + now2.nodeNum + "<" + now2.values.Sensor + "," + now2.values.Observation + "," + now2.values.Entity + ">";
				//System.out.println(str);
			}
			else if(now.type == types.typeVSR) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "VSR" + now2.nodeNum + "<" + now2.values.Sensor + "," + now2.values.Observation + "," + now2.values.Entity + ">";
				//System.out.println(str);
			}
			else if(now.type == types.typeAC) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "AC" + now2.nodeNum + "<" + now2.values.Sensor + "," + now2.values.Observation + "," + now2.values.Entity + ">";
			}
			else if(now.type == types.typeDA) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "DA" + now2.nodeNum + "<" + now2.values.Sensor + "," + now2.values.Observation + "," + now2.values.Entity + ">";
			}
			if(now.isRoot) str = "Root";

			graph.addNode(str);

			String par = "";
			for(int i = 0 ; i < now.Parents.size() ; i++) {
				parent = now.Parents.get(i);
				//System.out.prStringln("now = " + Integer.toString(now.nodeNum) + " par = " + Integer.toString(parent.nodeNum));

				if(parent.type == types.typeUA) {
					UANode parent2 = findUANode(TreeUA, parent.nodeNum);
					par = "UA" + parent2.nodeNum + "<" + parent2.values.Entity + "," + parent2.values.Property + "," + parent2.values.Condition + ">";
				}
				else if(parent.type == types.typeUR) {
					URNode parent2 = findURNode(TreeUA, parent.nodeNum);
					par = "UR" + parent2.nodeNum + "<" + parent2.values.Entity + "," + parent2.values.Condition + ">";
				}
				else if(parent.type == types.typePlus) par = "+" + parent.nodeNum;
				else if(parent.type == types.typeX) par = "x" + parent.nodeNum;
				else if(parent.type == types.typeXc) par = "Xc" + parent.nodeNum;
				else if(parent.type == types.typePSR) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "PSR" + parent2.nodeNum + "<" + parent2.values.Sensor + "," + parent2.values.Observation + "," + parent2.values.Entity + ">";
				}
				else if(parent.type == types.typeVSR) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "VSR" + parent2.nodeNum + "<" + parent2.values.Sensor + "," + parent2.values.Observation + "," + parent2.values.Entity + ">";
				}
				else if(parent.type == types.typeAC) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "AC" + parent2.nodeNum + "<" + parent2.values.Sensor + "," + parent2.values.Observation + "," + parent2.values.Entity + ">";
				}
				else if(parent.type == types.typeDA) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "AC" + parent2.nodeNum + "<" + parent2.values.Sensor + "," + parent2.values.Observation + "," + parent2.values.Entity + ">";
				}
				if(parent.isRoot) par = "Root";

				graph.addEdge(par+str, par, str);
			}

			for(int i = 0 ; i < now.Children.size(); i++) {	// looking around now's children array
				Node next = now.Children.get(i);
				int nextNum = next.nodeNum;

				if(visit[nextNum] == false) { // if not visited, push it in the queue
					visit[nextNum] = true;
					qu.add(next);
				}
			}
		}
		for (org.graphstream.graph.Node node : graph) {
	        node.addAttribute("ui.label", node.getId());
	    }
		graph.display();
	}

	public static void displayTree2(Tree TreeUA) {
		Graph graph = new SingleGraph("test");

		boolean[] visit = new boolean[10000];
		Queue<Node> qu = new LinkedList<Node>();

		Node temp = TreeUA.Root;
		qu.add(temp);
		visit[temp.nodeNum] = true;
		Node parent = temp;
		//graph.addNode("Root");

		while(!qu.isEmpty()) { // BFS using Queue interface
			Node now = qu.poll();

			String str = "";
			if(now.type == types.typeUA) {
				UANode now2 = findUANode(TreeUA, now.nodeNum);
				str = "UA"+ now2.nodeNum;
			}
			else if(now.type == types.typeUR) {
				URNode now2 = findURNode(TreeUA, now.nodeNum);
				str = "UR" + now2.nodeNum;
			}
			else if(now.type == types.typePlus) str = "+" + now.nodeNum;
			else if(now.type == types.typeX) str = "x" + now.nodeNum;
			else if(now.type == types.typeXc) str = "Xc" + now.nodeNum;
			else if(now.type == types.typePSR) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "PSR" + now2.nodeNum;
				//System.out.println(str);
			}
			else if(now.type == types.typeVSR) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "VSR" + now2.nodeNum;
				//System.out.println(str);
			}
			else if(now.type == types.typeAC) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "AC" + now2.nodeNum;
			}
			else if(now.type == types.typeDA) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "DA" + now2.nodeNum;
			}
			if(now.isRoot) str = "Root";

			graph.addNode(str);

			String par = "";
			for(int i = 0 ; i < now.Parents.size() ; i++) {
				parent = now.Parents.get(i);
				//System.out.prStringln("now = " + Integer.toString(now.nodeNum) + " par = " + Integer.toString(parent.nodeNum));

				if(parent.type == types.typeUA) {
					UANode parent2 = findUANode(TreeUA, parent.nodeNum);
					par = "UA" + parent2.nodeNum;
				}
				else if(parent.type == types.typeUR) {
					URNode parent2 = findURNode(TreeUA, parent.nodeNum);
					par = "UR" + parent2.nodeNum;
				}
				else if(parent.type == types.typePlus) par = "+" + parent.nodeNum;
				else if(parent.type == types.typeX) par = "x" + parent.nodeNum;
				else if(parent.type == types.typeXc) par = "Xc" + parent.nodeNum;
				else if(parent.type == types.typePSR) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "PSR" + parent2.nodeNum;
				}
				else if(parent.type == types.typeVSR) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "VSR" + parent2.nodeNum;
				}
				else if(parent.type == types.typeAC) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "AC" + parent2.nodeNum;
				}
				else if(parent.type == types.typeDA) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "DA" + parent2.nodeNum;
				}
				if(parent.isRoot) par = "Root";

				graph.addEdge(par+str, par, str);
			}

			for(int i = 0 ; i < now.Children.size(); i++) {	// looking around now's children array
				Node next = now.Children.get(i);
				int nextNum = next.nodeNum;

				if(visit[nextNum] == false) { // if not visited, push it in the queue
					visit[nextNum] = true;
					qu.add(next);
				}
			}
		}
		for (org.graphstream.graph.Node node : graph) {
	        node.addAttribute("ui.label", node.getId());
	    }
		graph.display();
	}	
}