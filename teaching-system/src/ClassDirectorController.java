
public class ClassDirectorController {
	
	private ClassDirectorView classDirectorViewObject;
	private Model modelObject;
	
	public ClassDirectorController(Model model, ClassDirectorView view) {
		
		this.setModelObject(model);
		this.setClassDirectorViewObject(view);
		
		view=new ClassDirectorView(this, model);
		
	}

	public ClassDirectorView getClassDirectorViewObject() {
		return classDirectorViewObject;
	}

	public void setClassDirectorViewObject(ClassDirectorView classDirectorViewObject) {
		this.classDirectorViewObject = classDirectorViewObject;
	}

	public Model getModelObject() {
		return modelObject;
	}

	public void setModelObject(Model modelObject) {
		this.modelObject = modelObject;
	}
		
	

}
