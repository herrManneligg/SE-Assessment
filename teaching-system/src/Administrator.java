import java.util.ArrayList;

public class Administrator extends Person {
	
	private ArrayList<Course> listOfCourses;
	
	public Administrator(String name, String email) {
		super(name, email);
	}
	
	public String getCourseInfo(Course course) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i) == course) {
				return listOfCourses.get(i).getCourseInfo();
			}
		}
		return "There is no information for the selected course.";
	}
	
	public void sendTeacherToTraining(Teacher teacher) {
		
	}
	
	public void assignTeacherToCourse(Course course, Teacher teacher) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i) == course) {
				listOfCourses.get(i).assingTeacher(teacher);
			}
		}
	}
	
	public String getTeachersList() {
		String list = "";
	
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i).getTeacher() != null) {
				list = list + " - " + listOfCourses.get(i).getCourseName() + ": " + listOfCourses.get(i).getTeacher().getName() + "\n";
			}
		}
		return list;
	}
}
