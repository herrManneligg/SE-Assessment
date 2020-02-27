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

	public void update(String name, Date timeExp, String availability) {
		
	}

	public void approve() {
		approved = true;
	}
	
	public void reject() {
		approved = false;
	}

}
