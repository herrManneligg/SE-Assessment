import java.io.PrintStream;
import java.util.InputMismatchException;

public class ClassDirectorController {
	
	private ClassDirectorView classDirectorViewObject;
	private InitialController initialController;
	private Model model;
	private PrintStream ps;
	
	public ClassDirectorController(InitialController initialController, Model model, PrintStream ps) {
		this.initialController = initialController;
		this.model = model;
		this.ps = ps;
		this.classDirectorViewObject = new ClassDirectorView(this, model, ps);
		this.showSelectedOptionFromInitScreen();
	}

	public ClassDirectorView getClassDirectorViewObject() {
		return classDirectorViewObject;
	}

	public void setClassDirectorViewObject(ClassDirectorView classDirectorViewObject) {
		this.classDirectorViewObject = classDirectorViewObject;
	}
	
	public void showSelectedOptionFromInitScreen() {	
		boolean finishAction = false;
		while(!finishAction) {
			int input = this.classDirectorViewObject.prinView();
			try {
				if (input == 1) {
					this.classDirectorViewObject.print(this.model.createdNewSemester());
					int year = Integer.valueOf(this.classDirectorViewObject.getUserInput(this.model.askYear()));
					int semester = Integer.valueOf(this.classDirectorViewObject.getUserInput(this.model.askSemester()));
					
					// Here is where the logic to create the new semester with the models and the database goes
					int semesterId = 1; // <-- this is the example ID for the new semester
					
					this.classDirectorViewObject.print(this.model.semesterCreatesMessage());
					this.showNewSemesterScreen(year, semester);
				} else if(input == 2) {
					this.classDirectorViewObject.print(this.model.selectSemester());
					int year = Integer.valueOf(this.classDirectorViewObject.getUserInput(this.model.askYear()));
					int semester = Integer.valueOf(this.classDirectorViewObject.getUserInput(this.model.askSemester()));
					
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
		this.classDirectorViewObject.print(String.format(this.model.newSemesterAction(), year, semester));
		this.classDirectorViewObject.getUserInput(this.model.classDirectorActions());
		this.actionsInsideSemester();
	}
	
	public void actionsInsideSemester() {
		boolean finishAction = false;
		while(!finishAction) {
			int input = Integer.valueOf(this.classDirectorViewObject.getUserInput(this.model.actionsInsideSemesterInfo()));
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
