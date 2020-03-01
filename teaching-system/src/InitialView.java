import java.io.PrintStream;

public class InitialView {
	
	// private Model modelObject;
	InitialViewController initialViewControllerObject;
	Model modelObject;
	PrintStream pS;

	
	public InitialView(Model model, InitialViewController controller, PrintStream ps) {
		this.modelObject=model;
		this.initialViewControllerObject=controller;
		this.pS=ps;
	}
	

}
