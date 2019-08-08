package tippersTree;

import java.util.ArrayList;

public class Tree_Flattening extends Tree{
	
	static OntologyManager OM = new OntologyManager();
	// make a tree structure
	public static Tree flattening(UA UARequest) {
		String E0 = UARequest.Entity;
		String P0 = UARequest.Property;
		String C0 = UARequest.Condition;

		ArrayList<String> E = OM.extractEnt(UARequest);
		ArrayList<String> P = OM.extractProp(UARequest);
		ArrayList<String> C = OM.extractCond(UARequest);

		Tree myTree = new Tree(UARequest);	// create a root node and a tree

		Node NodeEnts = myTree.newPlusNode();	// create a +node which connect root node and branches

		myTree.appendChild(myTree.Root, NodeEnts);	// Root(UA) - NodeEnts(+)
		myTree.UAi.add((UANode) myTree.Root);

		for(int i = 0 ; i < E.size() ; i++) { // for each Entity ->
			String newE = E.get(i); // get ei from E
			UA newUA = new UA(newE, P0, C0);	// create a new branch UAi = <ei, P, C>
			UANode NodeEnt = myTree.newUANode(newUA);

			myTree.appendChild(NodeEnts, NodeEnt); // Root(UA) - NodeEnts(+) - NodeEnt(UAi)
			myTree.UAi.add(NodeEnt);

			PlusNode NodeEnts2 = myTree.newPlusNode(); // create a new +node which connect UAi and UR

			myTree.appendChild(NodeEnt, NodeEnts2); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+)

			XcNode NodeConds = myTree.newXcNode(); // create a new +node which connect URij and another +node

			for (int j = 0 ; j < C.size() ; j++) { // for each Condition
				String newC = C.get(j); // get cj from C
				UR newUR = new UR(newE, newC); // create a new branch URij = <ei, cj>
				URNode NodeCond = myTree.newURNode(newUR);
				myTree.URij.add(NodeCond);

				myTree.appendChild(NodeEnts2, NodeCond); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij)
				myTree.appendChild(NodeCond, NodeConds); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc)
			}

			PlusNode NodeProps = myTree.newPlusNode(); // create a new +node
			myTree.appendChild(NodeConds, NodeProps); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+)

			for (int k = 0 ; k < P.size() ; k++) {
				String newP = P.get(k); // get pk from P
				UA newUA2 = new UA(newE, newP, C0); // create a new branch UAij = <ei, pk, cj>
				UANode NodeProp = myTree.newUANode(newUA2);
				myTree.UAij.add(NodeProp);

				myTree.appendChild(NodeProps, NodeProp); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+) - NodeProp(UAij)
			}
		}
		return myTree;
	}
}
