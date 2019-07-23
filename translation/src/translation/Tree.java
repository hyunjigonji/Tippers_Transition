package translation;

import java.util.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Tree {
	public Node Root;
	
	public static int totalNum = 0; // initialize totalNum = 0
	
	public ArrayList<Node> URij;
	public ArrayList<Node> UAij;
	
	public Tree() { }
	
	public Tree(UA UARequest) { // create root node which is UA node
		Root = newUANode(UARequest); // root node is always UA node 
		
		Root.isRoot = true; 
		Root.isLeaf = true;
	}
	/*
	public Node newNode() {
		Node newNode = new Node();
		newNode.nodeNum = ++totalNum;
		
		return newNode;
	}
	*/
	public UANode newUANode(UA UARequest) { // create new UA node
		UANode newUANode = new UANode(UARequest);
		newUANode.nodeNum = ++totalNum; // add 1 to totalNum and assign this node's number
		
		return newUANode;
	}
	
	public URNode newURNode(UR URRequest) { // create new UR node
		URNode newURNode = new URNode(URRequest);
		newURNode.nodeNum = ++totalNum; // add 1 to totalNum and assign this node's number
		
		return newURNode;
	}
	
	public PlusNode newPlusNode() { // create new +node
		PlusNode newPlusNode = new PlusNode();
		newPlusNode.nodeNum = ++totalNum;
		
		return newPlusNode;
	}
	
	public XNode newXNode() { // create new x node
		XNode newXNode = new XNode();
		newXNode.nodeNum = ++totalNum;
		
		return newXNode;
	}
	
	public XcNode newXcNode() { // create new xc node
		XcNode newXcNode = new XcNode();
		newXcNode.nodeNum = ++totalNum;
		
		return newXcNode;
	}
	
	public VSRNode newVSRNode(SR SRRequest) { // create new VSR node
		VSRNode newVSRNode = new VSRNode(SRRequest);
		newVSRNode.nodeNum = ++totalNum;
	
		return newVSRNode;
	}
	
	public PSRNode newPSRNode(SR SRRequest) { // create new PSR node
		PSRNode newPSRNode = new PSRNode(SRRequest);
		newPSRNode.nodeNum = ++totalNum;
		
		return newPSRNode;
	}
	
	// find leaf nodes of tree and return those leaf nodes in array list
	public ArrayList<Node> findLeafNode(Tree TreeUA){ 
		ArrayList<Node> leaves = new ArrayList<Node>();
		Node temp = TreeUA.Root;
		// DFS
		boolean[] visit = new boolean[100]; // MAXIMUM = 100?????? -> modify it!
		Stack<Node> stack = new Stack<Node>(); // initialize stack
		stack.push(temp); // push root to stack first
		visit[temp.nodeNum] = true;
		
		while(!stack.isEmpty()) {
			Node now = stack.pop();
			int nowNum = now.nodeNum;
			//System.out.println("now = " +nowNum);
			
			if(now.isLeaf) { // if leaf, add to leaves.
				leaves.add(now);
			}
			
			for(int i = 0 ; i < now.Children.size(); i++) {	// looking around now's children array 
				Node next = now.Children.get(i);
				int nextNum = next.nodeNum;
					
				if(visit[nextNum] == false) { // if not visited, push it in the stack
					visit[nextNum] = true;
					stack.push(next);
				}
			}	
		}
		
		return leaves;
	}
	
	// find node which has same node number as input and return that node
	public Node findNode(Tree TreeUA, int wantNum) {
		Node temp = TreeUA.Root;
		
		boolean[] visit = new boolean[100];
		Stack<Node> stack = new Stack<Node>();
		stack.push(temp);
		visit[temp.nodeNum] = true;
		
		while(!stack.isEmpty()) {
			Node now = stack.pop();
			int nowNum = now.nodeNum;
			//System.out.println("now = " +nowNum);
			
			if(nowNum == wantNum) {
				return now;
			}
			
			for(int i = 0 ; i < now.Children.size(); i++) {	// looking around now's children array 
				Node next = now.Children.get(i);
				int nextNum = next.nodeNum;
					
				if(visit[nextNum] == false) { // if not visited, push it in the stack
					visit[nextNum] = true;
					stack.push(next);
				}
			}	
		}
		return null;
	}
	
	// connect parent node and child node
	public void appendChild(Node Parent, Node Child) {
		if(Parent.isLeaf) { // if Parent was leaf, Child should be leaf
			Parent.isLeaf = false;
			Child.isLeaf = true;
		} 
		Child.isRoot = false;
		
		Parent.Children.add(Child);
		Child.Parents.add(Parent);
	}
	
	OntologyManager OM = new OntologyManager();
	
	// make a tree structure
	public Tree flattening(UA UARequest) {
		Graph graph = new SingleGraph("test");
		/*
		 *  Modify it!!! E, P, C -> What format? Integer/ArrayList ??
		 */
		int E0 = UARequest.Entity;
		int P0 = UARequest.Property;
		int C0 = UARequest.Condition;
		
		ArrayList<Integer> E = OM.extractEnt(UARequest);
		ArrayList<Integer> P = OM.extractProp(UARequest);
		ArrayList<Integer> C = OM.extractCond(UARequest);
		
		Tree TreeUA = new Tree(UARequest);	// create a root node and a tree
		//System.out.println(TreeUA.Root.nodeNum);
		graph.addNode("Root");
		//System.out.println("root = " + TreeUA.Root.nodeNum);
		
		Node NodeEnts = newPlusNode();	// create a +node which connect root node and branches
		graph.addNode("+");
		//System.out.println("nodeEnts = " + NodeEnts.nodeNum);
		
		appendChild(TreeUA.Root, NodeEnts);	// Root(UA) - NodeEnts(+)
		graph.addEdge("Root+","Root","+");
		
		for(int i = 0 ; i < E.size() ; i++) { // for each Entity -> 
			int newE = E.get(i); // get ei from E
			UA newUA = new UA(newE, P0, C0);	// create a new branch UAi = <ei, P, C>
			UANode NodeEnt = newUANode(newUA);
			//System.out.println("UAi = " + NodeEnt.nodeNum);
			
			String nowUA0 = "UA" + "<" + Integer.toString(newE) + ", " + Integer.toString(P0) + ", " + Integer.toString(C0) + ">";
			graph.addNode(nowUA0);
			//System.out.println("nodeEnt = " + NodeEnt.nodeNum);
			
			appendChild(NodeEnts, NodeEnt); // Root(UA) - NodeEnts(+) - NodeEnt(UAi)
			graph.addEdge("+"+nowUA0, "+", nowUA0);
			
			Node NodeEnts2 = newPlusNode(); // create a new +node which connect UAi and UR
			
			String nowPlus = "+" + Integer.toString(newE);
			graph.addNode(nowPlus);
			//System.out.println("nodeEnts2 = " + NodeEnts2.nodeNum);
			
			appendChild(NodeEnt, NodeEnts2); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+)
			graph.addEdge(nowUA0+nowPlus, nowUA0, nowPlus);
		
			Node NodeConds = newXcNode(); // create a new +node which connect URij and another +node
			
			String nowXc = "Xc" + Integer.toString(newE);
			graph.addNode(nowXc);
			//System.out.println("nodeConds = " + NodeConds.nodeNum);
			
			for (int j = 0 ; j < C.size() ; j++) { // for each Condition
				int newC = C.get(j); // get cj from C
				UR newUR = new UR(newE, newC); // create a new branch URij = <ei, cj>
				URNode NodeCond = newURNode(newUR);
				TreeUA.URij.add(NodeCond);
				
				String nowUR = "UR" + "<" + Integer.toString(newE) + ", " + Integer.toString(newC) + ">";
				graph.addNode(nowUR); 
				//System.out.println("nodeCond = " + NodeCond.nodeNum);
				
				appendChild(NodeEnts2, NodeCond); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij)
				graph.addEdge(nowPlus+nowUR, nowPlus, nowUR);
				
				appendChild(NodeCond, NodeConds); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc)
				graph.addEdge(nowUR+nowXc, nowUR, nowXc);
			}
			
			Node NodeProps = newPlusNode(); // create a new +node
			
			String nowPlus2 = "+2" + Integer.toString(newE);
			graph.addNode(nowPlus2);
			//System.out.println("nodeProps = " + NodeProps.nodeNum);
			 
			appendChild(NodeConds, NodeProps); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+)
			graph.addEdge(nowXc+nowPlus2, nowXc, nowPlus2);
			
			for (int k = 0 ; k < P.size() ; k++) {
				int newP = P.get(k); // get pk from P
				UA newUA2 = new UA(newE, newP); // create a new branch UAij = <ei, pk, cj>
				UANode NodeProp = newUANode(newUA2);
				TreeUA.UAij.add(NodeProp);
				
				String nowUA = "UA" + "<" + Integer.toString(newE) + ", " + Integer.toString(newP) + ">";
				graph.addNode(nowUA); 
				//System.out.println("nodeProp = " + NodeProp.nodeNum);
				
				appendChild(NodeProps, NodeProp); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+) - NodeProp(UAij)
				graph.addEdge(nowPlus2+nowUA, nowPlus2, nowUA);
			}
		}
		
		for (org.graphstream.graph.Node node : graph) {
	        node.addAttribute("ui.label", node.getId());
	    }
		
		//graph.display();
		
		return TreeUA;
	}
	
	// 각 virtual sensor가 어떤 phsysical 센서 or 어떤 virtual sensor를 필요로 하는지
	public Tree genrator(Tree TreeUA, SR SRRequest, Graph graph) {
		int S0 = SRRequest.Sensor;
		int O0 = SRRequest.Observation;
		int E0 = SRRequest.Entity;
		
		ArrayList<Integer> S = OM.extractSen(SRRequest);
		ArrayList<Integer> O = OM.extractObs(SRRequest);
		ArrayList<Integer> E = OM.extractEnt(SRRequest);
		
		for(int i = 0 ; i < TreeUA.URij.size() ; i++) { // extend each nodes in URij level
			Node nowNode = TreeUA.URij.get(i); // nowNode(URij) URij = <ei, cj>
			
			Node plusNode = newPlusNode();	// create a +node which connect root node and branches
			appendChild(nowNode, plusNode); // connect URij node with +node
			
			for(int j = 0 ; j < S.size() ; j++) {
				int newS = S.get(j);
				SR newSR = new SR(newS, O0, E0);
				
				if(newS == 0) { // if newS is type of virtual sensor
					VSRNode newVSRNode = new VSRNode(newSR);
				}
				else if(newS == 1) { // if newS is type of physical sensor
					PSRNode newPSRNode = new PSRNode(newSR);
				}
			}
		}
		
		for(int i = 0 ; i < TreeUA.UAij.size() ; i++) { // extend each nodes in UAij level
			Node nowNode = TreeUA.UAij.get(i); // nowNode(UAij) UAij = <ei, pk, cj>
			
			Node plusNode = newPlusNode(); // create a +node which connect root node and branches
			appendChild(nowNode, plusNode); // connect URij node with + node
			
			
		}
		
		return TreeUA;
	}
}