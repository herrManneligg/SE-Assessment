import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import org.json.simple.parser.ParseException;

public class PttDirector extends Person {
	
	private ArrayList<Course> listOfCourses;
	
	public PttDirector(View view) {
		super("PTT Director", "ptt.director@gmail.com", view);
		this.showSelectedOptionFromScreen();
	}
	
	public void approve(Course approvedCourse) {
		approvedCourse.approve();
	}
	
	public void reject(Course approvedCourse) {
		approvedCourse.reject();
	}
	
	public void showSelectedOptionFromScreen() {
		boolean finishAction = false;
		while(!finishAction) {
			String message = "---------------------------\n" +
				 	   		 "|    PTT Director View     |\n" +
				 	   		 "---------------------------\n" +
				 	   		 "--------- Actions ---------\n\n" +
				 	   		 " 1: See teacher requests\n" +
				 	   		 " 2: Go back\n\n" +
				 	   		 "Enter the number for your selection: ";
			int input = this.getViewObject().getUserInputInteger(message);
			try {
				if (input == 1) {
					this.openSemester();
					this.showTeacherRequests();
				} else if(input == 2) {
					finishAction = true;
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException | IOException | ParseException e) {
				System.out.println("Enter a numerical value.");
			}
		}
	}
	
	public void fillListOfCourses() throws IOException, ParseException {
		ArrayList<HashMap<String, Object>> listOfCourses = Semester.getCourses(this.getSemester().getDatasbaseId());
		this.listOfCourses = new ArrayList<Course>();
		for(int i = 0; i < listOfCourses.size(); i++) {
			HashMap<String, Object> row = listOfCourses.get(i);
			if(row.get("teacher_assigned") != null) {
				HashMap<String, Object> courseTemp = Course.findCourseInFile((int) row.get("course_id"));
				ListOfRequirements listOfRequirements = new ListOfRequirements((int) row.get("experience"), (String) row.get("availability"), (String) row.get("backgroundRequirement"));
				Course course = new Course((String) courseTemp.get("name"), listOfRequirements);
				course.setId((int) row.get("course_id"));
				HashMap<String, Object> teacherTemp = Teacher.findTeacherInFile((int) row.get("teacher_assigned"));
				Teacher teacherObject = new Teacher((String) teacherTemp.get("name"), (String) teacherTemp.get("email"), (int) teacherTemp.get("time_experience"), (String) teacherTemp.get("availability"), (String) teacherTemp.get("background"));
				course.assingTeacher(teacherObject);
				this.listOfCourses.add(course);
			}
		}
	}
	
	private void showTeacherRequests() {
		try {
			this.fillListOfCourses();
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String message = "---------------------------\n" +
	 	   		 "|    Teacher Requests     |\n" +
	 	   		 "---------------------------\n\n";
		
		if(this.listOfCourses.size() > 0) {
				
			String courseTmpl = " %2d: %s \n";
			int i = 1;
			for(Course course : this.listOfCourses) {
				message += String.format(courseTmpl, i++, course.getCourseName());
			}
			message += "\n";   		 
			message += "Enter the number for your selection and press 'Enter': ";
			int input = this.getViewObject().getUserInputInteger(message);
			
			message = "---------------------------\n" +
		 	   		 "|    " + this.listOfCourses.get(input - 1).getCourseName() + "     |\n" +
		 	   		 "---------------------------\n" +
					 "| Experience: " + this.listOfCourses.get(input - 1).getRequirements().getTimeExp() + " |\n" +
					 "| Availability: " + this.listOfCourses.get(input - 1).getRequirements().getAvailability() + " |\n" +
					 "| Background: " + this.listOfCourses.get(input - 1).getRequirements().getBackgroundRequirement() + " |\n" +
					 " -------------------------- \n";
			
			Teacher teacher = this.listOfCourses.get(input - 1).getTeacher();
			
			message += " -------------------------- \n" +
					 "|      Teacher Proposed    |\n" +
					 " ---------------------------\n" + 
					 "| Name: " + teacher.getName() + " |\n" +
					 "| Experience: " + teacher.getTimeExperience() + " |\n" +
					 "| Availability: " + teacher.getAvailability() + " |\n" +
					 "| Background: " + teacher.getBackground() + " |\n" +
					 " -------------------------- \n\n";
			this.getViewObject().printScreen(message);
			
			boolean action = false;
			String response = "", messageOfDesition = "";
			boolean responseInBoolean = false;
			while(!action) {
				response = this.getViewObject().getUserInputString("Do you approve this? (Y|N): ");
				
				if(response.toUpperCase().equals("Y")) {
					responseInBoolean = true;
					action = true;
					messageOfDesition = "\nTeacher Request Approved\n";
				} else if(response.toUpperCase().equals("N")) {
					responseInBoolean = false;
					action = true;
					messageOfDesition = "\nTeacher Request Declined\n";
				} else {
					this.getViewObject().printScreen("\n Please enter 'Y' for yes or 'N' for no \n\n");
				}
			}
			
			
			try {
				Course.setCourseDesitionOfApproval(this.getSemester().getDatasbaseId(), this.listOfCourses.get(input - 1).getId(), responseInBoolean);
				this.getViewObject().printScreen(messageOfDesition);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			message += " There are no requests \n\n";
			this.getViewObject().printScreen(message);
		}
		
	}
	
}
