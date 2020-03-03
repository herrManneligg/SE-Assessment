import java.util.ArrayList;

public class ListOfRequirements {
	private int timeExp; // years of experience
	private String availability; // the semester in which it should be available
	private Course CorrespondingCourse = null;
	private String backgroundRequirement;

// is there any case where we would have a list of requirements without a course?

	// When a new course is created, it can be created without requirements; in that case it will have default requirements
	public ListOfRequirements(Course associatedCourse) {
		this.timeExp = 0;
		this.availability = "All academic year";
		this.CorrespondingCourse = associatedCourse;	
		this.backgroundRequirement = "No specific background required.";
	}

	// When a new course is created, we can also assign requirements straight-away
	public ListOfRequirements(Course associatedCourse, int experience, String semester, String background) {
		this.timeExp = experience;
		this.availability = semester;
		this.CorrespondingCourse = associatedCourse;
		this.backgroundRequirement = background;
	}

	public void setSemester(String semester) {
		this.availability = semester;
	}

	public void updateAllRequirements(int experience, String semester, String backgroundRequirement) {
		this.timeExp = experience;
		this.availability = semester;
		this.backgroundRequirement = backgroundRequirement;
	}

	public void setExperience(int experience) {
		this.timeExp = experience;
	}

	public int getTimeExp() {
		return timeExp;
	}

	public String getAvailability() {
		return availability;
	}

	public Course getCorrespondingCourse() {
		return CorrespondingCourse;
	}

	public void setTimeExp(int timeExp) {
		this.timeExp = timeExp;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public void setCorrespondingCourse(Course correspondingCourse) {
		CorrespondingCourse = correspondingCourse;
	}

	public String getBackgroundRequirement() {
		return backgroundRequirement;
	}

	public void setBackgroundRequirement(String backgroundRequirement) {
		this.backgroundRequirement = backgroundRequirement;
	}

	public String filter() {
		if (CorrespondingCourse == null) {
			return "- No associated subject or course" + "\n";
		} else {
			return "- The associated course is called: " + this.CorrespondingCourse.getCourseName() + "\n";
		}
	}

	public Boolean isReady(Teacher t) {
		// isReady: Checks if Teacher t is ready for a specific course based on
		// experience
		if (t == null) {
			// if no teacher object is given as a parameter
			try {
				if (this.CorrespondingCourse.getTeacher() != null) {// tries to check assigned teacher for this
					// corresponding course
					// if no teacher is assigned to the course, return false
					// if a teacher is assigned to a corresponding course
					t = this.CorrespondingCourse.getTeacher(); // set t to value of assigned teacher
					if (t.getTimeExperience() >= timeExp) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}

		} else {
			if (t.getTimeExperience() >= timeExp) {
				return true;
			} else {
				return false;
			}
		}
	}
}
