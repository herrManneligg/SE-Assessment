import java.util.ArrayList;

public class ClassDirector extends Person {

	private ArrayList<Course> listOfCourses;

	public ClassDirector(String name, String email) {
		super(name, email);
		listOfCourses = new ArrayList<>();
	}

//	public void createCourse(String newCourseName) {
//		listOfCourses.add(new Course(newCourseName));
//	}

	public void removeCourse(String courseToRemove) {
		boolean removed = false;
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i).getCourseName().equals(courseToRemove)) {
				listOfCourses.remove(i);
				System.out.println("The course " + courseToRemove + " has been removed.");
				removed = true;
			} else if (removed == false) {
				System.out.println("There was no course removed.");
			}
		}
	}

	public Course selectCourse(String selectedCourse) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i).getCourseName().equals(selectedCourse)) {
				return listOfCourses.get(i);
			}
		}
		System.out.println("That course does not exist.");
		return null;
	}

	public void addRequirementsToCourse(Course selectedCourse, int timeExp, String availability, String description) {
		if (selectedCourse != null) {
			selectedCourse.updateListOfRequirements(timeExp, availability, description);
		} else {
			System.err.println("Please, select an existing course.");
		}
	}

//	public void addCourseToList(String newCourse) {
//		listOfCourses.add(new Course(newCourse));
//	}

	public void removeCourseFromList(Course selectedCourse) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i) == selectedCourse) {
				listOfCourses.remove(i);
			} else {
				System.out.println("That course does not exists.");
			}
		}
	}

	public ArrayList<Course> getListOfCourses() {
		return this.listOfCourses;
	}
}
