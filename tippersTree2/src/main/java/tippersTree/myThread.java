package tippersTree;

public class myThread extends Thread {

	private Tree feasibleTree;
	private SRNode nowSRNode;

	public myThread() {}
	
	public myThread(Tree feasibleTree, SRNode newNode) {
		this.feasibleTree = feasibleTree;
		this.nowSRNode = newNode;
	}

	@Override
	public void run() {
		try {
			System.out.println("Start Threading...");
			//System.out.println(Tree_Execute.executeTree(feasibleTree, nowSRNode));
			// Thread.sleep(1000);
		} catch (Exception e) {}
	}
}