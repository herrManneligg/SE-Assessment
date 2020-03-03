
public class PTTController {
	
	private PTTView pttView;
	private Model modelObject;
	
	public PTTController(Model model, PTTView view) {
		
		this.setModelObject(model);
		this.setPttView(view);
		
		view=new PTTView(this, model);
	}

	public PTTView getPttView() {
		return pttView;
	}

	public void setPttView(PTTView pttView) {
		this.pttView = pttView;
	}

	public Model getModelObject() {
		return modelObject;
	}

	public void setModelObject(Model modelObject) {
		this.modelObject = modelObject;
	}

}
