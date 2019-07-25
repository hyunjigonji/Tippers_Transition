package translation;

import java.util.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Tree {
	public Node Root;
	
	public static int totalNum = 0; // initialize totalNum = 0
	
	public ArrayList<URNode> URij = new ArrayList<URNode>();
	public ArrayList<UANode> UAij = new ArrayList<UANode>();
	
	public Tree() { }
	
	public Tree(UA UARequest) { // create root node which is UA node
		Root = newUANode(UARequest); // root node is always UA node 
		
		Root.isRoot = true; 
		Root.isLeaf = true;
	}
	
	public Node newNode() {
		Node newNode = new Node();
		newNode.nodeNum = ++totalNum;
		
		return newNode;
	}
	
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
	
	public SRNode newSRNode(SR SRRequest) {
		SRNode newSRNode = new SRNode(SRRequest);
		newSRNode.nodeNum = ++totalNum;
		
		return newSRNode;
	}
	/*
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
	*/
	// find leaf nodes of tree and return those leaf nodes in array list
	public ArrayList<Node> findLeafNode(Tree TreeUA){ 
		ArrayList<Node> leaves = new ArrayList<Node>();
		Node temp = TreeUA.Root;
		// DFS
		boolean[] visit = new boolean[10000]; // MAXIMUM = 100?????? -> modify it!
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
	
	static OntologyManager OM = new OntologyManager();
	
	// make a tree structure
	public Tree flattening(UA UARequest) {
		int E0 = UARequest.Entity;
		int P0 = UARequest.Property;
		int C0 = UARequest.Condition;
		
		ArrayList<Integer> E = OM.extractEnt(UARequest);
		ArrayList<Integer> P = OM.extractProp(UARequest);
		ArrayList<Integer> C = OM.extractCond(UARequest);
		
		Tree TreeUA = new Tree(UARequest);	// create a root node and a tree
		
		Node NodeEnts = newPlusNode();	// create a +node which connect root node and branches
		
		appendChild(TreeUA.Root, NodeEnts);	// Root(UA) - NodeEnts(+)
		
		for(int i = 0 ; i < E.size() ; i++) { // for each Entity -> 
			int newE = E.get(i); // get ei from E
			UA newUA = new UA(newE, P0, C0);	// create a new branch UAi = <ei, P, C>
			UANode NodeEnt = newUANode(newUA);
			
			appendChild(NodeEnts, NodeEnt); // Root(UA) - NodeEnts(+) - NodeEnt(UAi)
			
			PlusNode NodeEnts2 = newPlusNode(); // create a new +node which connect UAi and UR
			
			appendChild(NodeEnt, NodeEnts2); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+)
		
			XcNode NodeConds = newXcNode(); // create a new +node which connect URij and another +node
			
			for (int j = 0 ; j < C.size() ; j++) { // for each Condition
				int newC = C.get(j); // get cj from C
				UR newUR = new UR(newE, newC); // create a new branch URij = <ei, cj>
				URNode NodeCond = newURNode(newUR);
				TreeUA.URij.add(NodeCond);
				
				appendChild(NodeEnts2, NodeCond); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij)
				appendChild(NodeCond, NodeConds); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc)
			}
			
			PlusNode NodeProps = newPlusNode(); // create a new +node
			appendChild(NodeConds, NodeProps); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+)
			
			for (int k = 0 ; k < P.size() ; k++) {
				int newP = P.get(k); // get pk from P
				UA newUA2 = new UA(newE, newP); // create a new branch UAij = <ei, pk, cj>
				UANode NodeProp = newUANode(newUA2);
				TreeUA.UAij.add(NodeProp);
				
				appendChild(NodeProps, NodeProp); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+) - NodeProp(UAij)
			}
		}
		return TreeUA;
	}
	
	public static int count = 0;
	public void generator0(UANode nowNode) {
		int nowE = nowNode.values.Entity;
		int nowC = nowNode.values.Condition;
		
		PlusNode PlusNode = newPlusNode();
		appendChild(nowNode, PlusNode);
		
		int nowObs = OM.findObs(nowC);
		ArrayList<Integer> sens = OM.findSensor(nowObs);
		
		for(int i = 0 ; i < sens.size(); i++) {
			int nowSen = sens.get(i);
			SR newSR = new SR(nowSen, nowObs, nowE);
			SRNode newSRNode = newSRNode(newSR);
			
			if(count%3 != 0) newSRNode.type = types.typePSR;
			// nowSen == physic or virtual인지 type 정하기 (if)
			// newSRNode.type = types.typePSR / types.typeVSR;
			appendChild(PlusNode,newSRNode);
			generator1(newSRNode, nowE);
		}
		return;
	}
	
	public void generator1(SRNode nowNode, int nowEnt) {
		if(nowNode.type == types.typePSR) return;
		
		PlusNode PlusNode = newPlusNode();
		appendChild(nowNode, PlusNode);
		
		ArrayList<Integer> obs = OM.findInput(nowNode.values.Sensor);
		for(int i = 0 ; i < obs.size() ; i++) {
			int nowObs = obs.get(i);
			ArrayList<Integer> sens = OM.findSensor(nowObs);
			
			for(int j = 0 ; j < sens.size() ; j++) {
				int nowSen = sens.get(j);
				SR newSR = new SR(nowSen, nowObs, nowEnt);
				SRNode newSRNode = newSRNode(newSR);
				
				if(count%3 != 0 || count>50) newSRNode.type = types.typePSR;
				// nowSen == physic or virtual인지 type 정하기 (if)
				// newSRNode.type = types.typePSR / types.typeVSR;
				appendChild(PlusNode,newSRNode);
				generator1(newSRNode, nowEnt);
			}
		}
		return;
	}
	
	public void displayTree(Tree TreeUA) { // to display tree structure
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
			if(now.type == types.typeUA) str = "UA" + Integer.toString(now.nodeNum);
			else if(now.type == types.typeUR) str = "UR" + Integer.toString(now.nodeNum);
			else if(now.type == types.typePlus) str = "+" + Integer.toString(now.nodeNum);
			else if(now.type == types.typeXc) str = "Xc" + Integer.toString(now.nodeNum);
			else if(now.type == types.typePSR) str = "PSR" + Integer.toString(now.nodeNum);
			else if(now.type == types.typeVSR) str = "VSR" + Integer.toString(now.nodeNum);
			if(now.isRoot) str = "Root";
			
			graph.addNode(str);
			
			String par = "";
			for(int i = 0 ; i < now.Parents.size() ; i++) {
				parent = now.Parents.get(i);
				System.out.println("now = " + Integer.toString(now.nodeNum) + " par = " + Integer.toString(parent.nodeNum));
				
				if(parent.type == types.typeUA) par = "UA" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typeUR) par = "UR" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typePlus) par = "+" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typeXc) par = "Xc" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typePSR) par = "PSR" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typeVSR) par = "VSR" + Integer.toString(parent.nodeNum);
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