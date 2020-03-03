
public class AdminController {
	
	private AdminView adminViewObject;
	private Model modelObject;
	
	public AdminController(Model model, AdminView view) {
		this.setModelObject(model);
		this.setAdminViewObject(view);
		
		view=new AdminView(model, this);
	}

	public AdminView getAdminViewObject() {
		return adminViewObject;
	}

	public void setAdminViewObject(AdminView adminViewObject) {
		this.adminViewObject = adminViewObject;
	}

	public Model getModelObject() {
		return modelObject;
	}

	public void setModelObject(Model modelObject) {
		this.modelObject = modelObject;
	}

}
