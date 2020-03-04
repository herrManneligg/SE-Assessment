import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Semester {
	
	private int year;
	private int semesterNo;
	private int datasbaseId;
	private ArrayList<Course> listOfCourses;
	private ArrayList<Teacher> listOfTeachers;

	public Semester(int year, int semesterNo) {
		this.year = year;
		this.semesterNo = semesterNo;
		this.listOfCourses = new ArrayList<Course>();
		this.listOfTeachers = new ArrayList<Teacher>();
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public int getSemesterNo() {
		return semesterNo;
	}

	public void setSemesterNo(int semesterNo) {
		this.semesterNo = semesterNo;
	}

	public ArrayList<Course> getListOfCourses() {
		return listOfCourses;
	}

	public void setListOfCourses(ArrayList<Course> listOfCourses) {
		this.listOfCourses = listOfCourses;
	}

	public ArrayList<Teacher> getListOfTeachers() {
		return listOfTeachers;
	}

	public void setListOfTeachers(ArrayList<Teacher> listOfTeachers) {
		this.listOfTeachers = listOfTeachers;
	}

	public int getDatasbaseId() {
		return datasbaseId;
	}

	public void setDatasbaseId(int datasbaseId) {
		this.datasbaseId = datasbaseId;
	}
	
	public static ArrayList<HashMap<String, Object>> getSemesters() throws IOException, org.json.simple.parser.ParseException {
		SemesterInfoFileHandler semesterFileHandler = new SemesterInfoFileHandler();
		return semesterFileHandler.getAll();
	}
	
	public void addACourse(int semesterId, Course course) throws org.json.simple.parser.ParseException, IOException {
		this.listOfCourses.add(course);
		SemesterInfoFileHandler fileHandler = new SemesterInfoFileHandler();
		fileHandler.addNewCourse(semesterId, course.getId(), course.getRequirements().getTimeExp(), course.getRequirements().getAvailability(), course.getRequirements().getBackgroundRequirement());
	}
	
	public static ArrayList<HashMap<String, Object>> getCourses(int semesterId) throws org.json.simple.parser.ParseException, IOException {
		SemesterInfoFileHandler semesterFileHandler = new SemesterInfoFileHandler();
		return semesterFileHandler.getCoursesList(semesterId);
	}
	
	public static ArrayList<HashMap<String, Object>> getTeachers(int semesterId) throws org.json.simple.parser.ParseException, IOException {
		SemesterInfoFileHandler semesterFileHandler = new SemesterInfoFileHandler();
		return semesterFileHandler.getTeachersList(semesterId);
	}
	
	public static ArrayList<HashMap<String, Object>> getApprovedAndDeclinedCourses(int semesterId) throws org.json.simple.parser.ParseException, IOException {
		SemesterInfoFileHandler semesterFileHandler = new SemesterInfoFileHandler();
		return semesterFileHandler.getApprovedAndDeclinedCourses(semesterId);
	}
	
}
