import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InitialController {

	private View view;
	private Model model;
	private PrintStream ps;

	public InitialController(PrintStream ps) {
		this.model = new Model();
		this.ps = ps;
		this.view = new View(ps);
		this.showSelectedScreen();
	}

	public String getInitView() {
		return this.model.getInitialViewInfo();
	}

	public void showSelectedScreen() {

		int input = this.view.getUserInputInteger(this.model.getInitialViewInfo());

		if (input == 1) {
			new ClassDirectorController(this, this.model, this.ps);
		} else if (input == 2) {
			new AdminController(this, this.model, this.ps);

		} else if (input == 3) {
			new PTTDirectorController(this, this.model, this.ps);

		} else if (input == 4) {
			System.out.println("Good bye!");
			System.exit(0);
		}

	}

}
