

public class AdminView {
	private AdminController adminControllerObject;
	private Model modelobject;
	
	
	public AdminView(Model model, AdminController controller) {
		this.setModelobject(model);
		this.setAdminControllerObject(controller);
	}
	
	public void initializeAdminView() {

		System.out.println("You are now in the AdminView, enjoy MOTHA FUCKER"); 

	}

	public Model getModelobject() {
		return modelobject;
	}

	public void setModelobject(Model modelobject) {
		this.modelobject = modelobject;
	}

	public AdminController getAdminControllerObject() {
		return adminControllerObject;
	}

	public void setAdminControllerObject(AdminController adminControllerObject) {
		this.adminControllerObject = adminControllerObject;
	}

}
