package tippersTree;

import java.util.ArrayList;

public class Tree_Flattening extends Tree{
	// make a tree structure
	public static Tree flattening(UA UARequest) {
		String E0 = UARequest.Entity;
		String P0 = UARequest.Property;
		String C0 = UARequest.Condition;

		ArrayList<String> E = OntologyManager.extractEnt(UARequest);
		ArrayList<String> P = OntologyManager.extractProp(UARequest);
		ArrayList<String> C = OntologyManager.extractCond(UARequest);

		Tree myTree = new Tree(UARequest);	// create a root node and a tree

		Node NodeEnts = Tree.newPlusNode();	// create a +node which connect root node and branches

		Tree.appendChild(myTree.Root, NodeEnts);	// Root(UA) - NodeEnts(+)
		Tree.UAi.add((UANode) myTree.Root);

		for(int i = 0 ; i < E.size() ; i++) { // for each Entity ->
			String newE = E.get(i); // get ei from E
			UA newUA = new UA(newE, P0, C0);	// create a new branch UAi = <ei, P, C>
			UANode NodeEnt = Tree.newUANode(newUA);

			Tree.appendChild(NodeEnts, NodeEnt); // Root(UA) - NodeEnts(+) - NodeEnt(UAi)
			Tree.UAi.add(NodeEnt);

			PlusNode NodeEnts2 = Tree.newPlusNode(); // create a new +node which connect UAi and UR

			Tree.appendChild(NodeEnt, NodeEnts2); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+)

			XcNode NodeConds = Tree.newXcNode(); // create a new +node which connect URij and another +node

			for (int j = 0 ; j < C.size() ; j++) { // for each Condition
				String newC = C.get(j); // get cj from C
				UR newUR = new UR(newE, newC); // create a new branch URij = <ei, cj>
				URNode NodeCond = Tree.newURNode(newUR);
				Tree.URij.add(NodeCond);

				Tree.appendChild(NodeEnts2, NodeCond); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij)
				Tree.appendChild(NodeCond, NodeConds); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc)
			}

			PlusNode NodeProps = Tree.newPlusNode(); // create a new +node
			Tree.appendChild(NodeConds, NodeProps); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+)

			for (int k = 0 ; k < P.size() ; k++) {
				String newP = P.get(k); // get pk from P
				UA newUA2 = new UA(newE, newP, C0); // create a new branch UAij = <ei, pk, cj>
				UANode NodeProp = Tree.newUANode(newUA2);
				Tree.UAij.add(NodeProp);

				Tree.appendChild(NodeProps, NodeProp); // Root(UA) - NodeEnts(+) - NodeEnt(UAi) - NodeEnts2(+) - NodeCond(URij) - NodeConds(xc) - NodeProps(+) - NodeProp(UAij)
			}
		}
		return myTree;
	}
}
