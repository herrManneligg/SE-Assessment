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
	
	public String getCourseName() {
		return name;
	}
	
	public void update(String name, Integer timeExp, String availability) { //updates name, experience, and availability
		this.name = name;
		if (this.requirements == null) {
			this.requirements = new ListOfRequirements(this, timeExp, availability);
		} else {
			if (timeExp == null) {
				this.requirements.update(availability);
			}else if (availability == null) {
				this.requirements.update(timeExp);
			}else {
				this.requirements.update(timeExp, availability);
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

	public Teacher getTeacher() { //enables method to return null value
		if (this.assignedTeacher == null) {
			return null;
		} else {
			return assignedTeacher;
		}
	}
	
	public String readList() { //gives back String
		String info = "";
		if(this.requirements == null) {
			
		}else {
			info = "The Course-Requirements are: " + this.requirements.getTimeExp() + " years of experience and it will be available in the" + this.requirements.getAvailability();
		}
		return info;

	}
}
