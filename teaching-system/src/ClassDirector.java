import java.util.ArrayList;
import java.util.Date;

public class ClassDirector extends Person {

	private ArrayList<Course> listOfCourses;

	public ClassDirector(String name, String email) {
		super(name, email);

		listOfCourses = new ArrayList<>();
	}

	public Course selectCourse(Course selectedCourse) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i) == selectedCourse) {
				return listOfCourses.get(i);
			}
		}
		return null;
	}

	public void addRequerementsToCourse(Course selectedCourse, String name, Date timeExp, String availability) {

		selectedCourse.update(name, timeExp, availability);
	}

	public void addCourseToList(Course newCourse) {
		listOfCourses.add(newCourse);
	}

	public void removeCourseFromList(Course selectedCourse) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i) == selectedCourse) {
				listOfCourses.remove(i);
			} else {
				System.out.println("That course does not exists.");
			}
		}
	}
}
