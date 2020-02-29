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

	public void update(String name, int timeExp, String availability) {

	}

	public String getCourseInfo() {
		
		String info = "- Course: " + name + "\n";
		
		if (assignedTeacher == null) {
			info = info + "Teacher: Not assigned";
		} else {
			info = info + "- Teacher: " +  assignedTeacher.getName() + "\n";
		}
		
		if (approved = true) {
			info = info + "- Approved for teaching: Yes" + "\n";
		} else {
			info = info + "- Approved for teaching: No" + "\n";
		}
		return info;
	}
	
	public void assingTeacher(Teacher t) {
		assignedTeacher = t;
	}

	public void approve() {
		approved = true;
	}

	public void reject() {
		approved = false;
	}
	
	public Teacher getTeacher() {
		return assignedTeacher;
	}
	
	public String getCourseName() {
		return name;
	}
}
