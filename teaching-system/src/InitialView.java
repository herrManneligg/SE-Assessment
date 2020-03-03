import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InitialView {

	private InitialController controller;
	private Model model;
	private PrintStream ps;

	public InitialView(InitialController controller, Model model, PrintStream ps) {
		this.controller = controller;
		this.model = model;
		this.ps = ps;
	}
	
	public int initialView() {
		return Integer.valueOf(this.getUserInput(this.model.getInitialViewInfo()));
	}

	public String getUserInput(String message) {
		Scanner input = new Scanner(System.in);
		System.out.print(message);
		String str = input.nextLine();
		return str;
	}

}
