import java.io.PrintStream;
import java.util.InputMismatchException;

public class PTTDirectorController {
	
	private InitialController initialController;
	private Model model;
	private PrintStream ps;
	private PTTView adminViewObject;

	public PTTDirectorController(InitialController initialController, Model model, PrintStream ps) {
		this.initialController = initialController;
		this.model = model;
		this.ps = ps;
		this.adminViewObject = new PTTView(model, this, ps);
		this.showSelectedOptionFromScreen();
	}
	
	public void showSelectedOptionFromScreen() {
		boolean finishAction = false;
		while(!finishAction) {
			int input = this.adminViewObject.prinView();
			try {
				if (input == 1) {
					// Here is where the logic to create the new semester with the models and the database goes
				} else if(input == 2) {
					finishAction = true;
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				System.out.println("noob");
			}
		}
	}
	
}
