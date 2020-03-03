import java.io.PrintStream;
import java.util.InputMismatchException;

public class ClassDirectorController {
	
	private View viewObject;
	private InitialController initialController;
	private Model model;
	
	public ClassDirectorController(InitialController initialController, Model model, PrintStream ps) {
		this.initialController = initialController;
		this.model = model;
		this.viewObject = new View(ps);
		this.showSelectedOptionFromInitScreen();
	}

	public View getViewObject() {
		return this.viewObject;
	}
	
	public void showSelectedOptionFromInitScreen() {	
		boolean finishAction = false;
		while(!finishAction) {
			int input = this.viewObject.getUserInputInteger(this.model.classDirectorView());
			
			try {
				if (input == 1) {
					this.viewObject.printScreen(this.model.createdNewSemester());
					int year = Integer.valueOf(this.viewObject.getUserInputInteger(this.model.askYear()));
					int semester = Integer.valueOf(this.viewObject.getUserInputInteger(this.model.askSemester()));
					
					// Here is where the logic to create the new semester with the models and the database goes
					int semesterId = 1; // <-- this is the example ID for the new semester
					
					this.viewObject.printScreen(this.model.semesterCreatesMessage());
					this.showNewSemesterScreen(year, semester);
				} else if(input == 2) {
					this.viewObject.printScreen(this.model.selectSemester());
					int year = Integer.valueOf(this.viewObject.getUserInputInteger(this.model.askYear()));
					int semester = Integer.valueOf(this.viewObject.getUserInputInteger(this.model.askSemester()));
					
					// logic to show the current semester
					
					this.showNewSemesterScreen(year, semester);
				} else if(input == 3) {
					finishAction = true;
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				System.out.println("noob");
			}
		}
	}
	
	public void showNewSemesterScreen(int year, int semester) {
		this.viewObject.printScreen(String.format(this.model.newSemesterAction(), year, semester));
		this.viewObject.getUserInputInteger(this.model.classDirectorActions());
		this.actionsInsideSemester();
	}
	
	public void actionsInsideSemester() {
		boolean finishAction = false;
		while(!finishAction) {
			int input = Integer.valueOf(this.viewObject.getUserInputInteger(this.model.actionsInsideSemesterInfo()));
			try {
				if (input == 1) {
					System.out.println("Creating a course...");
				} else if(input == 2) {
					System.out.println("Reading courses...");
				} else if(input == 3) {
					finishAction = true;
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				System.out.println("noob");
			}
		}
	}

}
