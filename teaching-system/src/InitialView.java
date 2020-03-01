import java.io.PrintStream;
import java.util.Scanner;

public class InitialView {

	// private Model modelObject;
	InitialViewController initialViewControllerObject;
	Model modelObject;
	PrintStream pS;

	private final String outOfBoundaries = "Only numerical values allowed! Please choose a numerical value between 1 and 3";
	private final String selectViewUserInputOutOfBoundaries = "That number does not correspond to the avaiable options";
	private final String selectViewUserInput = "Please, enter the number that corresponds to your role and press 'Enter'.";

	public InitialView(Model model, InitialViewController controller, PrintStream ps) {
		this.modelObject = model;
		this.initialViewControllerObject = controller;
		PrintStream pStream = new PrintStream(System.out);
		pStream.print(initialView());

		this.userInput();

	}

	public Integer userInput() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		if (input.contentEquals("1")) {
			return Integer.valueOf(input);
		} else if (input.contentEquals("2")) {
			return Integer.valueOf(input);
		} else if (input.contentEquals("3")) {
			return Integer.valueOf(input);
		}

		return Integer.valueOf(input);
	}

	public String initialView() {
		return "   1: Class Director\n" + "   2: Administrator\n" + "   3: PTT Director\n"
				+ "Please enter the number that corresponds to your role and press 'Enter': \n\n";

	}

}
