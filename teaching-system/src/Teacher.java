import java.util.Date;

public class Teacher extends Person {
	
	private Course course;
	private boolean inTraining;
	private Date timeExperience;
	private String availability;
	
	public Teacher(String name, String email) {
		super(name, email);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public boolean isInTraining() {
		return inTraining;
	}

	public void setInTraining(boolean inTraining) {
		this.inTraining = inTraining;
	}

	public Date getTimeExperience() {
		return timeExperience;
	}

	public void setTimeExperience(Date timeExperience) {
		this.timeExperience = timeExperience;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
}
