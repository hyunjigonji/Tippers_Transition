package tippersTree;

public class myThread extends Thread {

	private Tree feasibleTree;
	private URNode ur;

	public myThread(Tree feasibleTree, URNode ur) {
		this.feasibleTree = feasibleTree;
		this.ur = ur;
	}

	@Override
	public void run() {
		try {
			System.out.println("Start Threading...");
			Tree_Execute.executeTree(feasibleTree, ur);
			// Thread.sleep(500);
		} catch (Exception e) {}
	}
}
