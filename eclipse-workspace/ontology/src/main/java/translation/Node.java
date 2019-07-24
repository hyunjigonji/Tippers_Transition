package translation;

import java.util.*;

//ua = <e,p,c>
//e = entity definition
//p = property definition
//c = condition

enum types{
	typeUA, typeUR, typePlus, typeX, typeXc, typeVSR, typePSR,
};

public class Node {
	public boolean isRoot; // root -> True or not -> False
	public boolean isLeaf; // leaf -> True or not -> False
	
	public types type;
	
	public int nodeNum;
	
	ArrayList<Node> Parents; // parents can be more than 1
	ArrayList<Node> Children; // children can be more than 1
	
	public Node() {
		Parents = new ArrayList<Node>();
		Children = new ArrayList<Node>();
		
		isRoot = true;
		isLeaf = true;
	}
	/*
	public Node(UA UARequest) {
		type = types.typeUA;
		
		isRoot = false;
		isLeaf = false;
	}
	
	public Node(UR URRequest) {
		type = types.typeUR;
		
		isRoot = false;
		isLeaf = false;
	}*/
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

class VSRNode extends Node{
	public SR values;
	
	public VSRNode() {
		type = types.typeVSR;
	}
	
	public VSRNode(SR SRRequest) {
		type = types.typeVSR;
		values = SRRequest;
	}
}

class PSRNode extends Node{
	public SR values;
	
	public PSRNode() {
		type = types.typePSR;
	}
	
	public PSRNode(SR SRRequest) {
		type = types.typePSR;
		values = SRRequest;
	}
}