import java.util.ArrayList;

public class ListOfRequirements {
	private int timeExp; // years of experience
	private String availability; // the semester in which it should be available
	private Course CorrespondingCourse = null;

	public ListOfRequirements(int experience, String semester) {
		this.timeExp = experience;
		this.availability = semester;

		CorrespondingCourse = null;
	}

	public ListOfRequirements(Course associatedCourse, int experience, String semester) {
		this.timeExp = experience;
		this.availability = semester;
		this.CorrespondingCourse = associatedCourse;
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

	public void update(String semester) {
		this.availability = semester;
	}

	public void update(Integer experience, String semester) {
		this.timeExp = experience;
		this.availability = semester;
	}

	public void update(Integer experience) {
		this.timeExp = experience;
	}

	public String filter() {
		if (CorrespondingCourse == null) {
			return "- No associated subject or course" + "\n";
		} else {
			return "- The associated course is called: " +this.CorrespondingCourse.getCourseName() + "\n";
		}

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
}
