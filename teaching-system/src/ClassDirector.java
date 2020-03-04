import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import org.json.simple.parser.ParseException;

public class ClassDirector {

	private ArrayList<Course> listOfCourses;
	private View viewObject; 
	private Semester semester;

	public ClassDirector(View view) {
		this.viewObject = view;
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
			
			int input = this.viewObject.getUserInputInteger(message);
			
			try {
				if (input == 1) {
					this.createNewSemester();
				} else if(input == 2) {
					
					message = "\n ----------------\n" +
							  "|    Semesters   |\n" +
							  " ----------------\n\n";
					
					ArrayList<HashMap<String, Object>> semesters = Semester.getSemesters();
					String semesterTpml = " %1d: year: %s - semester: %1d \n";
					for(int i = 0; i < semesters.size(); i++) {
						HashMap<String, Object> row = semesters.get(i);
						message += String.format(semesterTpml, (i + 1), row.get("year"), row.get("semester_no"));
					}
					message += "\n";
					this.viewObject.printScreen(message);
					int selection = this.viewObject.getUserInputInteger("Enter the number of the semester you want to see: ");
					
					this.setSemesterInfo(semesters.get(selection - 1));
					
					message = "\n -------------------------\n" +
					   		  "| Year %4d - Semester %2d |\n" +
					   		  " -------------------------\n\n";
			
					this.viewObject.printScreen(String.format(message, this.semester.getYear(), this.semester.getSemesterNo()));
					this.actionsInsideSemester();
					
				} else if(input == 3) {
					finishAction = true;
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				System.out.println("noob");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setSemesterInfo(HashMap<String, Object> selectedSemester) {
		this.semester = new Semester((int) selectedSemester.get("year"), (int) selectedSemester.get("semester_no"));
		this.semester.setDatasbaseId((int) selectedSemester.get("id"));
	}
	
	private void createNewSemester() {
		this.viewObject.printScreen("\n\n--- Enter the YEAR and SEMESTER ---\n");
		int year = Integer.valueOf(this.viewObject.getUserInputInteger("Enter the Year: "));
		int semester = Integer.valueOf(this.viewObject.getUserInputInteger("Enter the Semester: "));
		
		this.semester = new Semester(year, semester);
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
		
		this.semester.setDatasbaseId((int) lastSemesterInfo.get("id"));
		
		this.viewObject.printScreen("\n\nNew Semester Created\n");
		
		String message = "\n -------------------------\n" +
				   		 "| Year %4d - Semester %2d |\n" +
				   		 " -------------------------\n\n";
		
		this.viewObject.printScreen(String.format(message, year, semester));
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
			int input = Integer.valueOf(this.viewObject.getUserInputInteger(message));
			
			try {
				if (input == 1) {
					message = "\n -------------------------\n" +
					   		  "|       New Course       |\n" +
					   		  " -------------------------\n" +
							   " Write the name of the course \n" +
							   " And the list of requirements \n\n";
					this.viewObject.printScreen(message);
					String courseName = this.viewObject.getUserInputString("Enter the name: ");
					int timeExp = this.viewObject.getUserInputInteger("Experience of teacher: ");
					String availability = this.viewObject.getUserInputString("Availability: ");
					String background = this.viewObject.getUserInputString("Teacher background: ");
					
					ListOfRequirements courseRequirements = new ListOfRequirements(timeExp, availability, background);
					Course newCourse = new Course(courseName, courseRequirements);
					this.listOfCourses.add(newCourse);
					newCourse.save(this.semester.getDatasbaseId());
					
					this.viewObject.printScreen("\nCourse " + newCourse.getCourseName() + " added to the list\n");
					
				} else if(input == 2) {
					message = "\n -------------------------\n" +
					   		  "|    List of Courses    |\n" +
					   		  " -------------------------\n\n";
					
					
					ArrayList<HashMap<String, Object>> courseObject = Course.getCourses();
					String courseStringTpml = "  %2d: %s    \n";
					if(courseObject.size() > 0) {
						for(int i = 0; i < courseObject.size(); i++) {
							HashMap<String, Object> row = courseObject.get(i);
							message += String.format(courseStringTpml, (i + 1), row.get("name"));
						}
						
						message += "\n";
						this.viewObject.printScreen(message);

						
						int selection = this.viewObject.getUserInputInteger("Enter the number for your selection: ");
						
						this.viewObject.printScreen("\nEnter the requirements for the course\n");
						int timeExp = this.viewObject.getUserInputInteger("Experience of teacher: ");
						String availability = this.viewObject.getUserInputString("Availability: ");
						String background = this.viewObject.getUserInputString("Teacher background: ");
						
						ListOfRequirements courseRequirements = new ListOfRequirements(timeExp, availability, background);
						Course tempCourse = new Course((String) courseObject.get(selection - 1).get("name"), courseRequirements);
						tempCourse.setId((int) courseObject.get(selection - 1).get("id"));
						this.listOfCourses.add(tempCourse);
						this.semester.addACourse(this.semester.getDatasbaseId(), tempCourse);
						
						this.viewObject.printScreen("\nCourse " + this.listOfCourses.get(selection - 1).getCourseName() + " was added to the list\n");
						
					} else {
						message += "There are no courses yet\n" +
								   "Create courses\n\n";
						this.viewObject.printScreen(message);
					}
				
				} else if(input == 3) {
					message = "\n -------------------------\n" +
					   		  "|    List of Courses    |\n" +
					   		  " -------------------------\n\n";
					
					
					ArrayList<HashMap<String, Object>> courseObject = Semester.getCourses(this.semester.getDatasbaseId());
					String courseStringTpml = "  %2d: %s    \n";
					if(courseObject.size() > 0) {
						for(int i = 0; i < courseObject.size(); i++) {
							HashMap<String, Object> row = courseObject.get(i);
							HashMap<String, Object> course = Course.findCourseInFile((int) row.get("course_id"));
							message += String.format(courseStringTpml, (i + 1), course.get("name"));
						}
						
						message += "\n";
						this.viewObject.printScreen(message);
						
					} else {
						message += "There are no courses yet\n" +
								   "Create courses\n\n";
						this.viewObject.printScreen(message);
					}
				} else if(input == 4) {
					finishAction = true;
				} else {
					this.viewObject.printScreen("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				this.viewObject.printScreen("Wrong input");
			} catch(ParseException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createCourseAction() {
		String message = "\n--- NEW COURSE ---\n\n" +
				   		 " Write the name of the course \n" +
				   		 " And the list of requirements \n\n";
		this.viewObject.printScreen(message);
		
		String courseName = this.viewObject.getUserInputString("Course Name: ");
		int timeExperience = this.viewObject.getUserInputInteger("Time of Experience: ");
		String availability = this.viewObject.getUserInputString("Availability: ");
		String teacherBackground = this.viewObject.getUserInputString("Background of teacher: ");
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
			semesterFileHandler.addNewCourse(this.semester.getDatasbaseId(), newCourse.getId(), timeExperience, availability, teacherBackground);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		message = " --- New Course Created --- " +
				  " Course Name: %s \n" +
				  " Experience: %2d \n" + 
				  " Availability: %s \n" + 
				  " Background: %s";
		
		this.viewObject.printScreen(String.format(message, courseName, timeExperience, availability, teacherBackground));
		this.actionsInsideSemester();
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

	public Course selectCourse(String selectedCourse) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i).getCourseName().equals(selectedCourse)) {
				return listOfCourses.get(i);
			}
		}
		System.out.println("That course does not exist.");
		return null;
	}

	public void addRequirementsToCourse(Course selectedCourse, int timeExp, String availability, String description) {
		if (selectedCourse != null) {
			selectedCourse.updateListOfRequirements(timeExp, availability, description);
		} else {
			System.err.println("Please, select an existing course.");
		}
	}

	public void addCourseToList(String newCourse) {
		listOfCourses.add(new Course(newCourse));
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
