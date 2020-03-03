import java.io.PrintStream;
import java.util.Scanner;

public class PTTView {
	
	private PTTDirectorController pttDirectorControllerObject;
	private Model modelobject;
	private PrintStream ps;

	public PTTView(Model model, PTTDirectorController controller, PrintStream ps) {
		this.pttDirectorControllerObject = controller;
		this.modelobject = model;
		this.ps = ps;
	}
	
	public int prinView() {
		return Integer.valueOf(this.getUserInput(this.modelobject.pttDirectorView()));
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
