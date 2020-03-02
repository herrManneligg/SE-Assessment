import java.util.ArrayList;

public class PttDirector extends Person {
	
	private ArrayList<Course> listOfCourses;
	
	public PttDirector(String name, String email) {
		super(name, email);
	}
	
	public void approve(Course approvedCourse) {
		approvedCourse.approve();
	}
	
	public void reject(Course approvedCourse) {
		approvedCourse.reject();
	}
	

}
