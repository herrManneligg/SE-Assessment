import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InitialController {
	
	private InitialView view;
	private Model model;
	private PrintStream ps;

	public InitialController(PrintStream ps) {
		this.model = new Model();
		this.ps = ps;
		this.view = new InitialView(this, this.model, ps);
		this.showSelectedScreen();
	}
	
	public String getInitView() {
		return this.model.getInitialViewInfo();
	}
	
	public void showSelectedScreen() {
		boolean finishAction = false;
		while(!finishAction) {
			try {
				int input = view.initialView();
				
				if (input == 1) {
					new ClassDirectorController(this, this.model, this.ps);
				} else if (input == 2) {
					new AdminController(this, this.model, this.ps);
				} else if (input == 3) {
					new PTTDirectorController(this, this.model, this.ps);
				} else if (input == 4) {
					System.out.println("Good bye!");
					System.exit(0);
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				System.out.println("noob");
			}
			
		}
	}

}
