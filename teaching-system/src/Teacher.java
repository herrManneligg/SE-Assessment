import java.io.IOException;
import java.util.Date;

import org.json.simple.parser.ParseException;

public class Teacher extends Person {

	// Decided to change this to "background" instead of course. A person with
	// History of Arts may also be suitable
	// to teach History or even Geography. I think it's better to have a wee
	// description of the candidate teacher
	// background, and then the PTT Director may decide which teacher suits better
	// the position.
	
	private boolean inTraining;
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

	public String getAssignedCourse() {
		return assignedCourse != null ? assignedCourse.getCourseName() : name + " is not assigned to a course.";
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
	
	public void save() throws ParseException, IOException {
		TeacherFileHandler teacherFileHandler = new TeacherFileHandler();
		teacherFileHandler.create(this.name, this.email, this.timeExperience, this.background, this.availability);
	}

}
