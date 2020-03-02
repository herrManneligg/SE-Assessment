import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class SemesterInfoFileHandler extends FileHandler {

	public SemesterInfoFileHandler() throws IOException {
		super();
	}
	
	public ArrayList<HashMap<String, Object>> getAll() throws ParseException, IOException {
		JSONArray semestreInfo = this.readDatabaseFile("semester_info");
		Iterator<?> it = semestreInfo.iterator();
		
		ArrayList<HashMap<String, Object>> semestreInfoList = new ArrayList<HashMap<String, Object>>();
		
		while(it.hasNext()) {
			HashMap<String, Object> row = new HashMap<String, Object>();
			JSONObject object = (JSONObject) it.next();
			
			row.put("id", (int) (long) object.get("id"));
			row.put("year", (int) (long) object.get("year"));
			row.put("semester_no", (int) (long) object.get("semester_no"));
			
			JSONArray teachers_list = (JSONArray) object.get("teachers_list");
			ArrayList<HashMap<String, Object>> teachersList = new ArrayList<HashMap<String, Object>>();
			for(int i = 0; i < teachers_list.size(); i++) {
				HashMap<String, Object> teachers_hashmap = new HashMap<String, Object>();
				JSONObject current_teacher = (JSONObject) teachers_list.get(i);
				teachers_hashmap.put("teacher_id", (int) (long) current_teacher.get("teacher_id"));
				teachers_hashmap.put("trained", (boolean) current_teacher.get("trained"));
				teachersList.add(teachers_hashmap);
			}
			row.put("teachers_list", teachersList);
			
			JSONArray courses_list = (JSONArray) object.get("courses_list");
			ArrayList<HashMap<String, Object>> coursesList = new ArrayList<HashMap<String, Object>>();
			for(int i = 0; i < courses_list.size(); i++) {
				HashMap<String, Object> courses_hashmap = new HashMap<String, Object>();
				JSONObject current_course = (JSONObject) courses_list.get(i);
				courses_hashmap.put("course_id", (int) (long) current_course.get("course_id"));
				if(current_course.get("teacher_assigned") != null) {					
					courses_hashmap.put("teacher_assigned", (int) (long) current_course.get("teacher_assigned"));
				} else {
					courses_hashmap.put("teacher_assigned", current_course.get("teacher_assigned"));
				}
				courses_hashmap.put("approved", (boolean) current_course.get("approved"));
				courses_hashmap.put("experience", (int) (long) current_course.get("experience"));
				courses_hashmap.put("grade", (String) current_course.get("grade"));
				courses_hashmap.put("course", (String) current_course.get("course"));
				courses_hashmap.put("notes", (String) current_course.get("notes"));
				coursesList.add(courses_hashmap);
			}
			row.put("courses_list", coursesList);
			
			semestreInfoList.add(row);
		}
		
		return semestreInfoList;
	}
	
	@SuppressWarnings("unchecked")
	public void create(int year, int semester_no) throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> semestersList = this.getAll();
		int lastIndex = semestersList.size() + 1;
		
		JSONArray semesterInfoArray = new JSONArray();
		JSONArray coursesArray = this.readDatabaseFile("courses"); 
		JSONArray teachersArray = this.readDatabaseFile("teachers");
		
		for(HashMap<String, Object> teacher : semestersList) {
			JSONObject tempSemester = new JSONObject();
			tempSemester.put("id", teacher.get("id"));
			tempSemester.put("year", teacher.get("year"));
			tempSemester.put("semester_no", teacher.get("semester_no"));
			tempSemester.put("teachers_list", new JSONArray());
			tempSemester.put("courses_list", new JSONArray());
			semesterInfoArray.add(tempSemester);
		}
		
		JSONObject tempSemester = new JSONObject();
		tempSemester.put("id", lastIndex);
		tempSemester.put("year", year);
		tempSemester.put("semester_no", semester_no);
		tempSemester.put("teachers_list", new JSONArray());
		tempSemester.put("courses_list", new JSONArray());
		semesterInfoArray.add(tempSemester);
		
		JSONObject databaseObj = new JSONObject();
		databaseObj.put("courses", coursesArray);
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);
		
		@SuppressWarnings("resource")
		FileWriter file = new FileWriter(this.getPath());
		file.write(databaseObj.toJSONString());
		file.flush();
	}
	
	public HashMap<String, Object> find(int semesterId) throws IOException, ParseException {
		ArrayList<HashMap<String, Object>> semesterList = this.getAll();
		HashMap<String, Object> selectedSemester = new HashMap<String, Object>();
		for(HashMap<String, Object> semester : semesterList) {
			if(semesterId == (int) semester.get("id")) {
				selectedSemester.put("id", semester.get("id"));
				selectedSemester.put("year", semester.get("year"));
				selectedSemester.put("semester_no", semester.get("semester_no"));
				selectedSemester.put("teachers_list", semester.get("teachers_list"));
				selectedSemester.put("courses_list", semester.get("courses_list"));
			}
		}
		return selectedSemester;
	}
	
	@SuppressWarnings("unchecked")
	public void update(int semesterId, int year, int semester_no) throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> semesterList = this.getAll();
		int lastIndex = semesterList.size() + 1;
		
		JSONArray semesterInfoArray = new JSONArray();
		JSONArray coursesArray = this.readDatabaseFile("courses");
		JSONArray teachersArray = this.readDatabaseFile("teachers");
		
		for(HashMap<String, Object> semester : semesterList) {
			JSONObject tempSemester = new JSONObject();
			if(semesterId == (int) semester.get("id")) {
				tempSemester.put("id", (int) semester.get("id"));
				tempSemester.put("year", year);
				tempSemester.put("semester_no", semester_no);
				tempSemester.put("teachers_list", semester.get("teachers_list"));
				tempSemester.put("courses_list", semester.get("courses_list"));
			} else { 
				tempSemester.put("id", (int) semester.get("id"));
				tempSemester.put("year", (String) semester.get("year"));
				tempSemester.put("semester_no", (String) semester.get("semester_no"));
				tempSemester.put("teachers_list", semester.get("teachers_list"));
				tempSemester.put("courses_list", semester.get("courses_list"));
			}
			semesterInfoArray.add(tempSemester);
		}
		
		JSONObject databaseObj = new JSONObject();
		databaseObj.put("courses", coursesArray);
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);
		
		@SuppressWarnings("resource")
		FileWriter file = new FileWriter(this.getPath());
		file.write(databaseObj.toJSONString());
		file.flush();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> getTeachersList(int semesterId) throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> semesterList = this.getAll();
		ArrayList<HashMap<String, Object>> selectedSemesterTeachers = new ArrayList<HashMap<String, Object>>();
		for(HashMap<String, Object> semester : semesterList) {
			if(semesterId == (int) semester.get("id")) {
				selectedSemesterTeachers = (ArrayList<HashMap<String, Object>>) semester.get("teachers_list");
			}
		}		
		return selectedSemesterTeachers;
	}
	
	@SuppressWarnings("unchecked")
	public void addNewTeacher(int semesterId, int teacherId) throws ParseException, IOException {
		try {
			
			TeacherFileHandler teacher = new TeacherFileHandler();
			if(teacher.find(teacherId).size() <= 0) {
				throw new IllegalArgumentException("Teacher ID: " + teacherId + " does not exists");
			}
			
			ArrayList<HashMap<String, Object>> semesterList = this.getAll();
			int lastIndex = semesterList.size() + 1;
			
			JSONArray semesterInfoArray = new JSONArray();
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");
			
			for(HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				if(semesterId == (int) semester.get("id")) {
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					
					ArrayList<HashMap<String, Object>> assignedTeachers = (ArrayList<HashMap<String, Object>>) semester.get("teachers_list");
					JSONArray assignedTeachersArray = new JSONArray();
					for(int i = 0; i < assignedTeachers.size(); i++) {
						JSONObject assignedTeachersObject = new JSONObject();
						HashMap<String, Object> currentTeacher = assignedTeachers.get(i);
						if((int) currentTeacher.get("teacher_id") == teacherId) {
							throw new IllegalArgumentException("The teacher is already assigned");
						}
						assignedTeachersObject.put("teacher_id", currentTeacher.get("teacher_id"));
						assignedTeachersObject.put("trained", currentTeacher.get("trained"));
						assignedTeachersArray.add(assignedTeachersObject);					
					}
					
					JSONObject assignedTeachersObject = new JSONObject();
					assignedTeachersObject.put("teacher_id", teacherId);
					assignedTeachersObject.put("trained", false);
					assignedTeachersArray.add(assignedTeachersObject);
					tempSemester.put("teachers_list", assignedTeachersArray);
					
					tempSemester.put("courses_list", semester.get("courses_list"));
				} else { 
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));
					tempSemester.put("courses_list", semester.get("courses_list"));
				}
				semesterInfoArray.add(tempSemester);
			}
			
			JSONObject databaseObj = new JSONObject();
			databaseObj.put("courses", coursesArray);
			databaseObj.put("teachers", teachersArray);
			databaseObj.put("semester_info", semesterInfoArray);
			
			@SuppressWarnings("resource")
			FileWriter file = new FileWriter(this.getPath());
			file.write(databaseObj.toJSONString());
			file.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setTeacherTraining(int semesterId, int teacherId, boolean isTraining) throws ParseException, IOException {
		try {
			
			TeacherFileHandler teacher = new TeacherFileHandler();
			if(teacher.find(teacherId).size() <= 0) {
				throw new IllegalArgumentException("Teacher ID: " + teacherId + " does not exists");
			}
			
			ArrayList<HashMap<String, Object>> semesterList = this.getAll();
			int lastIndex = semesterList.size() + 1;
			
			JSONArray semesterInfoArray = new JSONArray();
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");
			
			for(HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				if(semesterId == (int) semester.get("id")) {
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					
					ArrayList<HashMap<String, Object>> assignedTeachers = (ArrayList<HashMap<String, Object>>) semester.get("teachers_list");
					JSONArray assignedTeachersArray = new JSONArray();
					for(int i = 0; i < assignedTeachers.size(); i++) {
						JSONObject assignedTeachersObject = new JSONObject();
						HashMap<String, Object> currentTeacher = assignedTeachers.get(i);
						if((int) currentTeacher.get("teacher_id") == teacherId) {
							assignedTeachersObject.put("teacher_id", currentTeacher.get("teacher_id"));
							assignedTeachersObject.put("trained", isTraining);
						} else {
							assignedTeachersObject.put("teacher_id", currentTeacher.get("teacher_id"));
							assignedTeachersObject.put("trained", currentTeacher.get("trained"));
						}
						assignedTeachersArray.add(assignedTeachersObject);
					}
					tempSemester.put("teachers_list", assignedTeachersArray);
					
					tempSemester.put("courses_list", semester.get("courses_list"));
				} else { 
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));
					tempSemester.put("courses_list", semester.get("courses_list"));
				}
				semesterInfoArray.add(tempSemester);
			}
			
			JSONObject databaseObj = new JSONObject();
			databaseObj.put("courses", coursesArray);
			databaseObj.put("teachers", teachersArray);
			databaseObj.put("semester_info", semesterInfoArray);
			
			@SuppressWarnings("resource")
			FileWriter file = new FileWriter(this.getPath());
			file.write(databaseObj.toJSONString());
			file.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public ArrayList<HashMap<String, Object>> getCoursesList(int semesterId) throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> semesterList = this.getAll();
		ArrayList<HashMap<String, Object>> selectedSemesterCourses = new ArrayList<HashMap<String, Object>>();
		for(HashMap<String, Object> semester : semesterList) {
			if(semesterId == (int) semester.get("id")) {
				selectedSemesterCourses = (ArrayList<HashMap<String, Object>>) semester.get("courses_list");
			}
		}		
		return selectedSemesterCourses;
	}
	
	@SuppressWarnings("unchecked")
	public void addNewCourse(int semesterId, int courseId, int experience, String grade, String courseInfo, String notes) throws ParseException, IOException {
		try {
			
			CourseFileHandler course = new CourseFileHandler();
			if(course.find(courseId).size() <= 0) {
				throw new IllegalArgumentException("Course ID: " + courseId + " does not exists");
			}
			
			ArrayList<HashMap<String, Object>> semesterList = this.getAll();
			int lastIndex = semesterList.size() + 1;
			
			JSONArray semesterInfoArray = new JSONArray();
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");
			for(HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				if(semesterId == (int) semester.get("id")) {
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					
					ArrayList<HashMap<String, Object>> assignedCourses = (ArrayList<HashMap<String, Object>>) semester.get("courses_list");
					JSONArray assignedCoursesArray = new JSONArray();
					for(int i = 0; i < assignedCourses.size(); i++) {
						JSONObject assignedTeachersObject = new JSONObject();
						HashMap<String, Object> currentCourse = assignedCourses.get(i);
						if((int) currentCourse.get("course_id") == courseId) {
							throw new IllegalArgumentException("The course is already added");
						}
						assignedTeachersObject.put("course_id", currentCourse.get("course_id"));
						assignedTeachersObject.put("teacher_assigned", currentCourse.get("teacher_assigned"));
						assignedTeachersObject.put("approved", currentCourse.get("approved"));
						assignedTeachersObject.put("experience", currentCourse.get("experience"));
						assignedTeachersObject.put("grade", currentCourse.get("grade"));
						assignedTeachersObject.put("course", currentCourse.get("course"));
						assignedTeachersObject.put("notes", currentCourse.get("notes"));
						assignedCoursesArray.add(assignedTeachersObject);					
					}
					
					JSONObject assignedTeachersObject = new JSONObject();
					assignedTeachersObject.put("course_id", courseId);
					assignedTeachersObject.put("teacher_assigned", null);
					assignedTeachersObject.put("approved", false);
					assignedTeachersObject.put("experience", experience);
					assignedTeachersObject.put("grade", grade);
					assignedTeachersObject.put("course", courseInfo);
					assignedTeachersObject.put("notes", notes);
					assignedCoursesArray.add(assignedTeachersObject);
					tempSemester.put("courses_list", assignedCoursesArray);
					
					tempSemester.put("teachers_list", semester.get("teachers_list"));
				} else { 
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));
					tempSemester.put("courses_list", semester.get("courses_list"));
				}
				semesterInfoArray.add(tempSemester);
			}
			
			JSONObject databaseObj = new JSONObject();
			databaseObj.put("courses", coursesArray);
			databaseObj.put("teachers", teachersArray);
			databaseObj.put("semester_info", semesterInfoArray);
			
			@SuppressWarnings("resource")
			FileWriter file = new FileWriter(this.getPath());
			file.write(databaseObj.toJSONString());
			file.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void assignTeacherToCourse(int semesterId, int courseId, int teacherId) throws ParseException, IOException {
		try {
			
			CourseFileHandler course = new CourseFileHandler();
			if(course.find(courseId).size() <= 0) {
				throw new IllegalArgumentException("Course ID: " + courseId + " does not exists");
			}
			
			TeacherFileHandler teacher = new TeacherFileHandler();
			if(teacher.find(teacherId).size() <= 0) {
				throw new IllegalArgumentException("Teacher ID: " + teacherId + " does not exists");
			}
						
			ArrayList<HashMap<String, Object>> semesterList = this.getAll();
			int lastIndex = semesterList.size() + 1;
			
			JSONArray semesterInfoArray = new JSONArray();
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");
			
			for(HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				if(semesterId == (int) semester.get("id")) {
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));
					
					ArrayList<HashMap<String, Object>> assignedCourses = (ArrayList<HashMap<String, Object>>) semester.get("courses_list");
					JSONArray assignedCoursesArray = new JSONArray();
					for(int i = 0; i < assignedCourses.size(); i++) {
						JSONObject assignedCoursesObject = new JSONObject();
						HashMap<String, Object> currentCourse = assignedCourses.get(i);
						assignedCoursesObject.put("course_id", currentCourse.get("course_id"));
						assignedCoursesObject.put("experience", currentCourse.get("experience"));
						assignedCoursesObject.put("approved", currentCourse.get("approved"));
						assignedCoursesObject.put("grade", currentCourse.get("grade"));
						assignedCoursesObject.put("course", currentCourse.get("course"));
						assignedCoursesObject.put("notes", currentCourse.get("notes"));
						if((int) currentCourse.get("course_id") == courseId) {
							assignedCoursesObject.put("teacher_assigned", teacherId);
						} else {
							assignedCoursesObject.put("teacher_assigned", currentCourse.get("teacher_assigned"));
						}
						assignedCoursesArray.add(assignedCoursesObject);
					}
					tempSemester.put("courses_list", assignedCoursesArray);
					
				} else { 
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));
					tempSemester.put("courses_list", semester.get("courses_list"));
				}
				semesterInfoArray.add(tempSemester);
			}
			
			JSONObject databaseObj = new JSONObject();
			databaseObj.put("courses", coursesArray);
			databaseObj.put("teachers", teachersArray);
			databaseObj.put("semester_info", semesterInfoArray);
			
			@SuppressWarnings("resource")
			FileWriter file = new FileWriter(this.getPath());
			file.write(databaseObj.toJSONString());
			file.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setCourseApproval(int semesterId, int courseId, boolean isApproved) throws ParseException, IOException {
		try {
			
			CourseFileHandler course = new CourseFileHandler();
			if(course.find(courseId).size() <= 0) {
				throw new IllegalArgumentException("Course ID: " + courseId + " does not exists");
			}
						
			ArrayList<HashMap<String, Object>> semesterList = this.getAll();
			int lastIndex = semesterList.size() + 1;
			
			JSONArray semesterInfoArray = new JSONArray();
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");
			
			for(HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				if(semesterId == (int) semester.get("id")) {
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));
					
					ArrayList<HashMap<String, Object>> assignedCourses = (ArrayList<HashMap<String, Object>>) semester.get("courses_list");
					JSONArray assignedCoursesArray = new JSONArray();
					for(int i = 0; i < assignedCourses.size(); i++) {
						JSONObject assignedCoursesObject = new JSONObject();
						HashMap<String, Object> currentCourse = assignedCourses.get(i);
						assignedCoursesObject.put("course_id", currentCourse.get("course_id"));
						assignedCoursesObject.put("teacher_assigned", currentCourse.get("teacher_assigned"));
						assignedCoursesObject.put("experience", currentCourse.get("experience"));
						assignedCoursesObject.put("grade", currentCourse.get("grade"));
						assignedCoursesObject.put("course", currentCourse.get("course"));
						assignedCoursesObject.put("notes", currentCourse.get("notes"));
						if((int) currentCourse.get("course_id") == courseId) {
							assignedCoursesObject.put("approved", isApproved);
						} else {
							assignedCoursesObject.put("approved", currentCourse.get("approved"));
						}
						assignedCoursesArray.add(assignedCoursesObject);
					}
					tempSemester.put("courses_list", assignedCoursesArray);
					
				} else { 
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));
					tempSemester.put("courses_list", semester.get("courses_list"));
				}
				semesterInfoArray.add(tempSemester);
			}
			
			JSONObject databaseObj = new JSONObject();
			databaseObj.put("courses", coursesArray);
			databaseObj.put("teachers", teachersArray);
			databaseObj.put("semester_info", semesterInfoArray);
			
			@SuppressWarnings("resource")
			FileWriter file = new FileWriter(this.getPath());
			file.write(databaseObj.toJSONString());
			file.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
