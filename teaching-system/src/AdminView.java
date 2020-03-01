import java.io.PrintStream;

public class AdminView {
	AdminController adminControllerObject;
	Model modelobject;
	
	
	public AdminView(Model model, AdminController controller) {
		this.modelobject= model;
		this.adminControllerObject=controller;
	}
	
	public void initializeAdminView() {

		System.out.println("You are now in the AdminView, enjoy MOTHA FUCKER"); 

	}

}
