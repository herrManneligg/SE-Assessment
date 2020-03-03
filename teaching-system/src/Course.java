import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.parser.ParseException;

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
//		if (this.assignedTeacher == null) {
//			Teacher t = null;
//			return t;
//		} else {
//			return assignedTeacher;
//		}
		// shortened code to following line> runs condition: if yes=null if no=this.assignedTeacher  // Ternary operation
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
	
	public ListOfRequirements getRequirementsList() {
		return this.requirements;
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
	
	public static ArrayList<HashMap<String, Object>> getCourses() throws ParseException, IOException {
		CourseFileHandler newCourse = new CourseFileHandler();
		return newCourse.getAll();
	}
	
	public void save(int semesterId) {
		try {
			CourseFileHandler newCourse = new CourseFileHandler();
			newCourse.create(this.name);
			HashMap<String, Object> lastRegister = newCourse.getLastRegister();
			SemesterInfoFileHandler semeserInfo = new SemesterInfoFileHandler();
			this.setId((int) lastRegister.get("id"));
			semeserInfo.addNewCourse(semesterId, (int) lastRegister.get("id"), this.requirements.getTimeExp(), this.requirements.getAvailability(), this.requirements.getBackgroundRequirement());
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

