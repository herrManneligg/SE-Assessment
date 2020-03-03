import java.io.PrintStream;
import java.util.Scanner;

public class AdminView {
	
	private AdminController adminControllerObject;
	private Model modelobject;
	private PrintStream ps;
	
	public AdminView(Model model, AdminController controller, PrintStream ps) {
		this.adminControllerObject = controller;
		this.modelobject = model;
		this.ps = ps;
	}
	
	public int prinView() {
		return Integer.valueOf(this.getUserInput(this.modelobject.administratorView()));
	}
	
	public String getUserInput(String message) {
		Scanner input = new Scanner(System.in);
		this.ps.print(message);
		String str = input.nextLine();
		return str;
	}
	
	public void print(String message) {
		this.ps.print(message);
	}

}
