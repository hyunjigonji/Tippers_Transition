package translation.tippersMaven;

import org.semanticweb.owlapi.model.OWLException;

public class index {

	public static void main(String[] args) throws OWLException {

		OntologyManager.startOntologyManager();
		OntologyManager.createOWLReasoner();
		OntologyManager.showOntology();		//show ontology path
		OntologyManager.showClasses();		//show ontology classes
<<<<<<< HEAD
		OntologyManager.showSubclasses("http://www.semanticweb.org/kimkimin/ontologies/2019/6/untitled-ontology-12#Room");	//show all of subclasses with superclasses
		OntologyManager.printIndividualsByclass("http://www.semanticweb.org/kimkimin/ontologies/2019/6/untitled-ontology-12#Sensor");
=======
		OntologyManager.showSubclasses(null);	//show all of subclasses with superclasses
		OntologyManager.extractEnt(OntologyManager.ONTOLOGYURI+"room");
		System.out.println("****PrintTEst****" + OntologyManager.extractEnt("http://www.semanticweb.org/kimkimin/ontologies/2019/6/untitled-ontology-12#room"));
>>>>>>> hotfix
		OntologyManager.findSensor("http://www.semanticweb.org/kimkimin/ontologies/2019/6/untitled-ontology-12#Occupancy");
		OntologyManager.findObs("http://www.semanticweb.org/kimkimin/ontologies/2019/6/untitled-ontology-12#tempProp");

		System.out.println(OntologyManager.isVS("Wifi"));
		
		System.out.println("\n" + OntologyManager.getOntoobjProperty("captures"));		//object property
		System.out.println("\n" + OntologyManager.getOntoDataProperty("captures"));		//data property	
	}
}
