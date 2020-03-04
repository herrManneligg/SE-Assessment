import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

public class Administrator extends Person {

	private ArrayList<Course> listOfCourses;
	private ArrayList<Teacher> listOfUnassignedTeachers;
	private ClassDirector classDirector;

	public Administrator(View view) {
		super("Administrator", "admin@gmail.com", view);
		this.listOfUnassignedTeachers = new ArrayList<Teacher>();
		this.listOfCourses = new ArrayList<Course>();
		try {
			this.openSemester();
			if(this.getSemester() != null) {
				this.fillListOfCourses();
				this.fillListOfTeachers();
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		this.showSelectedOptionFromScreen();
	}
	
	public Administrator(ClassDirector classDirector, View view) {
		super("Administrator", "admin@gmail.com", view);
		listOfUnassignedTeachers = new ArrayList<Teacher>();
		this.classDirector = classDirector;
		this.listOfCourses = classDirector.getListOfCourses();
	}
	
	public void showSelectedOptionFromScreen() {
		boolean finishAction = false;
		
		while(!finishAction) {
			String message = "---------------------------\n" +
				 	   		 "|    Administrator View     |\n" +
				 	   		 "---------------------------\n" +
				 	   		 "--------- Actions ---------\n\n" +
				 	   		 " 1: Add Teacher to System\n" +
				 	   		 " 2: Assign Teacher to Courses with matching requirements\n" +
				 	   		 " 3: Set training for Teachers\n" +
				 	   		 " 4: Go back to the role-selecting-view\n\n" +
				 	   		 "Enter the number for your selection and press 'Enter': ";
			int input = this.getViewObject().getUserInputInteger(message);
			int i = 1;
			
			try {
				if (input == 1) {
					this.createTeacher();
				} else if(input == 2) {
//					this.assignTeacherToCourse();
					this.getViewObject().printScreen("\nThis the assign teacher to course function\n\n");
				} else if(input == 3) {
					this.setTrainingForTeachers();
				} else if(input == 4) {
					finishAction = true;
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException | ParseException | IOException e) {
				System.out.println("An error ocurred");
			}
		}
	}

	// Getting the ArrayList of courses
	public ArrayList<Course> getListOfCourses() {
		return listOfCourses;
	}
	
	public void setTrainingForTeachers() throws IOException, ParseException {
		ArrayList<HashMap<String, Object>> listOfCourses = new ArrayList<HashMap<String, Object>>();
		try {
			listOfCourses = Semester.getApprovedAndDeclinedCourses(this.getSemester().getDatasbaseId());
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		
		String message = " ------------------ \n" +
						 "| List of Courses |\n" +
						 " ------------------ \n\n";
		
		int i = 0;
		for(HashMap<String, Object> row : listOfCourses) {
			HashMap<String, Object> course = Course.findCourseInFile((int) row.get("course_id"));
			message += " " + (i + 1) + ": Course: " + course.get("name") + " \n";
			i++;
		}
		this.getViewObject().printScreen(message);
		
		message = "\nSelect the course you want to check\n";
		int selection = this.getViewObject().getUserInputInteger(message);
		
		HashMap<String, Object> courseSelected = Course.findCourseInFile((int) listOfCourses.get(selection - 1).get("id"));
		
		message = "\n ----------------- \n" +
				  "|  Course: " + courseSelected.get("name") + " |\n";
	}
	
	public void fillListOfCourses() throws IOException, ParseException {
		ArrayList<HashMap<String, Object>> listOfCourses = Semester.getCourses(this.getSemester().getDatasbaseId());
		for(int i = 0; i < listOfCourses.size(); i++) {
			HashMap<String, Object> row = listOfCourses.get(i);
			HashMap<String, Object> courseTemp = Course.findCourseInFile((int) row.get("course_id"));
			ListOfRequirements listOfRequirements = new ListOfRequirements((int) row.get("experience"), (String) row.get("availability"), (String) row.get("backgroundRequirement"));
			Course course = new Course((String) courseTemp.get("name"), listOfRequirements);
			course.setId((int) row.get("course_id"));
			if(row.get("teacher_assigned") == null) {
				this.listOfCourses.add(course);
			}
		}
	}
	
	public void fillListOfTeachers() throws IOException, ParseException {
		ArrayList<HashMap<String, Object>> listOfTeachers = Teacher.getTeachers();
		for(int i = 0; i < listOfTeachers.size(); i++) {
			HashMap<String, Object> row = listOfTeachers.get(i);
			HashMap<String, Object> teacherTemp = Teacher.findTeacherInFile((int) row.get("id"));
			Teacher teacherObject = new Teacher((String) teacherTemp.get("name"), (String) teacherTemp.get("email"), (int) teacherTemp.get("time_experience"), (String) teacherTemp.get("availability"), (String) teacherTemp.get("background"));
			teacherObject.setId((int) row.get("id"));
			this.listOfUnassignedTeachers.add(teacherObject);
		}
	}

	// Setting the ArrayList of courses
	public void setListOfCourses(ArrayList<Course> listOfCourses) {
		this.listOfCourses = listOfCourses;
	}

	// Getting the ArrayList of unassigned teachers
	public ArrayList<Teacher> getListOfUnassignedTeachers() {
		return listOfUnassignedTeachers;
	}

	// Setting the ArrayList of unassigned teachers
	public void setListOfUnassignedTeachers(ArrayList<Teacher> listOfUnassignedTeachers) {
		this.listOfUnassignedTeachers = listOfUnassignedTeachers;
	}

	// Getting the class director
	public ClassDirector getClassDirector() {
		return classDirector;
	}

	// Setting the class director
	public void setClassDirector(ClassDirector classDirector) {
		this.classDirector = classDirector;
	}

	// Unassigning teacher from a course
	public void unassignTeacherFromCourse(Course c, Teacher t) {
		c.assingTeacher(null);
		t.setAssignedCourse(null);
	}

	// Getting information from a course
	public String getCourseInfo(Course course) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i) == course) {
				return listOfCourses.get(i).getCourseInfo();
			}
		}
		return "There is no information for the selected course.";
	}

