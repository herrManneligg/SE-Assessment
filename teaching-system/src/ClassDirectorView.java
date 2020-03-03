import java.io.PrintStream;
import java.util.Scanner;

public class ClassDirectorView {
	
	private ClassDirectorController controller;
	private Model model;
	private PrintStream ps;

	public ClassDirectorView(ClassDirectorController controller, Model model, PrintStream ps) {
		this.controller = controller;
		this.model = model;
		this.ps = ps;
	}
	
	public int prinView() {
		return Integer.valueOf(this.getUserInput(this.model.classDirectorView()));
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
