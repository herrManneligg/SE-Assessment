import java.util.ArrayList;
import java.util.HashMap;

public class Semester {
	
	private int year;
	private int semesterNo;
	private int datasbaseId;
	private ArrayList<HashMap<String, Object>> listOfCourses;
	private ArrayList<HashMap<String, Object>> listOfTeachers;

	public Semester(int year, int semesterNo) {
		this.year = year;
		this.semesterNo = semesterNo;
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

	public ArrayList<HashMap<String, Object>> getListOfCourses() {
		return listOfCourses;
	}

	public void setListOfCourses(ArrayList<HashMap<String, Object>> listOfCourses) {
		this.listOfCourses = listOfCourses;
	}

	public ArrayList<HashMap<String, Object>> getListOfTeachers() {
		return listOfTeachers;
	}

	public void setListOfTeachers(ArrayList<HashMap<String, Object>> listOfTeachers) {
		this.listOfTeachers = listOfTeachers;
	}

	public int getDatasbaseId() {
		return datasbaseId;
	}

	public void setDatasbaseId(int datasbaseId) {
		this.datasbaseId = datasbaseId;
	}
	
}