	// Sending the teacher to training
	public void sendTeacherToTraining(Teacher teacher) {
		teacher.setInTraining(true);
	}

	// removing a teacher from training
	public void removeTeacherFromTraining(Teacher teacher) {
		teacher.setInTraining(false);
	}

	// Assigning a teacher to a course
	// getAvailableCoursesForTeacher - Marjan
	public void assignTeacherToCourse() throws ParseException, IOException {
		
		String message = "\n ----------------\n" +
						 "| Select a course |\n" +
						 " ------------------\n\n";
		String courseStringTpml = "  %2d: %s    \n";
		int i = 1;
		if(this.listOfCourses.size() <= 0) {
			message += " There is no courses \n\n";
			this.getViewObject().printScreen(message);
		} else {
			for(Course course : this.listOfCourses) {
				message += String.format(courseStringTpml, i++, course.getCourseName());
			}
			message += "\n";
			this.getViewObject().printScreen(message);
			int courseSelection = this.getViewObject().getUserInputInteger("Enter the number for your selection: ");
			
			message = "\n ----------------\n" +
					 "|  Course Details  |\n" +
					 "|  " + this.listOfCourses.get(courseSelection - 1).getCourseName() + "  |\n" +
					 " ------------------\n\n" +
					 " Experience: " + this.listOfCourses.get(courseSelection - 1).getRequirements().getTimeExp() + "\n" +
					 " Avalability: " + this.listOfCourses.get(courseSelection - 1).getRequirements().getAvailability() + "\n" +
					 " Background: " + this.listOfCourses.get(courseSelection - 1).getRequirements().getBackgroundRequirement() + "\n\n" +
					 " --- Teachers to assign --- \n";
			
			if(this.listOfUnassignedTeachers.size() > 0) {
				String teacherStringTpml = "%2d: %s | Experience: %s | Background: %s | Availability: %s \n";
				i = 1;
				for(Teacher teacher : this.listOfUnassignedTeachers) {
					message += String.format(teacherStringTpml, i++, teacher.getName(), teacher.getTimeExperience(), teacher.getBackground(), teacher.getAvailability());
				}
				message += "\nEnter the number of the teacher you want to assign: ";
				int teacherSelection = this.getViewObject().getUserInputInteger(message);
				this.listOfUnassignedTeachers.get(teacherSelection - 1).assignCourse(this.getSemester().getDatasbaseId(), this.listOfCourses.get(courseSelection - 1));
				message = "Teacher " + this.listOfUnassignedTeachers.get(teacherSelection - 1).getName() + " assigned to course " + this.listOfCourses.get(courseSelection - 1).getCourseName() + " \n\n";
			} else {
				message += "\nThere are no teachers yet, create one\n\n";
			}
			this.getViewObject().printScreen(message);
		}
	}

