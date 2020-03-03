import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.InputMismatchException;

import org.json.simple.parser.ParseException;

public class ClassDirectorController {
	
	private View viewObject;
	private InitialController initialController;
	private Model model;
	private Semester currentSemester;
	
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
					this.createNewSemester();
				} else if(input == 2) {
					// show semester
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
	
	private void createNewSemester() {
		this.viewObject.printScreen(this.model.createdNewSemester());
		int year = Integer.valueOf(this.viewObject.getUserInputInteger(this.model.askYear()));
		int semester = Integer.valueOf(this.viewObject.getUserInputInteger(this.model.askSemester()));
		
		this.currentSemester = new Semester(year, semester);
		SemesterInfoFileHandler fileHandler;
		HashMap<String, Object> lastSemesterInfo = new HashMap<String, Object>();
		
		try {
			fileHandler = new SemesterInfoFileHandler();
			fileHandler.create(year, semester);
			lastSemesterInfo = fileHandler.getLastSemesterRegister();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.currentSemester.setDatasbaseId((int) lastSemesterInfo.get("id"));
		
		this.viewObject.printScreen(this.model.semesterCreatesMessage());
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
				System.out.println("Wrong input");
			}
		}
	}
	
	public void createCourseAction() {
		this.viewObject.printScreen(this.model.createNewCourseScreen());
		
		String courseName = this.viewObject.getUserInputString(this.model.askCourseName());
		int timeExperience = this.viewObject.getUserInputInteger(this.model.askCourseTimeExp());
		String availability = this.viewObject.getUserInputString(this.model.askCourseAvailability());
		String teacherBackground = this.viewObject.getUserInputString(this.model.askCoursebackgrounfOfTeacher());
		HashMap<String, Object> lastSemesterInfo = new HashMap<String, Object>();
		
		ListOfRequirements newListOfRequirements = new ListOfRequirements(timeExperience, availability, teacherBackground);
		Course newCourse = new Course(courseName, newListOfRequirements);
		
		CourseFileHandler courseFileHandler;
		SemesterInfoFileHandler semesterFileHandler;
		
		try {
			courseFileHandler = new CourseFileHandler();
			courseFileHandler.create(courseName);
			lastSemesterInfo = courseFileHandler.getLastRegister();
			newCourse.setId((int) lastSemesterInfo.get("id"));
			semesterFileHandler = new SemesterInfoFileHandler();
			semesterFileHandler.addNewCourse(this.currentSemester.getDatasbaseId(), newCourse.getId(), timeExperience, availability, teacherBackground);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.viewObject.printScreen(String.format(this.model.newCourseRequirementsCreatedMessage(), courseName, timeExperience, availability, teacherBackground));
		this.actionsInsideSemester();
	}

}
