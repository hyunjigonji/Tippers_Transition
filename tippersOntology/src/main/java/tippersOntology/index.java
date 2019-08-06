package tippersOntology;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLException;

public class index {

	public static void main(String[] args) throws OWLException {

		OntologyManager.startOntologyManager();
		
		OntologyManager.createOWLReasoner();
		OntologyManager.showOntology();		//show ontology path
		OntologyManager.showClasses();		//show ontology classes
		System.out.println(OntologyManager.showSubclasses("Room"));	//show all of subclasses with superclasses
		System.out.println(OntologyManager.extractEnt("office"));
		System.out.println(OntologyManager.findSensor("Occupancy"));
		System.out.println(OntologyManager.findObs("tempProp"));

		System.out.println(OntologyManager.isVS("Conn2occ"));
		System.out.println(OntologyManager.isVS("Wifi2"));
		
		System.out.println("\n" + OntologyManager.getOntoobjProperty("captures"));		//object property
		System.out.println("\n" + OntologyManager.getOntoDataProperty("captures"));		//data property	
	}
}
