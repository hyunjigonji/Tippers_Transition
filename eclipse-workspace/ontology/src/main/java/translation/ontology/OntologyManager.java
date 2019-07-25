package translation.ontology;

import java.io.File;
import java.lang.invoke.*;
import java.lang.invoke.StringConcatFactory;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.Ontology;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import com.github.andrewoma.dexx.collection.List;

//import edu.uci.isg.mobipedia.parsing.HTMLParser;
import es.unizar.semantic.DLQueryEngine;
import es.unizar.semantic.DLQueryParser;
import es.unizar.semantic.DLQueryEngine;


public class OntologyManager {
	public static OWLOntologyManager manager;
	public static OWLDataFactory factory;
	public static OWLOntology ontology;
	public static String ontologyURL = "C:\\Users\\KIM KI MIN\\Desktop\\research project\\ontology\\ontology.owl";
	public static OWLReasoner reasoner;
	public static DLQueryEngine queryEngine;
	public static IRI ontologyIRI = IRI
			.create("C:\\Users\\KIM KI MIN\\Desktop\\research project\\ontology\\ontology.owl");
	public static BidirectionalShortFormProvider bidiShortFormProvider;
	private Map<String, OntClass> classMap = new HashMap<String, OntClass>();

	public OntologyManager() {
		startOntologyManager();
	}

	public static void startOntologyManager() {
		manager = OWLManager.createOWLOntologyManager();
		factory = OWLManager.getOWLDataFactory();

		System.out.println(" Starting...");
		System.out.println("test");
		try {
			System.out.println("Loading ontology   " + ontologyURL + "...");
			ontology = manager.loadOntologyFromOntologyDocument(new File(ontologyURL));

			reasoner = createOWLReasoner();

			queryEngine = createDLQueryEngine();

			@SuppressWarnings("deprecation")
			Set<OWLOntology> importsClosure = ontology.getImportsClosure();
			// Create a bidirectional short form provider to do the actual
			// mapping.
			// It will generate names using the input
			// short form provider.
//			bidiShortFormProvider = new BidirectionalShortFormProviderAdapter(manager, importsClosure, queryEngine.getShortFormProvider());

		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// show information of ontology
	public static OWLOntology showOntology() throws OWLOntologyCreationException {
//		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//		File file = new File("C:\\Users\\KIM KI MIN\\Desktop\\research project\\ontology\\ontology.owl");
//		OWLOntology Ontology = manager.loadOntologyFromOntologyDocument(new File(ontologyURL));
//		IRI documentIRI = manager.getOntologyDocumentIRI(Ontology);

		System.out.println("Loaded ontology: " + ontology);
//		System.out.println("from: " + ontologyIRI);

		return ontology;
	}

	// show classes of ontology
	public static void showClasses() throws OWLException { // show classes
		OWLOntology o = showOntology();

		System.out.println("\n Print classes...");
		for (OWLClass cls : o.getClassesInSignature()) {
			System.out.println(cls);
		}
	}

	// show subclasses
	// 입력한 슈처 클래스의 서브 클래스 받아오기
	public static void showSubclasses() {
		System.out.println("\n print subclasses...");
		for (final OWLSubClassOfAxiom subClasse : ontology.getAxioms(AxiomType.SUBCLASS_OF)) {
			if (subClasse.getSuperClass() instanceof OWLClass && subClasse.getSubClass() instanceof OWLClass) {
				if (subClasse.getSuperClass().toString().equals(
						"<http://www.semanticweb.org/kimkimin/ontologies/2019/6/untitled-ontology-12#Entity>")) {
					System.out.println(subClasse.getSubClass());
				}
			}
		}
	}	//파라미터로 바꿀것
		//이미 코드가 있음

	// get individuals
	// 입력한 클래스의 individual 출력
	public static ArrayList<String> printIndividualsByclass(String owlClass) {
		System.out.println("\n show individuals...");
		ArrayList<String> instance = null;
		for (OWLClass c : ontology.getClassesInSignature()) {
			if (c.getIRI().toString().equals(owlClass)) {
				NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
				for (OWLNamedIndividual i : instances.getFlattened()) {
					System.out.println(i.getIRI());
					StringTokenizer str = new StringTokenizer(i.getIRI().toString(), "#");
					String temp = null;
					while (str.hasMoreElements()) {
						temp = str.nextToken();
					}
					System.out.println(temp);
				}
			}
		}
		return instance;
	}

	// 입력된 스트링과 같은 이름의 objective property 가 있으면 출력,
	// 아니면 null
	public static OWLObjectProperty getOntoobjProperty(String name) {
		for (OWLObjectProperty p : ontology.getObjectPropertiesInSignature())
			if (p.getIRI().getFragment().equals(name))
				return p;
		return null;
	}

	// 입력된 스트링과 같은 이름의 data property 가 있으면 출력, 아니면 null
	public static OWLDataProperty getOntoDataProperty(String name) {
		for (OWLDataProperty p : ontology.getDataPropertiesInSignature())
			if (p.getIRI().getFragment().equals(name))
				return p;
		return null;
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
		return new Reasoner(ontology);
	}

	/**
	 * Creates a query engine to process DL queries.
	 * 
	 * @param OWLOntology The ontology
	 * @return DLQueryEngine The engine to process DL queries
	 */

	public static DLQueryEngine createDLQueryEngine() throws IllegalArgumentException {
		if (reasoner == null) {
			throw new IllegalArgumentException("OWLReasoner is null");
		}
		return new DLQueryEngine(reasoner, new SimpleShortFormProvider());
	}

	/*
	 * 
	 * public void saveOntology(String nameOntology) { // Now save a local copy of
	 * the ontology. (Specify a path appropriate to // your setup)
	 * 
	 * File theDir = new File(HTMLParser.FOLDER_ONTOLOGIES);
	 * 
	 * if (!theDir.exists()) { try { theDir.mkdir(); } catch (SecurityException se)
	 * { // handle it } }
	 * 
	 * File file = new File(theDir.getPath() + File.separator + nameOntology +
	 * ".owl"); try { manager.saveOntology(ontology, IRI.create(file.toURI())); }
	 * catch (OWLOntologyStorageException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * // TO CHANGE FORMAT (IF NEEDED) // // By default ontologies are saved in the
	 * format from which they were // // loaded. In this case the ontology was
	 * loaded from rdf/xml. We // // can get information about the format of an
	 * ontology from its // manager // OWLDocumentFormat format =
	 * manager.getOntologyFormat(ontology); // // We can save the ontology in a
	 * different format. Lets save the // // ontology // // in owl/xml format //
	 * OWLXMLDocumentFormat owlxmlFormat = new OWLXMLDocumentFormat(); // // Some
	 * ontology formats support prefix names and prefix IRIs. In our // // case we
	 * loaded the Koala ontology from an rdf/xml format, which // // supports
	 * prefixes. When we save the ontology in the new format we // // will copy the
	 * prefixes over so that we have nicely abbreviated // IRIs // // in the new
	 * ontology document // if (format.isPrefixOWLOntologyFormat()) { //
	 * owlxmlFormat.copyPrefixesFrom(format.asPrefixOWLOntologyFormat()); // } //
	 * manager.saveOntology(ontology, owlxmlFormat, // IRI.create(file.toURI())); }
	 * 
	 */

	public static void addAxiomsOntology(Set<OWLAxiom> axioms) {
		manager.addAxioms(ontology, axioms);
	}

}