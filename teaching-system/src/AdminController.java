import java.io.PrintStream;
import java.util.InputMismatchException;

public class AdminController {
	
	private InitialController initialController;
	private Model model;
	private PrintStream ps;
	private AdminView adminViewObject;
	
	public AdminController(InitialController initialController, Model model, PrintStream ps) {
		this.initialController = initialController;
		this.model = model;
		this.ps = ps;
		this.adminViewObject = new AdminView(model, this, ps);
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
					// open previous semesters
				} else if(input == 3) {
				} else if(input == 4) {
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
