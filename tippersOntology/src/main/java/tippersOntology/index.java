package tippersOntology;

import org.semanticweb.owlapi.model.OWLException;

public class index {

	public static void main(String[] args) throws OWLException {
		OntologyManager.startOntologyManager();
		
		OntologyManager.createOWLReasoner();
		OntologyManager.showOntology();		//show ontology path
		OntologyManager.showClasses();		//show ontology classes
		System.out.println("\n[test showSubclasses]\n"+OntologyManager.showSubclasses("Sensor"));		//test >> show all of subclasses with super classes
																									//이거는 대소문자 구분 필요, 혼자 사용할 거 아니고 다른 메소드에서 불러서 쓸거라서 대소문자 구분 안해도 된다고 생각
		System.out.println(OntologyManager.extractEnt("office"));
		System.out.println(OntologyManager.findSensor("occupancy"));
		System.out.println(OntologyManager.findSensor("connectivity"));
		System.out.println(OntologyManager.findSensor("image"));

		System.out.println(OntologyManager.isVS("Location2connectivity"));
		System.out.println(OntologyManager.isVS("LocationImage2occupancy"));
		System.out.println(OntologyManager.isVS("Wifi"));
		System.out.println(OntologyManager.isVS("GPS"));
		
		System.out.println(OntologyManager.getAptDevice("meetingRoom"));
		System.out.println(OntologyManager.getAptDevice("office"));
		System.out.println(OntologyManager.getAptDevice("GPS"));
		
		System.out.println(OntologyManager.findInput("connectivity2occupancy"));
		System.out.println(OntologyManager.findInput("location2connectivity"));
		System.out.println(OntologyManager.findInput("locationimage2occupancy"));

		System.out.println(OntologyManager.checkCoverage("camera1", "meetingroom1"));		//true
		System.out.println(OntologyManager.checkCoverage("camera1", "meetingroom2"));		//false
		System.out.println(OntologyManager.checkCoverage("camera2", "meetingroom2"));		//true
		System.out.println(OntologyManager.checkCoverage("camera3", "meetingroom2"));		//true
		
		System.out.println(OntologyManager.checkCoverage("GPS2", "meetingroom2"));	
		
		System.out.println(OntologyManager.getTime("Wifi1"));		//대소문자 구분 필요함
		System.out.println(OntologyManager.getTime("Wifi2"));
		System.out.println(OntologyManager.getTime("Wifi3"));
		System.out.println(OntologyManager.getTime("GPS3"));
		
		System.out.println(OntologyManager.getMoney("Wifi1"));		//대소문자 구분 필요함
		System.out.println(OntologyManager.getMoney("Wifi2"));
		System.out.println(OntologyManager.getMoney("Wifi3"));
		

		System.out.println(OntologyManager.getMoney("Camera3"));
		System.out.println(OntologyManager.getTime("Camera3"));
		
//		System.out.println(OntologyManager.getOntoObjProperty("captures"));		//object property
//		System.out.println(OntologyManager.getOntoDataProperty("captures"));		//data property	
	}
}
