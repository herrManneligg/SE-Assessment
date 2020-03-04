<<<<<<< HEAD
<<<<<<< HEAD
import java.util.Date;
=======
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

>>>>>>> master

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
<<<<<<< HEAD
=======
import java.util.Date;

public class Course {

	private String name;
	private Teacher assignedTeacher;
	private ListOfRequirements requirements;
	private boolean approved;

	public Course(String name) {
		this.name = name;
		assignedTeacher = null;
		requirements = null;
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

	public void update(String name, Integer timeExp, String availability) { // updates name, experience, and
																			// availability
		if (name == null) {
			// no update of the name
		} else {
			this.name = name;
		}

		if ((timeExp == null) && (availability == null)) {
			// only Course-Name gets updated, no changes to the ListOfRequirements
		} else {
			if (this.requirements == null) {
				this.requirements = new ListOfRequirements(this, timeExp, availability);
			} else {
				if (timeExp == null) {
					this.requirements.update(availability);
				} else if (availability == null) {
					this.requirements.update(timeExp);
				} else {
					this.requirements.update(timeExp, availability);
				}
			}
		}
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
		if (this.assignedTeacher == null) {
			Teacher t = null;
			return t;
		} else {
			return assignedTeacher;
		}
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
		LoR.setCorrespondingCourse(this);

	}

}
>>>>>>> d85a8bb8071934a95bb4dc9993b05ee09b4ed8ab
=======

>>>>>>> master
