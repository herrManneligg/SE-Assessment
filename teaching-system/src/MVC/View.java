package MVC;
import People.*;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class View {

	private Scanner input;
	private PrintStream ps;

	public View(PrintStream ps) {
		this.ps = ps;
		this.input = new Scanner(System.in);
	}

	protected void openScanner() {
		this.input = new Scanner(System.in);
	}

	protected void closeScanner() {
		this.input.close();
	}

	public void printScreen(String message) {
		this.ps.print(message);
	}

	public int getUserInputInteger(String message) {
		int userResponse = -1;
		boolean action = false;
		while(!action) {
			try {
				System.out.print(message);
				userResponse = this.input.nextInt();
				action = true;
			} catch (InputMismatchException e) {
				this.ps.print("Please use only numerical values!\n");
				input.nextLine();
			}
		}
		return userResponse;
	}

	public String getUserInputString(String message) {
		String userResponse = "";
		try {
			this.ps.print(message);
			userResponse = this.input.next();
		} catch(IllegalArgumentException e) {
			this.input.nextLine();
			this.ps.print("Wrong choice of letter, try again\n");
			getUserInputString(message);

		}
		return userResponse;
	}

}
