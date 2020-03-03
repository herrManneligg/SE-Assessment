
public class Model {
	
	private final String outOfBoundaries = "Only numerical values allowed! Please choose a numerical value between 1 and 3";
	private final String selectViewUserInputOutOfBoundaries = "That number does not correspond to the avaiable options";
	private final String selectViewUserInput = "Please, enter the number that corresponds to your role and press 'Enter'.";
	
	public Model() {
		//
	}
	
	public String getInitialViewInfo() {
		return "\nPlease enter the number that corresponds to your role and press 'Enter':\n\n" + 
				"   1: Class Director\n" + 
				"   2: Administrator\n" + 
				"   3: PTT Director\n" + 
				"   4: Close Program\n" +
				"\nEnter the number for your selection: ";
	}
	
	public String classDirectorView() {
		return "---------------------------\n" +
		 	   "|   Class Director View    |\n" +
		       "---------------------------\n" +
		       "--------- Actions ---------\n\n" +
		       " 1: Create New Semester\n" +
		       " 2: Open previous Semester\n" +
		       " 3: Go back\n\n" +
		       "Enter the number for your selection: ";
	}
	
	public String administratorView() {
		return "---------------------------\n" +
		 	   "|    Administrator View     |\n" +
		       "---------------------------\n" +
		       "--------- Actions ---------\n\n" +
		       " 1: See all teachers\n" +
		       " 2: Create Course\n" +
		       " 3: Assign a Course to a semester\n" +
		       " 4: Go back\n\n" +
		       "Enter the number for your selection: ";
	}
	
	public String pttDirectorView() {
		return "---------------------------\n" +
		 	   "|    PTT Director View     |\n" +
		       "---------------------------\n" +
		       "--------- Actions ---------\n\n" +
		       " 1: See teacher requests\n" +
		       " 2: Go back\n\n" +
		       "Enter the number for your selection: ";
	}
	
	public String createdNewSemester() {
		return "\n\n--- Enter the YEAR and SEMESTER ---\n";
	}
	
	public String selectSemester() {
		return "\nSelect a year and semester";
	}
	
	public String actionsInsideSemesterInfo() {
		return "\nPlease enter the number that corresponds to your role and press 'Enter':\n\n" + 
				"   1: Create Course\n" + 
				"   2: Assign Course\n" + 
				"   3: Go Back\n" + 
				"\nEnter the number for your selection: ";
	}
	
	public String askYear() {
		return "Enter the Year: ";
	}
	
	public String askSemester() {
		return "Enter the Semester: ";
	}
	
	public String semesterCreatesMessage() {
		return "\n\nNew Semester Created\n";
	}
	
	public String newSemesterAction() {
		return "\n-------------------------\n" +
			   "| Year %4d - Semester %2d|\n" +
			   "-------------------------\n\n";
	}
	
	public String classDirectorActions() {
		return "\nActions for Class Director\n\n" +
			   " 1: Create new course\n" +
			   " 2: Assign course to semester\n" +
			   " 3: Go back\n\n" +
			   "Enter the number for your selection: ";
	}

}
