package tippersOntology;

import org.semanticweb.owlapi.model.OWLException;

public class index {

	public static void main(String[] args) throws OWLException {
		OntologyManager.startOntologyManager();

		OntologyManager.createOWLReasoner();
		OntologyManager.showOntology(); // show ontology path
		OntologyManager.showClasses(); // show ontology classes
		System.out.println("\n[test showSubclasses]\n" + OntologyManager.showSubclasses("Sensor")); // test >> show all
																									// of subclasses
																									// with super
																									// classes
		System.out.println(OntologyManager.getObservation());
		
		System.out.println("test		"+OntologyManager.getDomain("ThercapturesTemp"));
		System.out.println("test		"+OntologyManager.getRange("ThercapturesTemp"));
		
		// 이거는 대소문자 구분 필요, 혼자 사용할 거 아니고 다른 메소드에서 불러서 쓸거라서 대소문자 구분 안해도 된다고 생각
		System.out.println(OntologyManager.getIndividuals("office"));
		System.out.println(OntologyManager.findSensor("occupancy"));
		System.out.println(OntologyManager.findSensor("connectivity"));
		System.out.println(OntologyManager.findSensor("image"));

		System.out.println(OntologyManager.isVS("Location2connectivity"));
		System.out.println(OntologyManager.isVS("LocationImage2occupancy"));
		System.out.println(OntologyManager.isVS("Wifi"));
		System.out.println(OntologyManager.isVS("GPS"));
		System.out.println(OntologyManager.isVS("Camera"));
		System.out.println(OntologyManager.isVS("Thermometer"));
		System.out.println(OntologyManager.isVS("BB"));
		System.out.println(OntologyManager.isVS("Connectivity2occupancy"));

		System.out.println(OntologyManager.getAptDevice("meetingRoom"));
		System.out.println(OntologyManager.getAptDevice("office"));
		System.out.println(OntologyManager.getAptDevice("GPS"));

		System.out.println(OntologyManager.findInput("connectivity2occupancy"));
		System.out.println(OntologyManager.findInput("location2connectivity"));
		System.out.println(OntologyManager.findInput("locationimage2occupancy"));

		System.out.println(OntologyManager.checkCoverage("Camera1", "meetingroom1")); // true
		System.out.println(OntologyManager.checkCoverage("Camera1", "meetingroom2")); // false
		System.out.println(OntologyManager.checkCoverage("Camera2", "meetingroom2")); // true
		System.out.println(OntologyManager.checkCoverage("Camera3", "meetingroom2")); // true

		System.out.println(OntologyManager.checkCoverage("GPS2", "meetingroom2"));
		System.out.println(OntologyManager.checkCoverage("GPS1", "meetingroom1"));
		System.out.println(OntologyManager.checkCoverage("GPS1", "meetingroom2"));
		System.out.println(OntologyManager.checkCoverage("BB3", "meetingroom2"));
		System.out.println(OntologyManager.checkCoverage("BB3", "meetingroom3"));
		System.out.println(OntologyManager.checkCoverage("Light2", "meetingroom2"));
		System.out.println(OntologyManager.checkCoverage("Light1", "meetingroom1"));
		System.out.println(OntologyManager.checkCoverage("Thermometer3", "meetingroom3"));

		System.out.println(OntologyManager.getTime("Connectivity2occupancy")); // 40
		System.out.println(OntologyManager.getTime("Wifi2")); // 45
		System.out.println(OntologyManager.getTime("Wifi3")); // 30
		System.out.println(OntologyManager.getTime("Location2connectivity")); // 17
		System.out.println(OntologyManager.getTime("GPS3")); //
		System.out.println(OntologyManager.getTime("AC1")); //
		System.out.println(OntologyManager.getTime("AC2")); //
		System.out.println(OntologyManager.getTime("BB1")); // 80
		System.out.println(OntologyManager.getTime("GPS1")); // 5
		System.out.println(OntologyManager.getTime("Camera1")); // 5
		System.out.println(OntologyManager.getTime("LocationImage2occu")); // 1
		System.out.println(OntologyManager.getTime("Wifi1")); 
		
		System.out.println(OntologyManager.getMoney("Connectivity2occupancy")); // 30
		System.out.println(OntologyManager.getMoney("Wifi2")); // 50
		System.out.println(OntologyManager.getMoney("Wifi3")); // 60
		System.out.println(OntologyManager.getMoney("Location2connectivity")); // 33
		System.out.println(OntologyManager.getMoney("GPS3")); //
		System.out.println(OntologyManager.getMoney("AC1")); //
		System.out.println(OntologyManager.getMoney("AC2")); //
		System.out.println(OntologyManager.getMoney("BB1")); // 60
		System.out.println(OntologyManager.getMoney("GPS1")); // 5
		System.out.println(OntologyManager.getMoney("Camera1")); // 5
		System.out.println(OntologyManager.getMoney("LocationImage2occu")); // 5
		System.out.println(OntologyManager.getMoney("Wifi1")); 

//		System.out.println(OntologyManager.getsubProp("captures"));
//		System.out.println(OntologyManager.getsubProp("hasDevice"));
//		System.out.println(OntologyManager.getsubProp("input"));
//		System.out.println(OntologyManager.getCost("Location2conn", "Time"));

//		System.out.println(OntologyManager.getOntoObjProperty("captures"));		//object property
//		System.out.println(OntologyManager.getOntoDataProperty("captures"));		//data property	

		System.out.println(OntologyManager.getRange("inputC2O"));
		System.out.println(OntologyManager.getRange("inputL2C"));
		System.out.println(OntologyManager.getRange("inputLI2O"));

		System.out.println(OntologyManager.getDomain("inputC2O"));
		System.out.println(OntologyManager.getDomain("inputL2C"));
		System.out.println(OntologyManager.getDomain("inputLI2O"));
	}
}
