import java.io.PrintStream;

public class InitialViewController {

	private InitialView initialViewObject;
	private Model modelObject;
	PrintStream pS;
	private final String outOfBoundaries = "Only numerical values allowed! Please choose a numerical value between 1 and 3";
	private final String selectViewUserInputOutOfBoundaries = "That number does not correspond to the avaiable options";
	private final String selectViewUserInput = "Please, enter the number that corresponds to your role and press 'Enter'.";

	public InitialViewController(Model model) {
		this.modelObject = model;
		this.initialViewObject = new InitialView(model, this, pS);
	}

}
