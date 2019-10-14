package tippersTree;

import java.util.*;

public class Tree_Execute extends Tree {
	public static Tree myTree = new Tree();
	public static String action = "PREPARE";
	public static HashMap<Integer, Statement> checkDup = new HashMap<Integer, Statement>();

	public static void executeTree(Tree feasibleTree, SRNode nowSR) {
		myTree = feasibleTree;
		// Statement state1 = new Statement();

		if (nowSR.Children.isEmpty()) {
			action = "PREPARE";
			Statement state1 = new Statement(nowSR.values.Sensor, nowSR.values.Observation, nowSR.values.Entity);

			checkDup.put(nowSR.nodeNum, state1);
			
			
			System.out.println(nowSR.nodeNum + "	:	" + state1.Entity);
			System.out.println(nowSR.nodeNum + "	:	" + state1.Sensor);
			System.out.println(nowSR.nodeNum + "	:	" + state1.Observation);
			System.out.println(nowSR.nodeNum + "	:	" + state1.Former);
			System.out.println("===========" + nowSR.nodeNum + "	:	" + state1 + "===========");
			System.out.println(action + " <" + state1 + ">");

			createStatement(nowSR, state1);
		}
		return;
	}

	public static void createStatement(SRNode nowSR, Statement state1) {
		Statement state2 = new Statement();
		Node parent = nowSR.Parents.get(0).Parents.get(0);

		if (parent.type == types.typeUR) {
			action = "CALL";
			System.out.println(action + " <" + state1 + ">");
			return;
		} else {
			SRNode nowSR2 = findSRNode(myTree, parent.nodeNum);
			if (checkDup.containsKey(nowSR2.nodeNum)) {
				System.out.println("=== duplicated ===" + nowSR2.nodeNum);
				state2 = checkDup.get(nowSR2.nodeNum);
			} else {
				state2 = new Statement(nowSR2.values.Sensor, nowSR2.values.Observation, nowSR2.values.Entity);
			}

			state2.Former.add(state1);

			checkDup.put(nowSR2.nodeNum, state2);

			System.out.println(nowSR2.nodeNum + "	:	" + state2.Entity);
			System.out.println(nowSR2.nodeNum + "	:	" + state2.Sensor);
			System.out.println(nowSR2.nodeNum + "	:	" + state2.Observation);
			System.out.println(nowSR2.nodeNum + "	:	" + state2.Former);
			System.out.println("===========" + nowSR2.nodeNum + "	:	" + state2 + "===========");
			System.out.println(action + " <" + state2 + ">");

			createStatement(nowSR2, state2);
		}
	}
}
