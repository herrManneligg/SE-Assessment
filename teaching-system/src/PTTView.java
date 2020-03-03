
public class PTTView {
	
	private PTTController pttController;
	private Model modelObject;
	
	public PTTView(PTTController controller, Model model) {
		
		this.setPttController(controller);
		this.setModelObject(model);
		
	}
	
	public void initializePttView() {

		System.out.println("You are now in the PTT-View"); 

	}

	public PTTController getPttController() {
		return pttController;
	}

	public void setPttController(PTTController pttController) {
		this.pttController = pttController;
	}

	public Model getModelObject() {
		return modelObject;
	}

	public void setModelObject(Model modelObject) {
		this.modelObject = modelObject;
	}

}
