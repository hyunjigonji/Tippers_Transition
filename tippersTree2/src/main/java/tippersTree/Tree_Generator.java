package tippersTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Tree_Generator extends Tree {

	public static void URgenerator0(URNode nowNode) {
		String nowE = nowNode.values.Entity;
		String nowC = nowNode.values.Condition;
		// System.out.println("URgenerator0 " + Integer.toString(nowNode.nodeNum)+ " " +
		// nowE + " " + nowC);

		Node ConnectNode = newXNode();
		appendChild(nowNode, ConnectNode);

		Set<String> sens = OntologyManager.findSensor(nowC);

		// for(int i = 0 ; i < sens.size(); i++) {
		// String nowSen = sens<i>.toString();
		Iterator<String> value = sens.iterator();
		while (value.hasNext()) {
			String nowSen = value.next();
			// }
			// System.out.println(i + " "+ nowSen);
			SR newSR = new SR(nowSen, nowC, nowE);
			SRNode newSRNode = newSRNode(newSR);

			if (OntologyManager.isVS(nowSen))
				newSRNode.type = types.typeVSR;
			else
				newSRNode.type = types.typePSR;

			SRs.add(newSRNode);

			appendChild(ConnectNode, newSRNode);
			generator1(newSRNode, nowE); // call other function once
		}
		return;
	}

	public static void UAgenerator0(UANode nowNode) {
		String nowE = nowNode.values.Entity;
		String nowP = nowNode.values.Property;
		// System.out.println(Integer.toString(nowNode.nodeNum)+ " " + nowE + " " +
		// nowP);

		XNode XNode = newXNode();
		appendChild(nowNode, XNode);

		String nowObs = OntologyManager.findAction(nowP);
		String nowActuator = OntologyManager.findActuator(nowP);
		// System.out.println(nowActuator);

		SR newSR = new SR(nowActuator, nowObs, nowE);
		SRNode newSRNode = newSRNode(newSR);

		newSRNode.type = types.typeAC;

		SRs.add(newSRNode);
		appendChild(XNode, newSRNode);

		return;
	}

	// generate from SRNode using recursive algorithm
	public static void generator1(SRNode nowNode, String nowEnt) {
		// System.out.println("generator1 " + nowNode.values.Observation + " " +
		// nowNode.values.Sensor);
		if (nowNode.type == types.typePSR)
			return;
		ArrayList<String> obs = OntologyManager.findInput(nowNode.values.Sensor);
		// decide if it requires multiple input
		Node ConnectNode;
		if (obs.size() == 1)
			ConnectNode = newXNode();
		else
			ConnectNode = newPlusNode();
		appendChild(nowNode, ConnectNode);

		for (int i = 0; i < obs.size(); i++) {
			String nowObs = obs.get(i);

			Set<String> sens = OntologyManager.findSensor(nowObs);

		//	for (int j = 0; j < sens.size(); j++) {
//				String nowSen = sens.get(j);
			Iterator<String> value = sens.iterator();
			while(value.hasNext()) {
				String nowSen = value.next();
			//}
				SR newSR = new SR(nowSen, nowObs, nowEnt);
				SRNode newSRNode = newSRNode(newSR);

				if (OntologyManager.isVS(nowSen))
					newSRNode.type = types.typeVSR;
				else
					newSRNode.type = types.typePSR;

				SRs.add(newSRNode);

				appendChild(ConnectNode, newSRNode);
				generator1(newSRNode, nowEnt); // recursive
			}
		}

		return;
	}
}