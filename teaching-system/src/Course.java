

public class Course {

	private int id;
	private String name;
	private Teacher assignedTeacher;
	private ListOfRequirements requirements;
	private boolean approved;

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
	
	
}

