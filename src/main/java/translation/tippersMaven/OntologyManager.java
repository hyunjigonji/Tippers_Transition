package translation.tippersMaven;

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
import org.semanticweb.owlapi.reasoner.*;
//import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

//import edu.uci.isg.mobipedia.parsing.HTMLParser;
//import translation.ontologyManager;
import uk.ac.manchester.cs.jfact.JFactFactory;

public class OntologyManager {
   public static OWLOntologyManager manager;
   public static OWLDataFactory factory;
   public static OWLOntology ontology;
   public static String ontologyURL = "C:\\Users\\KIM KI MIN\\Desktop\\research project\\ontology\\ontology.owl";
   public static OWLReasoner reasoner;
//   public static DLQueryEngine queryEngine;
   public static IRI ontologyIRI = IRI
         .create("C:\\Users\\KIM KI MIN\\Desktop\\research project\\ontology\\ontology.owl");
   public static BidirectionalShortFormProvider bidiShortFormProvider;

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

//         queryEngine = createDLQueryEngine();

         @SuppressWarnings("deprecation")
         Set<OWLOntology> importsClosure = ontology.getImportsClosure();
         // Create a bidirectional short form provider to do the actual
         // mapping.
         // It will generate names using the input
         // short form provider.
//         bidiShortFormProvider = new BidirectionalShortFormProviderAdapter(manager, importsClosure, queryEngine.getShortFormProvider());

      } catch (OWLOntologyCreationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   // show information of ontology
   public static OWLOntology showOntology() throws OWLOntologyCreationException {
//      OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//      File file = new File("C:\\Users\\KIM KI MIN\\Desktop\\research project\\ontology\\ontology.owl");
//      OWLOntology Ontology = manager.loadOntologyFromOntologyDocument(new File(ontologyURL));
//      IRI documentIRI = manager.getOntologyDocumentIRI(Ontology);

      System.out.println("Loaded ontology: " + ontology);
//      System.out.println("from: " + ontologyIRI);

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
   // ?낅젰???덉쿂 ?대옒?ㅼ쓽 ?쒕툕 ?대옒??諛쏆븘?ㅺ린
   public static void showSubclasses(String str) {
      System.out.println("\n print subclasses...");
      for (final OWLSubClassOfAxiom subClasse : ontology.getAxioms(AxiomType.SUBCLASS_OF)) {
         if (subClasse.getSuperClass() instanceof OWLClass && subClasse.getSubClass() instanceof OWLClass) {
            if (subClasse.getSuperClass().toString().equals(str)) {
               System.out.println(subClasse.getSubClass());
            }
         }
      }
   } // ?뚮씪誘명꽣濡?諛붽?寃?
      // ?대? 肄붾뱶媛  ?덉쓬

   // get individuals
   // ?낅젰???대옒?ㅼ쓽 individual 異쒕젰
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

   // ?낅젰???ㅽ듃留곴낵 媛숈? ?대쫫??objective property 媛  ?덉쑝硫?異쒕젰,
   // ?꾨땲硫?null
   public static OWLObjectProperty getOntoobjProperty(String name) {
      for (OWLObjectProperty p : ontology.getObjectPropertiesInSignature())
         if (p.getIRI().getFragment().equals(name))
            return p;
      return null;
   }

   // ?낅젰???ㅽ듃留곴낵 媛숈? ?대쫫??data property 媛  ?덉쑝硫?異쒕젰, ?꾨땲硫?null
   public static OWLDataProperty getOntoDataProperty(String name) {
      for (OWLDataProperty p : ontology.getDataPropertiesInSignature())
         if (p.getIRI().getFragment().equals(name))
            return p;
      return null;
   }

   // findSensor
   // observation??二쇰㈃ sensor瑜??섍꺼以?
   public static OWLObjectProperty findSensor(String string) {
      System.out.println("\n[Print Sensor by Observation]");
      OWLObjectProperty p = factory.getOWLObjectProperty(
            IRI.create("http://www.semanticweb.org/kimkimin/ontologies/2019/6/untitled-ontology-12#captures"));
      for (final OWLSubObjectPropertyOfAxiom subProp : ontology.getObjectSubPropertyAxiomsForSuperProperty(p)) {
         if (subProp.getSuperProperty() instanceof OWLProperty && subProp.getSubProperty() instanceof OWLProperty) {
            if (subProp.getSuperProperty().toString().equals(
                  "<http://www.semanticweb.org/kimkimin/ontologies/2019/6/untitled-ontology-12#captures>")) {
               for (final OWLSubObjectPropertyOfAxiom subPrope : ontology
                     .getObjectSubPropertyAxiomsForSuperProperty(subProp.getSubProperty())) {
                  if (subProp.getSuperProperty() instanceof OWLProperty
                        && subProp.getSubProperty() instanceof OWLProperty) {
                     for (OWLObjectProperty o : subPrope.getSubProperty().getObjectPropertiesInSignature()) {
                        if (reasoner.getObjectPropertyRanges(o, true).toString().contains(string)) {
                           System.out.println(reasoner.getObjectPropertyDomains(o, true).getFlattened()); // return
                                                                                       					  // node
                                                                                       					  // set
                           
                           System.out.println("Sensor: " + reasoner.getObjectPropertyDomains(o, true)
                                 .getFlattened().iterator().next());
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   // is VS
   // find a type of specific sensor
   // string 以??? 吏㏃? ?⑥뼱濡?以????덈룄濡??섏젙
   public static boolean isVS(String Sensor) {
      boolean flag = false;
      System.out.println("\n[Find Sensor Type]\n" + Sensor + "  is Virtual Sensor?");
      OWLClass cls = factory.getOWLClass(IRI.create(Sensor));
      for (OWLSubClassOfAxiom sub : ontology.getSubClassAxiomsForSubClass(cls)) {
         if (sub.toString().contains("VirSensor")) {
            flag = true;
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
      // return new Reasoner.ReasonerFactory().createReasoner(ontology);
   }

   /**
    * Creates a query engine to process DL queries.
    * 
    * @param OWLOntology The ontology
    * @return DLQueryEngine The engine to process DL queries
    */

//   public static DLQueryEngine createDLQueryEngine() throws IllegalArgumentException {
//      if (reasoner == null) {
//         throw new IllegalArgumentException("OWLReasoner is null");
//      }
//      return new DLQueryEngine(reasoner, new SimpleShortFormProvider());
//   }

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