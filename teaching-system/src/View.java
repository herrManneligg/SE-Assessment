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
			System.out.print(message);
			userResponse = this.input.nextInt();
		} catch(InputMismatchException e) { 
			this.ps.print(e);
		}
		return userResponse;
	}

	protected String getUserInputString(String message) {
		String userResponse = "";
		try {			
			this.ps.print(message);
			userResponse = this.input.nextLine();
		} catch(IllegalArgumentException e) {
			this.ps.print(e);
		}
		return userResponse;
	}
	
}
