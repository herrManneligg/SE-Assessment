import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InitialView {

	// private Model modelObject;
	InitialViewController initialViewControllerObject;
	AdminController adminController;
	Model modelObject;
	PrintStream pS;
	AdminView adminview;

	private final String outOfBoundaries = "Please choose a numerical value between the range 1 and 3, please try again";
	private final String selectViewUserInputOutOfBoundaries = "That number does not correspond to the avaiable options, please try again";
	private final String selectViewUserInput = "Please, enter the number that corresponds to your role and press 'Enter'.";

	public InitialView(Model model, InitialViewController controller, PrintStream ps) {
		this.modelObject = model;
		this.initialViewControllerObject = controller;
		PrintStream pStream = new PrintStream(System.out);
		pStream.print(initialView());

		this.userInput();

	}

	public void userInput() {

		boolean isAccepted = false;
		Scanner scanner = new Scanner(System.in);

		while (!isAccepted) {
			try {
				int input = scanner.nextInt();

				if (input == 1) {
					System.out.println("ONE-works");
					isAccepted = true;

				} else if (input == 2) {
					System.out.println(" TWO- works");
					AdminView adminView = new AdminView(modelObject, adminController);
					adminView.initializeAdminView();
					isAccepted = true;

				} else if (input == 3) {
					System.out.println("THREE-works");
					isAccepted = true;
				} else {
					System.out.println(outOfBoundaries);
					scanner.nextLine();
					
				}
			} catch (InputMismatchException e) {
				System.out.println(selectViewUserInputOutOfBoundaries);
				scanner.nextLine();
			}
		}

		scanner.close();
	}

	public String initialView() {
		return "   1: Class Director\n" + "   2: Administrator\n" + "   3: PTT Director\n\n"
				+ selectViewUserInput+"\n\n";

	}

}