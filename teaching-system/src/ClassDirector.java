import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import org.json.simple.parser.ParseException;

public class ClassDirector extends Person {

	private ArrayList<Course> listOfCourses;

	public ClassDirector(View view) {
		super("Class Director", "class.director@gmail.com", view);
		listOfCourses = new ArrayList<Course>();
		this.showSelectedOptionFromInitScreen();
	}

	public void createCourse(String newCourseName) {
		listOfCourses.add(new Course(newCourseName));
	}
	
	public void showSelectedOptionFromInitScreen() {	
		boolean finishAction = false;
		while(!finishAction) {
			
			String message = " --------------------------\n" +
				 	   "|   Class Director View    |\n" +
				       " --------------------------\n" +
				       "--------- Actions ---------\n\n" +
				       " 1: Create New Semester\n" +
				       " 2: Open previous Semester\n" +
				       " 3: Go back\n\n" +
				       "Enter the number for your selection: ";
			
			int input = this.getViewObject().getUserInputInteger(message);
			
			try {
				if (input == 1) {
					this.createNewSemester();
				} else if(input == 2) {
					this.openSemester();
					if(this.getSemester() != null) {			
						this.actionsInsideSemester();
					}
				} else if(input == 3) {
					finishAction = true;
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				System.out.println("Enter a numerical value.");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createNewSemester() {
		this.getViewObject().printScreen("\n\n--- Enter the YEAR and SEMESTER ---\n");
		int year = Integer.valueOf(this.getViewObject().getUserInputInteger("Enter the Year: "));
		int semester = Integer.valueOf(this.getViewObject().getUserInputInteger("Enter the Semester: "));
		
		this.setSemester(new Semester(year, semester));
		SemesterInfoFileHandler fileHandler;
		HashMap<String, Object> lastSemesterInfo = new HashMap<String, Object>();
		
		try {
			fileHandler = new SemesterInfoFileHandler();
			fileHandler.create(year, semester);
			lastSemesterInfo = fileHandler.getLastSemesterRegister();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.getSemester().setDatasbaseId((int) lastSemesterInfo.get("id"));
		
		this.getViewObject().printScreen("\n\nNew Semester Created\n");
		
		String message = "\n -------------------------\n" +
				   		 "| Year %4d - Semester %2d |\n" +
				   		 " -------------------------\n\n";
		
		this.getViewObject().printScreen(String.format(message, year, semester));
		this.actionsInsideSemester();
	}
	
	public void actionsInsideSemester() {
		boolean finishAction = false;
		while(!finishAction) {
		
			String message = "\nPlease enter the number that corresponds to your role and press 'Enter':\n\n" + 
							 "   1: Create Course\n" +
							 "   2: Assign Course\n" + 
							 "   3: See courses in the list\n" +
							 "   4: Go Back\n" + 
							 "\nEnter the number for your selection: ";
			int input = Integer.valueOf(this.getViewObject().getUserInputInteger(message));
			
			try {
				if (input == 1) {
					this.createCourseAction();
				} else if(input == 2) {
					this.assignCourseToList();
				} else if(input == 3) {
					this.listCoursesInTheSystem();
				} else if(input == 4) {
					finishAction = true;
				} else {
					this.getViewObject().printScreen("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				this.getViewObject().printScreen("Wrong input");
			} catch(ParseException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createCourseAction() throws ParseException {
		String message = "\n -------------------------\n" +
		   		  		 "|       New Course       |\n" +
		   		  		 " -------------------------\n" +
		   		  		 " Write the name of the course \n" +
		   		  		 " And the list of requirements \n\n";
		this.getViewObject().printScreen(message);
		String courseName = this.getViewObject().getUserInputString("Enter the name: ");
		
		ListOfRequirements courseRequirements = addRequirementsToCourse();
		Course newCourse = new Course(courseName, courseRequirements);
		this.listOfCourses.add(newCourse);
		newCourse.save(this.getSemester().getDatasbaseId());
		
		message = " -------------------- \n" +
				  "| Created Course: " + newCourse.getCourseName() + " |\n" +
				  "| Added to the year: " + this.getSemester().getYear() + " |\n" +
				  "| semester: " + this.getSemester().getSemesterNo() + " |\n" +
				  " -------------------- ";
		
		this.getViewObject().printScreen(message);
	}
	
	private void assignCourseToList() throws IOException, ParseException {
		String message = "\n -------------------------\n" +
		   		  		 "|    List of Courses    |\n" +
		   		  		 " -------------------------\n\n";
		
		
		ArrayList<HashMap<String, Object>> courseObject = Course.getCourses();
		String courseStringTpml = "  %2d: %s    \n";
		ArrayList<HashMap<String, Object>> listOfUnassignedCourses = new ArrayList<HashMap<String, Object>>();
		
		int cont = 0;
		for(int i = 0; i < courseObject.size(); i++) {
			HashMap<String, Object> row = courseObject.get(i);
			SemesterInfoFileHandler semesterFileHandler = new SemesterInfoFileHandler();
			boolean isCourseAssigned = semesterFileHandler.isCourseAssignedToSemester(this.getSemester().getDatasbaseId(), (int) row.get("id"));
			if(!isCourseAssigned) {
				listOfUnassignedCourses.add(row);
				message += String.format(courseStringTpml, (cont + 1), row.get("name"));
				cont++;
			}
		}
		
		if(listOfUnassignedCourses.size() > 0) {
			message += "\n";
			this.getViewObject().printScreen(message);
			
			int selection = this.getViewObject().getUserInputInteger("Enter the number for your selection: ");
			this.getViewObject().printScreen("\nEnter the requirements for the course\n");
			
			ListOfRequirements courseRequirements = this.addRequirementsToCourse();
			Course tempCourse = new Course((String) listOfUnassignedCourses.get(selection - 1).get("name"), courseRequirements);
			tempCourse.setId((int) listOfUnassignedCourses.get(selection - 1).get("id"));
			this.listOfCourses.add(tempCourse);
			this.getSemester().addACourse(this.getSemester().getDatasbaseId(), tempCourse);
			
			message = " ------------------------- \n" +
					  "| Created Course: " + tempCourse.getCourseName() + " |\n" +
					  "| Added to the year: " + this.getSemester().getYear() + " |\n" +
					  "| semester: " + this.getSemester().getSemesterNo() + " |\n" +
					  " ------------------------- ";
			
			this.getViewObject().printScreen(message);
			
		} else {
			message += "There are no courses to assign\n" +
					   "Create courses\n\n";
			this.getViewObject().printScreen(message);
		}
	}
	
	private void listCoursesInTheSystem() throws ParseException, IOException {
		String message = "\n -------------------------\n" +
		   		  		 "|    List of Courses    |\n" +
		   		  		 " -------------------------\n\n";
		
		
		ArrayList<HashMap<String, Object>> courseObject = Semester.getCourses(this.getSemester().getDatasbaseId());
		String courseStringTpml = "  %2d: %s    \n";
		if(courseObject.size() > 0) {
			for(int i = 0; i < courseObject.size(); i++) {
				HashMap<String, Object> row = courseObject.get(i);
				HashMap<String, Object> course = Course.findCourseInFile((int) row.get("course_id"));
				message += String.format(courseStringTpml, (i + 1), course.get("name"));
			}
			
			message += "\n";
			this.getViewObject().printScreen(message);
			
		} else {
			message += "There are no courses yet\n" +
					   "Create courses\n\n";
			this.getViewObject().printScreen(message);
		}
	}
	
	public void removeCourse(String courseToRemove) {
		boolean removed = false;
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i).getCourseName().equals(courseToRemove)) {
				listOfCourses.remove(i);
				System.out.println("The course " + courseToRemove + " has been removed.");
				removed = true;
			} else if (removed == false) {
				System.out.println("There was no course removed.");
			}
		}
	}

	public ListOfRequirements addRequirementsToCourse() {
		int timeExp = this.getViewObject().getUserInputInteger("Experience of teacher (in years): ");
		String availability = this.getViewObject().getUserInputString("Availability: ");
		String background = this.getViewObject().getUserInputString("Teacher background: ");
		
		return new ListOfRequirements(timeExp, availability, background);
	}

	public void removeCourseFromList(Course selectedCourse) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i) == selectedCourse) {
				listOfCourses.remove(i);
			} else {
				System.out.println("That course does not exists.");
			}
		}
	}

	public ArrayList<Course> getListOfCourses() {
		return this.listOfCourses;
	}
}
