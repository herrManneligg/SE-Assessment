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
	
	protected void printScreen(String message) {
		this.ps.print(message);
	}
	
	protected int getUserInputInteger(String message) {
		int userResponse = -1;
		try {
			this.ps.print(message);
			userResponse = this.input.nextInt();
		} catch(InputMismatchException e) { 
			this.input.nextLine();
			this.ps.print("You should enter a number\n");
			this.getUserInputInteger(message);
		}
		return userResponse;
	}

	protected String getUserInputString(String message) {
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
