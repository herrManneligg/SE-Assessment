package People;
import CourseTeaching.*;
import FileHandler.*;
import MVC.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.parser.ParseException;

public class Teacher extends Person {

	// Decided to change this to "background" instead of course. A person with
	// History of Arts may also be suitable
	// to teach History or even Geography. I think it's better to have a wee
	// description of the candidate teacher
	// background, and then the PTT Director may decide which teacher suits better
	// the position.
	
	private boolean inTraining;
	private int id;
	private int timeExperience;
	private String availability;
	// background is representing the needed requirement as single skill - Marjan
	private String background;
	private Course assignedCourse;

	public Teacher(String name, String email, int timeExperience, String availability, String background) {
		super(name, email);
		this.timeExperience = timeExperience;
		this.availability = availability;
		this.background = background;
		this.inTraining = false;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssignedCourse() {
		return assignedCourse != null ? assignedCourse.getCourseName() : this.getName() + " is not assigned to a course.";
	}

	public void setAssignedCourse(Course assignedCourse) {
		this.assignedCourse = assignedCourse;
	}

	public String getCourse() {
		return background;
	}

	public void setCourse(String course) {
		this.background = course;
	}

	public boolean isInTraining() {
		return inTraining;
	}

	public void setInTraining(boolean inTraining) {
		this.inTraining = inTraining;
	}

	public void removeTeacherFromTraining(boolean inTraining) {
		this.inTraining = inTraining;
	}

	public int getTimeExperience() {
		return timeExperience;
	}

	public void setTimeExperience(int timeExperience) {
		this.timeExperience = timeExperience;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
	public void assignCourse(int semesterId, Course course) throws IOException, org.json.simple.parser.ParseException {
		this.assignedCourse = course;
		SemesterInfoFileHandler semesterFileHandler = new SemesterInfoFileHandler();
		semesterFileHandler.assignTeacherToCourse(semesterId, course.getId(), this.id);
	}
	
	public void save() throws ParseException, IOException {
		TeacherFileHandler teacherFileHandler = new TeacherFileHandler();
		teacherFileHandler.create(this.getName(), this.getEmail(), this.timeExperience, this.background, this.availability);
	}
	
	public static ArrayList<HashMap<String, Object>> getTeachers() throws org.json.simple.parser.ParseException, IOException {
		TeacherFileHandler teachersFileHandler = new TeacherFileHandler();
		return teachersFileHandler.getAll();
	}
	
	public static HashMap<String, Object> findTeacherInFile(int teacherId) throws IOException, org.json.simple.parser.ParseException {
		TeacherFileHandler teacherFileHandler = new TeacherFileHandler();
		return teacherFileHandler.find(teacherId);
	}

}
