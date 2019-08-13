package tippersOntology;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import uk.ac.manchester.cs.jfact.JFactFactory;

/*
 * File Description
 * ontology.owl has all of sensors and rooms
 * ontology1.owl has all of sensors in a room
 * ontology2.owl has no Wifi sensor in a room
 * ontology3.owl has  only GPS, Camera in a room
 * ontology4.owl has no Camera in a room 
 */

public class OntologyManager {
	public static OWLOntologyManager manager;
	public static OWLDataFactory factory;
	public static OWLOntology ontology;
	public static String ontologyURL = "../ontology4.owl";
	public static OWLReasoner reasoner;
	public static IRI ontologyIRI = IRI.create("../ontology4.owl");
	public static BidirectionalShortFormProvider bidiShortFormProvider;
	public static String ONTOLOGYURL = "http://www.semanticweb.org/kimkimin/ontologies/2019/6/untitled-ontology-12#";

	public OntologyManager() {
		startOntologyManager();
	}

	public static void startOntologyManager() {
		manager = OWLManager.createOWLOntologyManager();
		factory = OWLManager.getOWLDataFactory();

		System.out.println(" Starting...");
		try {
			System.out.println("Loading ontology   " + ontologyURL + "...");
			ontology = manager.loadOntologyFromOntologyDocument(new File(ontologyURL));

			reasoner = createOWLReasoner();

//			@SuppressWarnings("deprecation")
//			Set<OWLOntology> importsClosure = ontology.getImportsClosure();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String strToken0(String string) {
		// TODO Auto-generated method stub
		StringTokenizer str = new StringTokenizer(string, "#");
		String temp = null;
		while (str.hasMoreElements()) {
			temp = str.nextToken();
		}
		StringTokenizer str2 = new StringTokenizer(temp, ">");
		temp = str2.nextToken();
		return temp;
	}

	public static ArrayList<String> strToken1(String string) {
		// TODO Auto-generated method stub
		StringTokenizer str = new StringTokenizer(string, ",");
		ArrayList<String> temp = new ArrayList<String>();
		while (str.hasMoreElements()) {
			temp.add(str.nextToken());
		}
		return temp;
	}

	// show information of ontology
	public static OWLOntology showOntology() throws OWLOntologyCreationException {
		System.out.println("Loaded ontology: " + ontology);
		return ontology;
	}

	// show classes of ontology
	public static void showClasses() throws OWLException { // show classes
		OWLOntology o = showOntology();
		System.out.println("\n[showClasses: Print all classes]");
		for (OWLClass cls : o.getClassesInSignature()) {
			System.out.println(cls);
		}
	}

	// get OWL object property
	public static OWLObjectProperty getOwlObjProp(String str) {
		return factory.getOWLObjectProperty(IRI.create(ONTOLOGYURL + str));
	}

	// get OWL class
	public static OWLClass getOwlClass(String str) {
		return factory.getOWLClass(IRI.create(ONTOLOGYURL + str));
	}

	// show subclasses
	public static ArrayList<OWLClassExpression> showSubclasses(String str) {
		ArrayList<OWLClassExpression> sub = new ArrayList<OWLClassExpression>();
		OWLClass c = getOwlClass(str);
		for (OWLSubClassOfAxiom cls : ontology.getSubClassAxiomsForSuperClass(c)) {
			sub.add(cls.getSubClass());
		}
		return sub;
	}

	// extract entity
	// extract individual
	public static ArrayList<String> extractEnt(String ent) {
		System.out.println("\n[extractEnt: Extract Entity {" + ent + "}]");
		ArrayList<String> instance = new ArrayList<String>();
		String ent0 = ONTOLOGYURL + ent;
		for (OWLClass c : ontology.getClassesInSignature()) {
			if (c.getIRI().toString().equalsIgnoreCase(ent0)) {
				NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
				for (OWLNamedIndividual i : instances.getFlattened()) {
					String temp = strToken0(i.getIRI().toString());
					instance.add(temp);
				}
			}
		}
		return instance;
	}

	// if there is object property
	// return that property, else return null
	public static OWLObjectProperty getOntoObjProperty(String name) {
		System.out.println("\n[getOntoObjProperty: get object property that has same name with input]");
		for (OWLObjectProperty p : ontology.getObjectPropertiesInSignature())
			if (p.getIRI().getFragment().equalsIgnoreCase(name))
				return p;
		return null;
	}

	// if there is data property
	// return that property, else return null
	public static OWLDataProperty getOntoDataProperty(String name) {
		System.out.println("\n[getOntoDataProperty: get data property that has same name with input]");
		for (OWLDataProperty p : ontology.getDataPropertiesInSignature())
			if (p.getIRI().getFragment().equalsIgnoreCase(name))
				return p;
		return null;
	}

	// go to sub property
	public static ArrayList<OWLObjectProperty> getsubProp(String prop) {
		// TODO Auto-generated method stub
		ArrayList<OWLObjectProperty> objprop = new ArrayList<OWLObjectProperty>();
		OWLObjectProperty p = getOwlObjProp(prop);
		for (final OWLSubObjectPropertyOfAxiom subProp : ontology.getObjectSubPropertyAxiomsForSuperProperty(p)) {
			if (subProp.getSuperProperty() instanceof OWLProperty && subProp.getSubProperty() instanceof OWLProperty) {
				if (subProp.getSuperProperty().toString().equalsIgnoreCase("<" + ONTOLOGYURL + prop + ">")) {
					objprop.addAll(subProp.getSubProperty().getObjectPropertiesInSignature());
				}
			}
		}
		return objprop;
	}

	// getAptDevice
	// get individual of class
	// 대소문자 구분 X
	public static ArrayList<String> getAptDevice(String cls) {
		// TODO Auto-generated method stub
		System.out.println("\n[getAptDevice: get individuals of {" + cls + "} ]");
		ArrayList<String> idv = new ArrayList<String>();
		for (Node<OWLNamedIndividual> i : reasoner.getInstances(getOwlClass(cls), false)) {
			idv.add(strToken0(i.toString()));
		}
		return idv;
	}

	// find Sensor
	// return names of classes in arrayList
	public static ArrayList<String> findSensor(String obs) {
		ArrayList<String> sen = new ArrayList<String>();
		System.out.println("\n[findSensor: Print Sensor by {" + obs + "}]");
		for (OWLObjectPropertyExpression p : ontology.getObjectPropertiesInSignature()) {
			if (strToken0(reasoner.getObjectPropertyRanges(p, true).toString()).equalsIgnoreCase(obs)
					& strToken0(p.toString()).contains("captures")) {
				for (Node<OWLClass> c : reasoner.getObjectPropertyDomains(p, true)) {
					if (strToken0(c.toString()).contains("Node"))
						continue;
					sen.add(strToken0(c.toString()));
				}
			}
		}
		checkdup(sen);
		return sen;
	}

	// check multiple or plus
	// if there is only 1 thing in array, return true
	public static ArrayList<String> checkdup(ArrayList<String> arr) {
		if (arr.size() >= 2) {
			for (int i = 0; i < arr.size(); i++) {
				for (int j = i + 1; j < arr.size(); j++) {
					if (arr.get(i).equals(arr.get(j))) {
						arr.remove(j);
					}
				}
			}
		} else if (arr.isEmpty()) return null;

		return arr;
	}

	// is VS
	// find a type of specific sensor
	// A parameter(Sensor) is class name
	public static boolean isVS(String Sensor) {
		boolean flag = false;
		System.out.println("\n[isVS: Find Sensor Type]\n" + Sensor + " is Virtual Sensor?");
		ArrayList<OWLClassExpression> cls = showSubclasses("Sensor");
		for (int i = 0; i < cls.size(); i++) {
			if (strToken0(cls.get(i).toString()).equalsIgnoreCase("VirSensor")) {
				for (OWLClassExpression e : showSubclasses(strToken0(cls.get(i).toString()))) {
					if (strToken0(e.toString()).equalsIgnoreCase(Sensor))
						flag = true;
				}
			}
		}
		return flag;
	}

	// find input
	// input array of class name
	public static ArrayList<String> findInput(String vs) {
		ArrayList<String> arr = new ArrayList<String>();
		System.out.println("\n[findInput: find VS{" + vs + "} input]");
		for (OWLObjectProperty p : ontology.getObjectPropertiesInSignature()) {
			if (strToken0(reasoner.getObjectPropertyDomains(p, true).toString()).equalsIgnoreCase(vs)
					& p.toString().contains("input")) {
				for (Node<OWLClass> c : reasoner.getObjectPropertyRanges(p, true)) {
					if (strToken0(c.toString()).contains("Node"))
						continue;
					arr.add(strToken0(c.toString()));
				}
			}
		}
		return arr;
	}

	// check a device in a room
	// both are individual name
	public static boolean checkCoverage(String sen, String ent) {
		boolean chk = false;
		System.out.println("\n[checkCoverage: check a sensor{" + sen + "} in a room{" + ent + "}]");
		for (OWLIndividual i : ontology.getIndividualsInSignature()) {
			if (strToken0(i.toString()).equalsIgnoreCase(ent)) {
				for (OWLObjectPropertyAssertionAxiom o : ontology.getObjectPropertyAssertionAxioms(i)) {
					if (strToken0(o.toString()).equalsIgnoreCase(sen))
						chk = true;
				}
			}
		}
		return chk;
	}

	/**
	 * Creates a HermiT OWLReasoner with the given ontology.
	 * 
	 * @param OWLOntology The ontology
	 * @return OWLReasoner The reasoner created
	 */
	// create owl reasoner
	public static OWLReasoner createOWLReasoner() throws IllegalArgumentException {

		OWLReasonerFactory jfact = new JFactFactory();
		return jfact.createReasoner(ontology, new SimpleConfiguration(50000));
	}

	/**
	 * Creates a query engine to process DL queries.
	 * 
	 * @param OWLOntology The ontology
	 * @return DLQueryEngine The engine to process DL queries
	 */

	public static void addAxiomsOntology(Set<OWLAxiom> axioms) {
		manager.addAxioms(ontology, axioms);
	}
}