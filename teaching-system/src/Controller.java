import java.io.PrintStream;
import java.util.InputMismatchException;

public class Controller {

	private View view;
	private PrintStream ps;

	public Controller(PrintStream ps) {
		this.ps = ps;
		this.view = new View(ps);
		this.showSelectedScreen();
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public PrintStream getPs() {
		return ps;
	}

	public void setPs(PrintStream ps) {
		this.ps = ps;
	}
	
	public void showSelectedScreen() {
		boolean finishAction = false;
		while(!finishAction) {
			try {
				String message = "\nPlease enter the number that corresponds to your role and press 'Enter':\n\n" + 
								 "   1: Class Director\n" + 
								 "   2: Administrator\n" + 
								 "   3: PTT Director\n" + 
								 "   4: Close Program\n" +
								 "\nEnter the number for your selection: ";
				int input = this.view.getUserInputInteger(message);
				
				if (input == 1) {
					new ClassDirector(this.view);
				} else if (input == 2) {
					new Administrator(this.view);
				} else if (input == 3) {
					new PttDirector(this.view);
				} else if (input == 4) {
					System.out.println("Goodbye!");
					System.exit(0);
				} else {
					System.out.println("Enter a numerical value within the range");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("Enter a numerical value.");
			}
			
		}
	}
	
}
