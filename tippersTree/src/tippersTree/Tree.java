package tippersTree;

import java.util.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Tree {
	public Node Root;
	
	public static int totalNum = 0; // initialize totalNum = 0

	public ArrayList<URNode> URij = new ArrayList<URNode>();
	public ArrayList<UANode> UAi = new ArrayList<UANode>();
	public ArrayList<UANode> UAij = new ArrayList<UANode>();
	public ArrayList<SRNode> SRs = new ArrayList<SRNode>();

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
			//System.out.prStringln("now = " +nowNum);

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
			//System.out.prStringln("now = " +nowNum);

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

	public UANode findUANode(Tree TreeUA, int wantNum) {
		for(int i = 0 ; i < TreeUA.UAi.size() ; i++) {
			UANode now = TreeUA.UAi.get(i);
			if(now.nodeNum == wantNum) {
				return now;
			}
		}
		for(int i = 0 ; i < TreeUA.UAij.size() ; i++) {
			UANode now = TreeUA.UAij.get(i);
			if(now.nodeNum == wantNum) {
				return now;
			}
		}
		return null;
	}

	public URNode findURNode(Tree TreeUA, int wantNum) {
		for(int i = 0 ; i < TreeUA.URij.size() ; i++) {
			URNode now = TreeUA.URij.get(i);
			if(now.nodeNum == wantNum) {
				return now;
			}
		}
		return null;
	}

	public SRNode findSRNode(Tree TreeUA, int wantNum) {
		for(int i = 0 ; i < TreeUA.SRs.size() ; i++) {
			SRNode now = TreeUA.SRs.get(i);
			if(now.nodeNum == wantNum) {
				return now;
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

		//System.out.prStringln("Parent = " + Parent.nodeNum + ", Child = " + Child.nodeNum);
	}

	static OntologyManager OM = new OntologyManager();
	// make a tree structure
	public Tree flattening(UA UARequest) {
		String E0 = UARequest.Entity;
		String P0 = UARequest.Property;
		String C0 = UARequest.Condition;

		ArrayList<String> E = OM.extractEnt(UARequest);
		ArrayList<String> P = OM.extractProp(UARequest);
		ArrayList<String> C = OM.extractCond(UARequest);

		Tree TreeUA = new Tree(UARequest);	// create a root node and a tree

		Node NodeEnts = newPlusNode();	// create a +node which connect root node and branches

		appendChild(TreeUA.Root, NodeEnts);	// Root(UA) - NodeEnts(+)
		TreeUA.UAi.add((UANode) TreeUA.Root);

		for(int i = 0 ; i < E.size() ; i++) { // for each Entity ->
			String newE = E.get(i); // get ei from E
			UA newUA = new UA(newE, P0, C0);	// create a new branch UAi = <ei, P, C>
			UANode NodeEnt = newUANode(newUA);

			appendChild(NodeEnts, NodeEnt); // Root(UA) - NodeEnts(+) - NodeEnt(UAi)
			TreeUA.UAi.add(NodeEnt);

			PlusNode NodeEnts2 = newPlusNode(); // create a new +node which connect UAi and UR

			appendChild(NodeEnt, NodeEnts2); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+)

			XcNode NodeConds = newXcNode(); // create a new +node which connect URij and another +node

			for (int j = 0 ; j < C.size() ; j++) { // for each Condition
				String newC = C.get(j); // get cj from C
				UR newUR = new UR(newE, newC); // create a new branch URij = <ei, cj>
				URNode NodeCond = newURNode(newUR);
				TreeUA.URij.add(NodeCond);

				appendChild(NodeEnts2, NodeCond); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij)
				appendChild(NodeCond, NodeConds); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc)
			}

			PlusNode NodeProps = newPlusNode(); // create a new +node
			appendChild(NodeConds, NodeProps); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+)

			for (int k = 0 ; k < P.size() ; k++) {
				String newP = P.get(k); // get pk from P
				UA newUA2 = new UA(newE, newP, C0); // create a new branch UAij = <ei, pk, cj>
				UANode NodeProp = newUANode(newUA2);
				TreeUA.UAij.add(NodeProp);

				appendChild(NodeProps, NodeProp); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+) - NodeProp(UAij)
			}
		}
		return TreeUA;
	}

	// generate from UR or UA
	public static int count = 0;
	public void URgenerator0(URNode nowNode) {
		String nowE = nowNode.values.Entity;
		String nowC = nowNode.values.Condition;
		//System.out.println(Integer.toString(nowNode.nodeNum)+ " " + nowE + " " + nowC);

		XNode XNode = newXNode();
		appendChild(nowNode, XNode);

		String nowObs = OM.findObs(nowC);
		ArrayList<String> sens = OM.findSensor(nowObs);

		for(int i = 0 ; i < sens.size(); i++) {
			String nowSen = sens.get(i);
			SR newSR = new SR(nowSen, nowObs, nowE);
			SRNode newSRNode = newSRNode(newSR);

			if(OM.isVS(nowSen)) newSRNode.type = types.typeVSR;
			else newSRNode.type = types.typePSR;

			//if(count++%2 == 0) newSRNode.type = types.typePSR;
			//else newSRNode.type = types.typeVSR;
			
			// nowSen == physic or virtual인지 type 정하기 (if)
			// newSRNode.type = types.typePSR / types.typeVSR;
			SRs.add(newSRNode);

			appendChild(XNode,newSRNode);
			generator1(newSRNode, nowE); // call other function once
		}
		return;
	}
	
	public void UAgenerator0(UANode nowNode) {
		String nowE = nowNode.values.Entity;
		String nowP = nowNode.values.Property;
		//System.out.println(Integer.toString(nowNode.nodeNum)+ " " + nowE + " " + nowC);

		XNode XNode = newXNode();
		appendChild(nowNode, XNode);

		String nowObs = OM.findAct(nowP);
		ArrayList<String> sens = OM.findActuatorInd(nowP);

		for(int i = 0 ; i < sens.size(); i++) {
			String nowSen = sens.get(i);
			SR newSR = new SR(nowSen, nowObs, nowE);
			SRNode newSRNode = newSRNode(newSR);
			newSRNode.type = types.typeAC;

			//if(OM.isVS(nowSen)) newSRNode.type = types.typeVSR;
			//else newSRNode.type = types.typePSR;

			//if(count++%2 == 0) newSRNode.type = types.typePSR;
			//else newSRNode.type = types.typeVSR;
			
			// nowSen == physic or virtual인지 type 정하기 (if)
			// newSRNode.type = types.typePSR / types.typeVSR;
			SRs.add(newSRNode);

			appendChild(XNode,newSRNode);
			//generator1(newSRNode, nowE); // call other function once
		}
		return;
	}
	
	// generate from SRNode using recursive algorithm
	public void generator1(SRNode nowNode, String nowEnt) {
		if(nowNode.type == types.typePSR) return;

		ArrayList<String> obs = OM.findInput(nowNode.values.Sensor);
		String determine = obs.get(0);

		Node ConnectNode;
		if(determine.equals("True")) {
			ConnectNode = newPlusNode();
			appendChild(nowNode, ConnectNode);
		}else {
			ConnectNode = newXNode();
			appendChild(nowNode, ConnectNode);
		}

		for(int i = 1 ; i < obs.size() ; i++) {
			String nowObs = obs.get(i);
			ArrayList<String> sens = OM.findSensor(nowObs);

			for(int j = 0 ; j < sens.size() ; j++) {
				String nowSen = sens.get(j);
				SR newSR = new SR(nowSen, nowObs, nowEnt);
				SRNode newSRNode = newSRNode(newSR);

				if(OM.isVS(nowSen)) newSRNode.type = types.typeVSR;
				else newSRNode.type = types.typePSR;

				//if(count%2==0 || count++>5) newSRNode.type = types.typePSR;
				//else newSRNode.type = types.typeVSR;
				
				// nowSen == physic or virtual인지 type 정하기 (if)
				// newSRNode.type = types.typePSR / types.typeVSR;
				SRs.add(newSRNode);

				if(determine.equals("True")) appendChild(ConnectNode, newSRNode);
				else appendChild(ConnectNode, newSRNode);
				generator1(newSRNode, nowEnt); // recursive
			}
		}
		return;
	}



	// check feasibility of each node
	public void checking(SRNode nowSRNode) {
		String nowS = nowSRNode.values.Sensor;
		String nowO = nowSRNode.values.Observation;
		String nowE = nowSRNode.values.Entity;

		ArrayList<String> sensors = OM.getAptDevices(nowS);

		boolean flag = false;
		for(int i = 0 ; i < sensors.size() ; i++) {
			String nowSI = sensors.get(i);
			//System.out.println(nowSI + " " + nowE);

			if(!OM.checkAccess(nowSI, nowE)) { // always return true
				removing(nowSRNode);
			}
			else if(!OM.checkCoverage(nowSI, nowE)) { // I don't know
				removing(nowSRNode);
			}
			// if available, add child
			else {
				//flag = true;
			}
		}
		if(flag) {
			// create a x node
			Node newXNode = newXNode();
			appendChild(nowSRNode, newXNode);

			// create a child node
			for(int i = 0 ; i < sensors.size() ; i++) {
				String nowSI = sensors.get(i);
				SR newSR = new SR(nowSI, nowO, nowE);
				Node newSRNode = newSRNode(newSR);

				appendChild(newXNode, newSRNode);
			}
		}
	}

	// remove recursively
	public void removing(Node nowNode) {
		boolean temp = false;
		if(nowNode.nodeNum == 33) temp = true;
		// if x node, there will be another chance.
		if(nowNode.type == types.typeX || nowNode.type == types.typeXc) {
			if(!nowNode.Children.isEmpty()) return;
		}

		// remove from parents' children array
		ArrayList<Node> parents = nowNode.Parents;
//		Node firPar = parents.get(0);
//		if(firPar.type == types.typePlus || firPar.type == types.typeX || firPar.type == types.typeXc) {
//			parents = firPar.Parents;
//		}
		for(int i = 0 ; i < parents.size() ; i++) {
			Node nowPar = parents.get(i);
			if(temp) System.out.println(nowPar.nodeNum);

			nowPar.Children.remove(nowNode);
			if(temp) System.out.println(nowPar.Children.get(0).nodeNum);
			// if nowPar has empty children, remove parent
			if(nowPar.Children.isEmpty()) {
				nowPar.isLeaf = nowNode.isLeaf;
				removing(nowPar);
			}
		}
		return;
	}

	// to display tree structure using BFS algorithm
	public void displayTree(Tree TreeUA) {
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
				str = "UA"+ Integer.toString(now2.nodeNum) + "<" + now2.values.Entity + "," + now2.values.Property + "," + now2.values.Condition + ">";
			}
			else if(now.type == types.typeUR) {
				URNode now2 = findURNode(TreeUA, now.nodeNum);
				str = "UR" + Integer.toString(now2.nodeNum) + "<" + now2.values.Entity + "," + now2.values.Condition + ">";
			}
			else if(now.type == types.typePlus) str = "+" + Integer.toString(now.nodeNum);
			else if(now.type == types.typeX) str = "x" + Integer.toString(now.nodeNum);
			else if(now.type == types.typeXc) str = "Xc" + Integer.toString(now.nodeNum);
			else if(now.type == types.typePSR) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "PSR" + Integer.toString(now2.nodeNum) + "<" + now2.values.Sensor + "," + now2.values.Observation + "," + now2.values.Entity + ">";
				//System.out.println(str);
			}
			else if(now.type == types.typeVSR) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "VSR" + Integer.toString(now2.nodeNum) + "<" + now2.values.Sensor + "," + now2.values.Observation + "," + now2.values.Entity + ">";
				//System.out.println(str);
			}
			else if(now.type == types.typeAC) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "AC" + Integer.toString(now2.nodeNum) + "<" + now2.values.Sensor + "," + now2.values.Observation + "," + now2.values.Entity + ">";
			}
			if(now.isRoot) str = "Root";

			graph.addNode(str);

			String par = "";
			for(int i = 0 ; i < now.Parents.size() ; i++) {
				parent = now.Parents.get(i);
				//System.out.prStringln("now = " + Integer.toString(now.nodeNum) + " par = " + Integer.toString(parent.nodeNum));

				if(parent.type == types.typeUA) {
					UANode parent2 = findUANode(TreeUA, parent.nodeNum);
					par = "UA" + Integer.toString(parent2.nodeNum) + "<" + parent2.values.Entity + "," + parent2.values.Property + "," + parent2.values.Condition + ">";
				}
				else if(parent.type == types.typeUR) {
					URNode parent2 = findURNode(TreeUA, parent.nodeNum);
					par = "UR" + Integer.toString(parent2.nodeNum) + "<" + parent2.values.Entity + "," + parent2.values.Condition + ">";
				}
				else if(parent.type == types.typePlus) par = "+" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typeX) par = "x" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typeXc) par = "Xc" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typePSR) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "PSR" + Integer.toString(parent2.nodeNum) + "<" + parent2.values.Sensor + "," + parent2.values.Observation + "," + parent2.values.Entity + ">";
				}
				else if(parent.type == types.typeVSR) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "VSR" + Integer.toString(parent2.nodeNum) + "<" + parent2.values.Sensor + "," + parent2.values.Observation + "," + parent2.values.Entity + ">";
				}
				else if(parent.type == types.typeAC) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "AC" + Integer.toString(parent2.nodeNum) + "<" + parent2.values.Sensor + "," + parent2.values.Observation + "," + parent2.values.Entity + ">";
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

	public void displayTree2(Tree TreeUA) {
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
				str = "UA"+ Integer.toString(now2.nodeNum);
			}
			else if(now.type == types.typeUR) {
				URNode now2 = findURNode(TreeUA, now.nodeNum);
				str = "UR" + Integer.toString(now2.nodeNum);
			}
			else if(now.type == types.typePlus) str = "+" + Integer.toString(now.nodeNum);
			else if(now.type == types.typeX) str = "x" + Integer.toString(now.nodeNum);
			else if(now.type == types.typeXc) str = "Xc" + Integer.toString(now.nodeNum);
			else if(now.type == types.typePSR) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "PSR" + Integer.toString(now2.nodeNum);
				//System.out.println(str);
			}
			else if(now.type == types.typeVSR) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "VSR" + Integer.toString(now2.nodeNum);
				//System.out.println(str);
			}
			else if(now.type == types.typeAC) {
				SRNode now2 = findSRNode(TreeUA, now.nodeNum);
				str = "AC" + Integer.toString(now2.nodeNum);
			}
			if(now.isRoot) str = "Root";

			graph.addNode(str);

			String par = "";
			for(int i = 0 ; i < now.Parents.size() ; i++) {
				parent = now.Parents.get(i);
				//System.out.prStringln("now = " + Integer.toString(now.nodeNum) + " par = " + Integer.toString(parent.nodeNum));

				if(parent.type == types.typeUA) {
					UANode parent2 = findUANode(TreeUA, parent.nodeNum);
					par = "UA" + Integer.toString(parent2.nodeNum);
				}
				else if(parent.type == types.typeUR) {
					URNode parent2 = findURNode(TreeUA, parent.nodeNum);
					par = "UR" + Integer.toString(parent2.nodeNum);
				}
				else if(parent.type == types.typePlus) par = "+" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typeX) par = "x" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typeXc) par = "Xc" + Integer.toString(parent.nodeNum);
				else if(parent.type == types.typePSR) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "PSR" + Integer.toString(parent2.nodeNum);
				}
				else if(parent.type == types.typeVSR) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "VSR" + Integer.toString(parent2.nodeNum);
				}
				else if(parent.type == types.typeAC) {
					SRNode parent2 = findSRNode(TreeUA, parent.nodeNum);
					par = "AC" + Integer.toString(parent2.nodeNum);
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
