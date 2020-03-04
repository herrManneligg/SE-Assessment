import java.io.PrintStream;
import java.util.InputMismatchException;

public class Controller {

	private View view;
	private Model model;
	private PrintStream ps;

	public Controller(PrintStream ps) {
		this.model = new Model();
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

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public PrintStream getPs() {
		return ps;
	}

	public void setPs(PrintStream ps) {
		this.ps = ps;
	}
	
	public String getInitView() {
		return this.model.getInitialViewInfo();
	}
	
	public void showSelectedScreen() {
		boolean finishAction = false;
		while(!finishAction) {
			try {
				int input = this.view.getUserInputInteger(this.model.getInitialViewInfo());
				
				if (input == 1) {
					new ClassDirector(this.view);
				} else if (input == 2) {
					new Administrator(this.view);
				} else if (input == 3) {
					new PttDirector(this.view);
				} else if (input == 4) {
					System.out.println("Good bye!");
					System.exit(0);
				} else {
					System.out.println("Enter a numerical value within the range");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("noob");
			}
			
		}
	}
	
}
