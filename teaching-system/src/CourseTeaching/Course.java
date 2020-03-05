package CourseTeaching;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import People.*;
import FileHandler.*;

public class Course {

	private int id;
	private String name;
	private Teacher assignedTeacher;
	private ListOfRequirements requirements;
	private boolean approved;

	public Course(String name) {
		this.name = name;
		this.assignedTeacher = null;
	}
	
	public Course(String name, ListOfRequirements requirements) {
		this.name = name;
		this.assignedTeacher = null;
		this.requirements = requirements;
	}

	public String getCourseInfo() {

		String info = "- Course: " + name + "\n";

		if (assignedTeacher == null) {
			info = info + "- Teacher: Not assigned" + "\n";
		} else {
			info = info + "- Teacher: " + assignedTeacher.getName() + "\n";
		}

		if (approved == true) { //corrected from = to ==
			info = info + "- Approved for teaching: Yes" + "\n";
		} else {
			info = info + "- Approved for teaching: No" + "\n";
		}
		return info;
	}

	public String getCourseName() {
		return name;
	}

	public void updateListOfRequirements(int timeExp, String availability, String backgroundDescription) {
		
	}

	public void assingTeacher(Teacher t) {
		assignedTeacher = t;
	}

	public void approve() {
		this.approved = true;
	}

	public void reject() {
		this.approved = false;
	}

	public Teacher getTeacher() { // enables method to return null value
		return this.assignedTeacher == null ? null : this.assignedTeacher;
	}

	public String readList() { // gives back String
		String info = "";
		if (this.requirements == null) {
			info = "- No Course-Requirements are currently associated with this course" + "\n";
		} else {
			info = "- The Course-Requirements are: " + this.requirements.getTimeExp()
					+ " years of experience and it will be available in the " + this.requirements.getAvailability()
					+ "\n";
		}
		return info;
	}

	public void assignRequirementsList(ListOfRequirements LoR) {
		this.requirements = LoR;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ListOfRequirements getRequirements() {
		return requirements;
	}

	public void setRequirements(ListOfRequirements requirements) {
		this.requirements = requirements;
	}
	
	public static ArrayList<HashMap<String, Object>> getCourses() throws IOException, org.json.simple.parser.ParseException {
		CourseFileHandler newCourse = new CourseFileHandler();
		return newCourse.getAll();
	}
	
	public void save(int semesterId) throws org.json.simple.parser.ParseException {
		try {
			CourseFileHandler newCourse = new CourseFileHandler();
			newCourse.create(this.name);
			HashMap<String, Object> lastRegister = newCourse.getLastRegister();
			SemesterInfoFileHandler semeserInfo = new SemesterInfoFileHandler();
			this.setId((int) lastRegister.get("id"));
			semeserInfo.addNewCourse(semesterId, (int) lastRegister.get("id"), this.requirements.getTimeExp(), this.requirements.getAvailability(), this.requirements.getBackgroundRequirement());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, Object> findCourseInFile(int courseId) throws IOException, org.json.simple.parser.ParseException {
		CourseFileHandler courseFileHandler = new CourseFileHandler();
		return courseFileHandler.find(courseId);
	}
	
	public static void setCourseDesitionOfApproval(int semesterId, int courseId, boolean desition) throws IOException, org.json.simple.parser.ParseException {
		SemesterInfoFileHandler semesterFileHandler = new SemesterInfoFileHandler();
		semesterFileHandler.setCourseApproval(semesterId, courseId, desition);
	}
	
}

