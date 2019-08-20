package tippersTree;

import java.util.*;

//ua = <e,p,c>
//e = entity definition
//p = property definition
//c = condition

enum types{
	typeUA, typeUR, typePlus, typeX, typeXc, typeVSR, typePSR, typeAC, typeDA,
};

public class Node {
	public boolean isRoot; // root -> True or not -> False
	public boolean isLeaf; // leaf -> True or not -> False
	
	public types type;
	//public Object values;
	
	public int nodeNum;
	
	ArrayList<Node> Parents; // parents can be more than 1
	ArrayList<Node> Children; // children can be more than 1
	
	public Node() {
		Parents = new ArrayList<Node>();
		Children = new ArrayList<Node>();
		
		isRoot = true;
		isLeaf = true;
	}
}

class UANode extends Node{
	public UA values; // UA = <E, P, C>
	
	public UANode() {
		type = types.typeUA;
	}
	
	public UANode(UA UARequest) { // initializer of UA node
		type = types.typeUA;
		values = UARequest;
	}
}

class URNode extends Node{
	public UR values; // UR = <E, C>
	
	public URNode() {
		type = types.typeUR;
	}
	
	public URNode(UR URRequest) { // initializer of UR node
		type = types.typeUR;
		values = URRequest;
	}
}

class PlusNode extends Node{
	public PlusNode() { 
		type = types.typePlus;
	}
}

class XNode extends Node{
	public XNode() {
		type = types.typeX;
	}
}

class XcNode extends Node{
	public XcNode() {
		type = types.typeXc;
	}
}

class SRNode extends Node{
	public SR values;
	
	public SRNode() {
		type = types.typeVSR;
	}
	
	public SRNode(SR SRRequest) {
		type = types.typeVSR;
		values = SRRequest;
	}
}