package translation;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.layout.HierarchicalLayout;

public class Generator {

	public Generator() { 
		// TODO Auto-generated constructor stub 
	}

	public int[] generatePlan(int levels, int inputs, int vs, Boolean randomVSGen, Boolean display) {
		Graph graph = new MultiGraph("Execution Plan Visualization");	
		graph.addAttribute("ui.stylesheet", "graph { fill-color: #FFF, #BBB; fill-mode: gradient-radial; }");
		
		int countPS = 0;
		int countVS = 0;
		int[] counts = new int[2];
	
		if (!checkContraints(levels,inputs, vs)) System.exit(0);
	
		if (levels == 1) {
			countPS = 1;
			createNode(graph, "PS1", null);
//			for (int i=1; i<=inputs; i++) {
//				createNode(graph, "PS"+i, "VS1");			
//			}
		} 
		else {
			createNode(graph, "VS1", null);
			countVS = 1;
			int currentCountVS = 1;
			for (int i=1; i<levels; i++) {
				int aux = 0;
				for (int j=1; j<=currentCountVS; j++) {
					int inputsTemp = inputs;
					if (i == levels-1 ) {
//						countPS = countPS + inputs;
						inputsTemp = getRandomNumberInRange(1, inputs);
//						System.out.println("Num of final PS: " + inputsTemp);
						
						for (int k=1; k<=inputsTemp; k++) {
							countPS++;
							createNode(graph, "PS"+countPS, "VS"+(countVS+1-j));
						}	
					} 
					else {
						if (randomVSGen) {
							vs = getRandomNumberInRange(1, inputs);
//							System.out.println("Parent node: " + graph.getNode("VS"+(countVS+1-j)) +  " num of VS: "+ vs);
						}
						int ps = 0;
						if (inputs-vs > 0) {
							ps = getRandomNumberInRange(0, (inputs - vs));
						}
//						System.out.println("Parent node: " + graph.getNode("VS"+(countVS+1-j)) +  " num of PS: "+ ps);
						
						for (int k=1; k<=ps; k++) {
							countPS++;
							createNode(graph, "PS"+countPS, "VS"+(countVS+1-j));
						}
		
						for (int k=1; k<=vs; k++) {
							aux++;
							createNode(graph, "VS"+(countVS+aux), "VS"+(countVS+1-j));
						}						
//						countVS = countVS + vs;
//						aux+=vs;
					}
				}
				countVS+=aux;
				
				currentCountVS = aux;
			}
		}
		
		if (display) {
			Viewer viewer = graph.display(false);
			viewer.disableAutoLayout();
			
			HierarchicalLayout h1 = new HierarchicalLayout();
			h1.setRoots("VS1");
			h1.compute();
			h1.shake();
			viewer.enableAutoLayout(h1);
	        graph.setAutoCreate(true); 
	
		}		
	
		counts[0] = countPS;
		counts[1] = countVS;
		
		return counts;
	}
	
	public Boolean checkContraints(int levels, int inputs, int vs) {
		Boolean flag = true;
		
		if (levels < 1 ) {
			System.err.println("levels must be >= 1");
			flag = false;
		} else if (vs > inputs) {
			System.err.println("vs must be <= inputs");
			flag = false;
		}
		return flag;
	}
	
	public void createNode (Graph graph, String node, String parent) {
		graph.addNode(node);
		
		Node e1 = graph.getNode(node);
//		e1.addAttribute("ui.style", "shape:circle;fill-color: red;size: 40px; text-size: 20px;");
		e1.addAttribute("ui.style", "size-mode: fit; shape: rounded-box; fill-color: white; stroke-mode: plain; padding: 3px, 2px; text-size: 20px; ");
		e1.addAttribute("ui.label", node);
//		System.out.println("creating node: "+ node);
		
		if (parent != null) {
			graph.addEdge(parent+node, parent, node);
//			System.out.println("creating edge: "+ parent+node);
			
			Edge e2 = graph.getEdge(parent+node);
//			e2.addAttribute("ui.style", "shape: line; fill-color: #222; arrow-size: 3px, 2px;");
		}
	}
	
	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		return (int)(Math.random() * ((max - min) + 1)) + min;
	}
}
