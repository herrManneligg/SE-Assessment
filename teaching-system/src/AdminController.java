import java.io.PrintStream;

public class AdminController {
	
	private AdminView adminViewObject;
	private Model modelObject;
	
	public AdminController(Model model) {
		this.modelObject=model;
		this.adminViewObject=new AdminView(model, this);
	}

}
