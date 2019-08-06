package tippersOntology;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import uk.ac.manchester.cs.jfact.JFactFactory;
import uk.ac.manchester.cs.jfact.kernel.Individual;

public class OntologyManager {
	public static OWLOntologyManager manager;
	public static OWLDataFactory factory;
	public static OWLOntology ontology;
	public static String ontologyURL = "C:\\Users\\KIM KI MIN\\Desktop\\research project\\ontology\\ontology.owl";
	// public static String ontologyURL =
	// "/Users/hyunjigonji/tippers_transition/tippersOntology/src/main/java/tippersOntology/ontology.owl";
	public static OWLReasoner reasoner;
	public static IRI ontologyIRI = IRI
			.create("C:\\Users\\KIM KI MIN\\Desktop\\research project\\ontology\\ontology.owl");
	// public static IRI ontologyIRI =
	// IRI.create("/Users/hyunjigonji/tippers_transition/tippersOntology/src/main/java/tippersOntology/ontology.owl");
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

			@SuppressWarnings("deprecation")
			Set<OWLOntology> importsClosure = ontology.getImportsClosure();
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
		System.out.println("\n[Print all classes]");
		for (OWLClass cls : o.getClassesInSignature()) {
			System.out.println(cls);
		}
	}

	// get OWL object property
	public static OWLObjectProperty getOwlObjProp(String str) {
		return factory.getOWLObjectProperty(IRI.create(str));
	}

	// get OWL class
//	public static OWLClass getOwlClass(String str) {
//		return factory.getOWLClass(IRI.create(str));
//	}

	// show subclasses
	public static ArrayList<OWLClassExpression> showSubclasses(String str) {
		System.out.println("\n[Print subclasses]");
		ArrayList<OWLClassExpression> sub = new ArrayList<OWLClassExpression>();
		OWLClass c = factory.getOWLClass(IRI.create(ONTOLOGYURL+str));
		for (OWLSubClassOfAxiom cls : ontology.getSubClassAxiomsForSuperClass(c)) {
			sub.add(cls.getSubClass());
		}
		return sub;
	}

	// extract entity
	// extract individual
	public static ArrayList<String> extractEnt(String ent) {
		System.out.println("\n[Extract Entity]");
		ArrayList<String> instance = new ArrayList<String>();
		String ent0 = ONTOLOGYURL + ent;
		for (OWLClass c : ontology.getClassesInSignature()) {
			if (c.getIRI().toString().equals(ent0)) {
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
	public static OWLObjectProperty getOntoobjProperty(String name) {
		for (OWLObjectProperty p : ontology.getObjectPropertiesInSignature())
			if (p.getIRI().getFragment().equals(name))
				return p;
		return null;
	}

	// if there is data property
	// return that property, else return null
	public static OWLDataProperty getOntoDataProperty(String name) {
		for (OWLDataProperty p : ontology.getDataPropertiesInSignature())
			if (p.getIRI().getFragment().equals(name))
				return p;
		return null;
	}

	// go to sub property
	public static ArrayList<OWLObjectProperty> getsubProp(String prop) {
		// TODO Auto-generated method stub
		ArrayList<OWLObjectProperty> objprop = new ArrayList<OWLObjectProperty>();
		OWLObjectProperty p = getOwlObjProp(ONTOLOGYURL + prop);
		for (final OWLSubObjectPropertyOfAxiom subProp : ontology.getObjectSubPropertyAxiomsForSuperProperty(p)) {
			if (subProp.getSuperProperty() instanceof OWLProperty && subProp.getSubProperty() instanceof OWLProperty) {
				if (subProp.getSuperProperty().toString().equals("<" + ONTOLOGYURL + prop + ">")) {
					objprop.addAll(subProp.getSubProperty().getObjectPropertiesInSignature());
				}
			}
		}
		return objprop;
	}

	// find Sensor
	public static ArrayList<String> findSensor(String obs) {
		ArrayList<String> sen = new ArrayList<String>();
		System.out.println("\n[Print Sensor by Observation]");
		ArrayList<OWLObjectProperty> subprop = getsubProp("captures");
		for (int i = 0; i < subprop.size(); i++) {
			for (final OWLSubObjectPropertyOfAxiom subPrope : ontology
					.getObjectSubPropertyAxiomsForSuperProperty(subprop.get(i))) {
				if (subPrope.getSuperProperty() instanceof OWLProperty
						&& subPrope.getSubProperty() instanceof OWLProperty) {
					if (reasoner.getObjectPropertyRanges(subPrope.getSubProperty(), true).toString().contains(ONTOLOGYURL+obs)) {
						sen = strToken1(reasoner.getObjectPropertyDomains(subPrope.getSubProperty(), true)
								.getFlattened().toString());
						for (int j = 0; j < sen.size(); j++) {
							String temp = strToken0(sen.get(j));
							sen.remove(j);
							sen.add(j, temp);
						}
					}
				}
			}
		}
		return sen;
	}

	// find Observation
	public static ArrayList<String> findObs(String prop) {
		ArrayList<String> obs = new ArrayList<String>();
		System.out.println("\n[Print Observation by Observation Property]");
		ArrayList<OWLObjectProperty> subprop = getsubProp("obsType");																//ok
		for (int i = 0; i < subprop.size(); i++) {
			if (reasoner.getObjectPropertyDomains(subprop.get(i), true).toString().contains(ONTOLOGYURL+prop)) {
				obs.add(strToken0(reasoner.getObjectPropertyRanges(subprop.get(i), true).toString()));
			}
		}
		return obs;
	}

	// is VS
	// find a type of specific sensor
	public static boolean isVS(String Sensor) {
		boolean flag = false;
		System.out.println("\n[Find Sensor Type]\n" + Sensor + "  is Virtual Sensor?");
		ArrayList<OWLClassExpression> cls = showSubclasses(ONTOLOGYURL + "Sensor");
		for (int i = 0; i < cls.size(); i++) {
			for (OWLNamedIndividual idv : reasoner.getInstances(cls.get(i), false).getFlattened()) {
				if (strToken0(idv.getIRI().toString()).equals(Sensor) && cls.get(i).toString().contains("VirSensor")) {
					flag = true;
				}
			}
		}
		return flag;
	}

	private static OWLOntologyManager create() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates a HermiT OWLReasoner with the given ontology.
	 * 
	 * @param OWLOntology The ontology
	 * @return OWLReasoner The reasoner created
	 */
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