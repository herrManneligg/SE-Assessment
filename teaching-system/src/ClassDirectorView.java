
public class ClassDirectorView {
	
	private ClassDirectorController classDirectorController;
	private Model modelObject;
	
	public ClassDirectorView(ClassDirectorController controller, Model model) {
		
		this.classDirectorController=controller;
		this.modelObject=model;
		
	}
	
	public void initializeClassDirectorView() {

		System.out.println("You are now in the ClassDirector-View"); 

	}

	
}