	// Getting a list of the teachers assigned to a course
	public String getAssignedTeachersList() {
		String list = "List of assigned teachers: \n";

		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i).getTeacher() != null) {
				list = list + " - " + listOfCourses.get(i).getCourseName() + ": "
						+ listOfCourses.get(i).getTeacher().getName() + "\n";
			}
		}
		return list;
	}

	// Getting a list of unassigned teachers
	public String getUnassignedTeachersList() {
		String list = "List of unassigned teachers: \n";

		for (int i = 0; i < listOfUnassignedTeachers.size(); i++) {
			list = list + " - " + listOfUnassignedTeachers.get(i).getName() + "\n";
		}

		return list;
	}

	// Creating a teacher in the system
	public void createTeacher() {

		String teacherName = "";
		String teacherSurname = "";
		String teacherEmail = "";
		String background = "";
		String semester = "";

		boolean isAccepted = false;
		int experience = 0;
		int av;

		Scanner in = new Scanner(System.in);

		this.getViewObject().printScreen("-- Enter the information of the new teacher --\n");
		while (!isAccepted) {
			System.out.print("Name: ");
			try {
				if (in.hasNextLine()) {

					teacherName = in.nextLine();
					if (isLetter(teacherName) == true && teacherName.length() < 15) {
						teacherName = teacherName.substring(0, 1).toUpperCase()
								+ teacherName.substring(1).toLowerCase();
						isAccepted = true;
					} else {
						System.out.println(
								"Enter a name with alphabetical values with no accents, numbers, symbols or spaces (max. 15 characters).");
					}
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a valid name.");
			}
		}

		isAccepted = false;
		while (!isAccepted) {
			System.out.print("Surname: ");
			try {
				if (in.hasNextLine()) {

					teacherSurname = in.nextLine();
					if (isLetter(teacherSurname) == true && teacherSurname.length() < 15) {
						teacherSurname = teacherSurname.substring(0, 1).toUpperCase()
								+ teacherSurname.substring(1).toLowerCase();
						isAccepted = true;
					} else {
						System.out.println(
								"Enter a surname with alphabetical values with no accents, numbers, symbols or spaces (max. 15 characters).");
					}
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a valid surname.");
			}
		}
		teacherName += " " + teacherSurname;

		isAccepted = false;
		while (!isAccepted) {
			System.out.print("Email: ");
			try {
				if (in.hasNextLine()) {
					teacherEmail = in.nextLine();
					if (isEmail(teacherEmail) == true) {
						isAccepted = true;
					} else {
						System.out.println("Enter a real email.");
					}
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a valid email.");
			}
		}

		isAccepted = false;
		while (!isAccepted) {
			System.out.print("Experience (in years): ");
			try {
				if (in.hasNextInt()) {
					experience = in.nextInt();

					if (isLetter(Integer.toString(experience)) == false && experience >= 0 && experience <= 50) {
						isAccepted = true;
						in.nextLine();
					} else {
						System.out.println("Please enter a positive value less or equals than 50 years.");
					}

				} else {
					System.out.println("Please enter the years of experience.");
					in.next();
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, years of experience.");
			}
		}

		System.out.print("When is available?\n(1) Semester I\n(2) Semester II\n(3) All academic year\n");
		isAccepted = false;
		while (!isAccepted) {

			try {
				av = in.nextInt();
				if (1 <= av && av <= 3) {
					if (av == 1) {
						semester = "Semester I";
					} else if (av == 2) {
						semester = "Semester II";
					} else if (av == 3) {
						semester = "All academic year";
					}
					isAccepted = true;
					in.nextLine();
				} else {
					System.out.println("Please, select one of the given options.");
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter the semester availability pressing 1, 2 or 3 in the keyboard.");
				in.next();
			}
		}

		System.out.print("Background description: ");
		isAccepted = false;
		while (!isAccepted) {

			try {
				if (in.hasNextLine()) {
					isAccepted = true;
					background = in.nextLine();
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a background description.");
			}
		}

		try {
			Teacher newTeacher = new Teacher(teacherName, teacherEmail, experience, semester, background);
			newTeacher.save();
			this.listOfUnassignedTeachers.add(newTeacher);
			System.out.println("\nTeacher " + teacherName + " created.\n");
		} catch (Exception e) {
			System.err.println(
					"\n Something went wrong when creating the new teacher. Please, revise the teacher details and try again.");
		}

//		in.close();
	}

	// Checking if the user input is alphabetical
	public boolean isLetter(String checkText) {
		String textUpper = checkText.toUpperCase();

		if (checkText.length() < 3) {
			return false;
		}

		for (int i = 0; i < textUpper.length(); i++) {
			if (textUpper.charAt(i) < 'A' || textUpper.charAt(i) > 'Z' || textUpper.charAt(1) == ' ') {
				return false;
			}

		}
		return true;
	}

	// Checking if the user input corresponds to an acceptable email
	public boolean isEmail(String checkText) {

		if (checkText.length() > 6) {
			int count = 0;
			for (int i = 0; i < checkText.length(); i++) {
				if (checkText.charAt(i) == '@') {
					count++;
				} else if (count >= 2) {
					return false;
				}
			}

			for (int i = 0; i < checkText.length(); i++) {
				if ((checkText.charAt(0) != '@'
						&& (checkText.charAt(i) == '@' && ((checkText.charAt(checkText.length() - 4) == '.')
								&& ((checkText.charAt(checkText.length() - 5)) != '@')
								|| ((checkText.charAt(checkText.length() - 3) == '.'
										&& (checkText.charAt(checkText.length() - 4)) != '@')))))) {
					return true;
				}
			}
		}
		return false;
	}
}
