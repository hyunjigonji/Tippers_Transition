package tippersTree;

import java.util.*;

public class Tree {
	public Node Root;
	
	public static int totalNum = 0; // initialize totalNum = 0

	public static ArrayList<URNode> URij = new ArrayList<URNode>();
	public static ArrayList<UANode> UAi = new ArrayList<UANode>();
	public static ArrayList<UANode> UAij = new ArrayList<UANode>();
	public static ArrayList<SRNode> SRs = new ArrayList<SRNode>();

	public Tree() { }

	public Tree(UA UARequest) { // create root node which is UA node
		Root = newUANode(UARequest); // root node is always UA node

		Root.isRoot = true;
		Root.isLeaf = true;
	}

	public static Node newNode() {
		Node newNode = new Node();
		newNode.nodeNum = ++totalNum;

		return newNode;
	}

	public static UANode newUANode(UA UARequest) { // create new UA node
		UANode newUANode = new UANode(UARequest);
		newUANode.nodeNum = ++totalNum; // add 1 to totalNum and assign this node's number

		return newUANode;
	}

	public static URNode newURNode(UR URRequest) { // create new UR node
		URNode newURNode = new URNode(URRequest);
		newURNode.nodeNum = ++totalNum; // add 1 to totalNum and assign this node's number

		return newURNode;
	}

	public static PlusNode newPlusNode() { // create new +node
		PlusNode newPlusNode = new PlusNode();
		newPlusNode.nodeNum = ++totalNum;

		return newPlusNode;
	}

	public static XNode newXNode() { // create new x node
		XNode newXNode = new XNode();
		newXNode.nodeNum = ++totalNum;

		return newXNode;
	}

	public static XcNode newXcNode() { // create new xc node
		XcNode newXcNode = new XcNode();
		newXcNode.nodeNum = ++totalNum;

		return newXcNode;
	}

	public static SRNode newSRNode(SR SRRequest) {
		SRNode newSRNode = new SRNode(SRRequest);
		newSRNode.nodeNum = ++totalNum;

		return newSRNode;
	}
	
	// find leaf nodes of tree and return those leaf nodes in array list
	public static ArrayList<Node> findLeafNode(Tree myTree){
		ArrayList<Node> leaves = new ArrayList<Node>();
		Node temp = myTree.Root;
		// DFS
		boolean[] visit = new boolean[10000]; // MAXIMUM = 100?????? -> modify it!
		Stack<Node> stack = new Stack<Node>(); // initialize stack
		stack.push(temp); // push root to stack first
		visit[temp.nodeNum] = true;

		while(!stack.isEmpty()) {
			Node now = stack.pop();
			//int nowNum = now.nodeNum;
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
	public static Node findNode(Tree myTree, int wantNum) {
		Node temp = myTree.Root;

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

	public static UANode findUANode(Tree myTree, int wantNum) {
		for(int i = 0 ; i < Tree.UAi.size() ; i++) {
			UANode now = Tree.UAi.get(i);
			if(now.nodeNum == wantNum) {
				return now;
			}
		}
		for(int i = 0 ; i < Tree.UAij.size() ; i++) {
			UANode now = Tree.UAij.get(i);
			if(now.nodeNum == wantNum) {
				return now;
			}
		}
		return null;
	}

	public static URNode findURNode(Tree myTree, int wantNum) {
		for(int i = 0 ; i < Tree.URij.size() ; i++) {
			URNode now = Tree.URij.get(i);
			if(now.nodeNum == wantNum) {
				return now;
			}
		}
		return null;
	}

	public static SRNode findSRNode(Tree myTree, int wantNum) {
		for(int i = 0 ; i < Tree.SRs.size() ; i++) {
			SRNode now = Tree.SRs.get(i);
			if(now.nodeNum == wantNum) {
				return now;
			}
		}
		return null;
	}

	// connect parent node and child node
	public static void appendChild(Node Parent, Node Child) {
		if(Parent.isLeaf) { // if Parent was leaf, Child should be leaf
			Parent.isLeaf = false;
			Child.isLeaf = true;
		}
		Child.isRoot = false;

		Parent.Children.add(Child);
		Child.Parents.add(Parent);

		//System.out.prStringln("Parent = " + Parent.nodeNum + ", Child = " + Child.nodeNum);
	}
}