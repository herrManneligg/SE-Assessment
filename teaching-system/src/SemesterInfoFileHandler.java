import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Class intended to retrieve info about all semesters registered in the system
 * Inherits from the FileHandler class
 */
public class SemesterInfoFileHandler extends FileHandler {

	/**
	 * Constructor of the class
	 * 
	 * @throws IOException
	 */
	public SemesterInfoFileHandler() throws IOException {
		super();
	}

	/**
	 * Retrieve all the "rows" of the semesters. All semesters ever stored in the
	 * system will be returned
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public ArrayList<HashMap<String, Object>> getAll() throws ParseException, IOException {
		// call the parent method and ask for the info of the semesters only
		JSONArray semestreInfo = this.readDatabaseFile("semester_info");

		// We use an iterator to loop the retrieved info
		Iterator<?> it = semestreInfo.iterator();

		// set an array list with the type hashmap (String, Object) to order all the
		// info and return it in a better structure
		ArrayList<HashMap<String, Object>> semestreInfoList = new ArrayList<HashMap<String, Object>>();
		
		while (it.hasNext()) {
			// store each "row" in a HashMap object that will have an index and a value
			HashMap<String, Object> row = new HashMap<String, Object>();

			// obtain the current row in iteration
			JSONObject object = (JSONObject) it.next();

			// store the information in the HashMap object
			row.put("id", (int) (long) object.get("id"));
			row.put("year", (int) (long) object.get("year"));
			row.put("semester_no", (int) (long) object.get("semester_no"));

			// We obtain and store the info of the teachers who are going to teach in one
			// semester
			JSONArray teachers_list = (JSONArray) object.get("teachers_list");
			ArrayList<HashMap<String, Object>> teachersList = new ArrayList<HashMap<String, Object>>();
			
			for (int i = 0; i < teachers_list.size(); i++) {
				HashMap<String, Object> teachers_hashmap = new HashMap<String, Object>();
				// Iterate all teachers and store the info in hashmaps
				JSONObject current_teacher = (JSONObject) teachers_list.get(i);
				teachers_hashmap.put("teacher_id", (int) (long) current_teacher.get("teacher_id"));
				teachers_hashmap.put("trained", (boolean) current_teacher.get("trained"));

				// then we add the info to the array
				teachersList.add(teachers_hashmap);
			}
			// Once we have all info parsed we passed it to the big array with all the
			// semester info
			row.put("teachers_list", teachersList);

			// We obtain and store the info of the courses who are going to be taught in one
			// semester
			JSONArray courses_list = (JSONArray) object.get("courses_list");
			ArrayList<HashMap<String, Object>> coursesList = new ArrayList<HashMap<String, Object>>();
			
			for (int i = 0; i < courses_list.size(); i++) {
				HashMap<String, Object> courses_hashmap = new HashMap<String, Object>();
				JSONObject current_course = (JSONObject) courses_list.get(i);

				// Iterate all teachers and store the info in hashmaps
				courses_hashmap.put("course_id", (int) (long) current_course.get("course_id"));

				// We need this is because the data of the teacher assigned can be null
				// And if it is null we don't have to cast the data
				if (current_course.get("teacher_assigned") != null) {
					courses_hashmap.put("teacher_assigned", (int) (long) current_course.get("teacher_assigned"));
				} else {
					courses_hashmap.put("teacher_assigned", current_course.get("teacher_assigned"));
				}
				if (current_course.get("approved") != null) {
					courses_hashmap.put("approved", (boolean) current_course.get("approved"));
				} else {
					courses_hashmap.put("approved", current_course.get("approved"));
				}
				courses_hashmap.put("experience", (int) (long) current_course.get("experience"));
				courses_hashmap.put("availability", (String) current_course.get("availability"));
				courses_hashmap.put("backgroundRequirement", (String) current_course.get("backgroundRequirement"));

				// then we add the info to the array
				coursesList.add(courses_hashmap);
			}
			// Once we have all info parsed we passed it to the big array with all the
			// semester info
			row.put("courses_list", coursesList);

			// Add the info parsed to the big array and return it
			semestreInfoList.add(row);
		}

		return semestreInfoList;
	}

	/**
	 * Create a new semester in the file
	 * 
	 * @param year
	 * @param semester_no
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void create(int year, int semester_no) throws ParseException, IOException {
		// Obtain the info of all semesters, because we will need to
		// re write everything and we don't want to loose what is already there
		ArrayList<HashMap<String, Object>> semestersList = this.getAll();

		// Obtain the last index and add one for the ID of the new course
		int lastIndex = semestersList.size() + 1;

		// create the new JSON Array of the semesters
		// Since we are adding a new item, we'll need to rebuild it with the new one
		// added
		JSONArray semesterInfoArray = new JSONArray();

		// Set the JSON Arrays of the other fields of the database to re store the info
		JSONArray coursesArray = this.readDatabaseFile("courses");
		JSONArray teachersArray = this.readDatabaseFile("teachers");

		// Read all data of the semesters and store it in objects
		for (HashMap<String, Object> teacher : semestersList) {

			// Initialize the JSON Object to store the semesters info
			JSONObject tempSemester = new JSONObject();

			// set the info of the semesters inside the JSON Object
			tempSemester.put("id", teacher.get("id"));
			tempSemester.put("year", teacher.get("year"));
			tempSemester.put("semester_no", teacher.get("semester_no"));
			tempSemester.put("teachers_list", new JSONArray());
			tempSemester.put("courses_list", new JSONArray());

			// Add the info to the JSON Array
			semesterInfoArray.add(tempSemester);
		}

		// Add the info of the new item to the JSON Object
		JSONObject tempSemester = new JSONObject();
		tempSemester.put("id", lastIndex);
		tempSemester.put("year", year);
		tempSemester.put("semester_no", semester_no);
		tempSemester.put("teachers_list", new JSONArray());
		tempSemester.put("courses_list", new JSONArray());

		// and after to the previous courses JSON Array
		semesterInfoArray.add(tempSemester);

		// create the new JSON Object that is going to be stored in the file
		JSONObject databaseObj = new JSONObject();

		// pass all info
		databaseObj.put("courses", coursesArray);
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);

		// call the method to write into the file
		this.writeDatabaseFile(databaseObj);
	}

	public HashMap<String, Object> getLastSemesterRegister() throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> semestersList = this.getAll();
		int lastIndex = semestersList.size() - 1;
		return semestersList.get(lastIndex);
	}
	
	/**
	 * Method that will return a single semester by its ID
	 * 
	 * @param semesterId
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public HashMap<String, Object> find(int semesterId) throws IOException, ParseException {
		// retrieve all info of the semester
		ArrayList<HashMap<String, Object>> semesterList = this.getAll();

		// Create the hashmap that will have the info of the semester
		HashMap<String, Object> selectedSemester = new HashMap<String, Object>();

		// iterate to find the semester
		for (HashMap<String, Object> semester : semesterList) {
			// compare the current id with the id we are looking for
			if (semesterId == (int) semester.get("id")) {
				// when the semester is found we store the info in the hashmap
				selectedSemester.put("id", semester.get("id"));
				selectedSemester.put("year", semester.get("year"));
				selectedSemester.put("semester_no", semester.get("semester_no"));
				selectedSemester.put("teachers_list", semester.get("teachers_list"));
				selectedSemester.put("courses_list", semester.get("courses_list"));
			}
		}
		return selectedSemester;
	}

	/**
	 * Update an item of the semesters
	 * 
	 * @param semesterId
	 * @param year
	 * @param semester_no
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void update(int semesterId, int year, int semester_no) throws ParseException, IOException {
		// Obtain all info of the semesters
		ArrayList<HashMap<String, Object>> semesterList = this.getAll();

		// Create the JSON Array that will store the new info for the semesters
		JSONArray semesterInfoArray = new JSONArray();

		// Create the JSON arrays with the info of all other things in the file
		JSONArray coursesArray = this.readDatabaseFile("courses");
		JSONArray teachersArray = this.readDatabaseFile("teachers");

		// Iterate and find the semester that is going to be updated
		for (HashMap<String, Object> semester : semesterList) {
			JSONObject tempSemester = new JSONObject();
			// Once we find it we pass the new name
			if (semesterId == (int) semester.get("id")) {
				tempSemester.put("id", (int) semester.get("id"));
				tempSemester.put("year", year);
				tempSemester.put("semester_no", semester_no);
				tempSemester.put("teachers_list", semester.get("teachers_list"));
				tempSemester.put("courses_list", semester.get("courses_list"));
			} else {
				// all other will remain the same
				tempSemester.put("id", (int) semester.get("id"));
				tempSemester.put("year", (String) semester.get("year"));
				tempSemester.put("semester_no", (String) semester.get("semester_no"));
				tempSemester.put("teachers_list", semester.get("teachers_list"));
				tempSemester.put("courses_list", semester.get("courses_list"));
			}
			// Add the new info of the semesters to the JSON Array
			semesterInfoArray.add(tempSemester);
		}

		// Create the JSON Object for the file and store all the info
		JSONObject databaseObj = new JSONObject();
		databaseObj.put("courses", coursesArray);
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);

		// call the method to write into the file
		this.writeDatabaseFile(databaseObj);
	}

	/**
	 * Get the list of teachers who are going to teach in a semester
	 * 
	 * @param semesterId
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> getTeachersList(int semesterId) throws ParseException, IOException {
		// Get the list of the semesters to find the semester
		ArrayList<HashMap<String, Object>> semesterList = this.getAll();
		ArrayList<HashMap<String, Object>> selectedSemesterTeachers = new ArrayList<HashMap<String, Object>>();
		// Iterate all semesters to find the one we are going to affect
		for (HashMap<String, Object> semester : semesterList) {
			if (semesterId == (int) semester.get("id")) {
				// Store the semester in the array list
				selectedSemesterTeachers = (ArrayList<HashMap<String, Object>>) semester.get("teachers_list");
			}
		}
		return selectedSemesterTeachers;
	}

	/**
	 * Add a new teacher to a given semester. It assigns from a teacher stored
	 * previously in the system
	 * 
	 * @param semesterId
	 * @param teacherId
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void addNewTeacher(int semesterId, int teacherId) throws ParseException, IOException {
		try {
			// check if the teacher we are trying to assign exists in the database
			TeacherFileHandler teacher = new TeacherFileHandler();
			if (teacher.find(teacherId).size() <= 0) {
				// If not, prints an error
				throw new IllegalArgumentException("Teacher ID: " + teacherId + " does not exists");
			}

			// Get the info of all the semester
			ArrayList<HashMap<String, Object>> semesterList = this.getAll();

			// Create the JSON Array that will store the updated semester
			JSONArray semesterInfoArray = new JSONArray();

			// Create the JSON arrays with the info of all other things in the file
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");

			// Iterate and find the semester that is going to be updated
			for (HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				// Once we find it we pass the new data
				if (semesterId == (int) semester.get("id")) {
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));

					// THIS IS THE IMPORTANT PART
					// Since we are adding a new teacher, in this part is where we are going to do
					// it
					// We create the hashmap to store the info of the new teacher
					// We will pass all info of the teachers previously stored
					ArrayList<HashMap<String, Object>> assignedTeachers = (ArrayList<HashMap<String, Object>>) semester.get("teachers_list");
					JSONArray assignedTeachersArray = new JSONArray();
					for (int i = 0; i < assignedTeachers.size(); i++) {
						JSONObject assignedTeachersObject = new JSONObject();
						HashMap<String, Object> currentTeacher = assignedTeachers.get(i);
						// if the teacher ID is already in the data then it already added
						if ((int) currentTeacher.get("teacher_id") == teacherId) {
							// if it was added previously we return an error message
							throw new IllegalArgumentException("The teacher is already assigned");
						}
						assignedTeachersObject.put("teacher_id", currentTeacher.get("teacher_id"));
						assignedTeachersObject.put("trained", currentTeacher.get("trained"));

						// add all the info of the new teacher to the array to pass it to the semester
						// info
						assignedTeachersArray.add(assignedTeachersObject);
					}

					// Then we create the new teacher
					JSONObject assignedTeachersObject = new JSONObject();
					assignedTeachersObject.put("teacher_id", teacherId);
					assignedTeachersObject.put("trained", false);

					// and pass the new teacher to the array with all the others
					assignedTeachersArray.add(assignedTeachersObject);

					// and we store the info in the big semester array
					tempSemester.put("teachers_list", assignedTeachersArray);
					tempSemester.put("courses_list", semester.get("courses_list"));
				} else {
					// we need the else for all the other semesters we are not affecting
					// just store the info as it is
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));
					tempSemester.put("courses_list", semester.get("courses_list"));
				}
				// Add each semester to the semester array info
				semesterInfoArray.add(tempSemester);
			}

			// we store all info in the JSON Objects
			JSONObject databaseObj = new JSONObject();
			databaseObj.put("courses", coursesArray);
			databaseObj.put("teachers", teachersArray);
			databaseObj.put("semester_info", semesterInfoArray);

			// Send the info to the write database method to store it in the file
			this.writeDatabaseFile(databaseObj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Find a single teacher of a given semester and change his training flag This
	 * will help to know that the teacher is being trained
	 * 
	 * @param semesterId
	 * @param teacherId
	 * @param isTraining
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void setTeacherTraining(int semesterId, int teacherId, boolean isTraining)
			throws ParseException, IOException {
		try {
			// Check if the teacher exists in the database previously
			TeacherFileHandler teacher = new TeacherFileHandler();
			if (teacher.find(teacherId).size() <= 0) {
				throw new IllegalArgumentException("Teacher ID: " + teacherId + " does not exists");
			}

			// Obtain all info of the semesters
			ArrayList<HashMap<String, Object>> semesterList = this.getAll();

			// Create the JSON Array that will store the new info for the semesters
			JSONArray semesterInfoArray = new JSONArray();

			// Create the JSON arrays with the info of all other things in the file
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");

			// Iterate in all the semesters to find the one we are going to affect
			for (HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				if (semesterId == (int) semester.get("id")) {
					// Once we find it, pass all the info as it is, except for the flat "trained" in
					// the teacher to be found
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));

					// The info of the teachers needs to be re build
					// since we are going to affect a data there
					ArrayList<HashMap<String, Object>> assignedTeachers = (ArrayList<HashMap<String, Object>>) semester
							.get("teachers_list");
					JSONArray assignedTeachersArray = new JSONArray();

					// Iterate all teachers to find the one we are lookig for
					for (int i = 0; i < assignedTeachers.size(); i++) {
						JSONObject assignedTeachersObject = new JSONObject();
						HashMap<String, Object> currentTeacher = assignedTeachers.get(i);
						if ((int) currentTeacher.get("teacher_id") == teacherId) {
							// once we find it we set the new flag of "tranied"
							assignedTeachersObject.put("teacher_id", currentTeacher.get("teacher_id"));
							assignedTeachersObject.put("trained", isTraining);
						} else {
							// all other ramain the same
							assignedTeachersObject.put("teacher_id", currentTeacher.get("teacher_id"));
							assignedTeachersObject.put("trained", currentTeacher.get("trained"));
						}
						// pass the info of every teacher info an array to be stored later
						assignedTeachersArray.add(assignedTeachersObject);
					}
					// store the info of the teachers list in the big array of the semester
					tempSemester.put("teachers_list", assignedTeachersArray);

					tempSemester.put("courses_list", semester.get("courses_list"));
				} else {
					// store the info of all other semesters as it is
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));
					tempSemester.put("courses_list", semester.get("courses_list"));
				}
				// Store the info of ALL semesters in the array
				semesterInfoArray.add(tempSemester);
			}

			// Create the JSON Object for the file and store all the info
			JSONObject databaseObj = new JSONObject();
			databaseObj.put("courses", coursesArray);
			databaseObj.put("teachers", teachersArray);
			databaseObj.put("semester_info", semesterInfoArray);

			// call the method to write into the file
			this.writeDatabaseFile(databaseObj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Obtain the list of course that are going to be taught in a semester
	 * 
	 * @param semesterId
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> getCoursesList(int semesterId) throws ParseException, IOException {
		// Obtain the info off all semesters
		ArrayList<HashMap<String, Object>> semesterList = this.getAll();
		// initialize the array where we are going to store the course we are looking
		// for
		ArrayList<HashMap<String, Object>> selectedSemesterCourses = new ArrayList<HashMap<String, Object>>();
		// iterate to find the semester
		for (HashMap<String, Object> semester : semesterList) {
			if (semesterId == (int) semester.get("id")) {
				// Once we find the semester we obtain the info of the courses
				selectedSemesterCourses = (ArrayList<HashMap<String, Object>>) semester.get("courses_list");
			}
		}
		return selectedSemesterCourses;
	}

	/**
	 * Add a new course to a given semester. It is assigned from a course stored
	 * previously in the system
	 * 
	 * @param semesterId
	 * @param courseId
	 * @param experience
	 * @param grade
	 * @param courseInfo
	 * @param notes
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void addNewCourse(int semesterId, int courseId, int experience, String availability, String backgroundRequirement) throws ParseException, IOException {
		try {

			// check if the course we are trying to assign exists in the database
			CourseFileHandler course = new CourseFileHandler();
			if (course.find(courseId).size() <= 0) {
				// If not, prints an error
				throw new IllegalArgumentException("Course ID: " + courseId + " does not exists");
			}

			// Obtain the info of all semesters
			ArrayList<HashMap<String, Object>> semesterList = this.getAll();

			// Create the JSON Array that will store the new info for the courses
			JSONArray semesterInfoArray = new JSONArray();

			// Create the JSON arrays with the info of all other things in the file
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");

			// Iterate and find the course that is going to be updated
			for (HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				// Once we find it we pass the new info
				if (semesterId == (int) semester.get("id")) {
					// Once we find it, pass all the info as it is
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));

					// The info of the courses needs to be re build
					// since we are going to affect a data there
					ArrayList<HashMap<String, Object>> assignedCourses = (ArrayList<HashMap<String, Object>>) semester
							.get("courses_list");

					JSONArray assignedCoursesArray = new JSONArray();
					for (int i = 0; i < assignedCourses.size(); i++) {
						JSONObject assignedTeachersObject = new JSONObject();
						HashMap<String, Object> currentCourse = assignedCourses.get(i);
						if ((int) currentCourse.get("course_id") == courseId) {
							throw new IllegalArgumentException("The course is already added");
						}
						assignedTeachersObject.put("course_id", currentCourse.get("course_id"));
						assignedTeachersObject.put("teacher_assigned", currentCourse.get("teacher_assigned"));
						assignedTeachersObject.put("approved", currentCourse.get("approved"));
						assignedTeachersObject.put("experience", currentCourse.get("experience"));
						assignedTeachersObject.put("availability", currentCourse.get("availability"));
						assignedTeachersObject.put("backgroundRequirement", currentCourse.get("backgroundRequirement"));
						assignedCoursesArray.add(assignedTeachersObject);
					}

					JSONObject assignedTeachersObject = new JSONObject();
					assignedTeachersObject.put("course_id", courseId);
					assignedTeachersObject.put("teacher_assigned", null);
					assignedTeachersObject.put("approved", null);
					assignedTeachersObject.put("experience", experience);
					assignedTeachersObject.put("availability", availability);
					assignedTeachersObject.put("backgroundRequirement", backgroundRequirement);
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

			this.writeDatabaseFile(databaseObj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void assignTeacherToCourse(int semesterId, int courseId, int teacherId) throws ParseException, IOException {
		try {

			CourseFileHandler course = new CourseFileHandler();
			if (course.find(courseId).size() <= 0) {
				throw new IllegalArgumentException("Course ID: " + courseId + " does not exists");
			}

			TeacherFileHandler teacher = new TeacherFileHandler();
			if (teacher.find(teacherId).size() <= 0) {
				throw new IllegalArgumentException("Teacher ID: " + teacherId + " does not exists");
			}

			ArrayList<HashMap<String, Object>> semesterList = this.getAll();

			JSONArray semesterInfoArray = new JSONArray();
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");

			for (HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				if (semesterId == (int) semester.get("id")) {
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));

					ArrayList<HashMap<String, Object>> assignedCourses = (ArrayList<HashMap<String, Object>>) semester
							.get("courses_list");
					JSONArray assignedCoursesArray = new JSONArray();
					for (int i = 0; i < assignedCourses.size(); i++) {
						JSONObject assignedCoursesObject = new JSONObject();
						HashMap<String, Object> currentCourse = assignedCourses.get(i);
						assignedCoursesObject.put("course_id", currentCourse.get("course_id"));
						assignedCoursesObject.put("approved", currentCourse.get("approved"));
						assignedCoursesObject.put("experience", currentCourse.get("experience"));
						assignedCoursesObject.put("availability", currentCourse.get("availability"));
						assignedCoursesObject.put("backgroundRequirement", currentCourse.get("backgroundRequirement"));
						if ((int) currentCourse.get("course_id") == courseId) {
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

			this.writeDatabaseFile(databaseObj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void setCourseApproval(int semesterId, int courseId, boolean isApproved) throws ParseException, IOException {
		try {

			CourseFileHandler course = new CourseFileHandler();
			if (course.find(courseId).size() <= 0) {
				throw new IllegalArgumentException("Course ID: " + courseId + " does not exists");
			}

			ArrayList<HashMap<String, Object>> semesterList = this.getAll();

			JSONArray semesterInfoArray = new JSONArray();
			JSONArray coursesArray = this.readDatabaseFile("courses");
			JSONArray teachersArray = this.readDatabaseFile("teachers");

			for (HashMap<String, Object> semester : semesterList) {
				JSONObject tempSemester = new JSONObject();
				if (semesterId == (int) semester.get("id")) {
					tempSemester.put("id", semester.get("id"));
					tempSemester.put("year", semester.get("year"));
					tempSemester.put("semester_no", semester.get("semester_no"));
					tempSemester.put("teachers_list", semester.get("teachers_list"));

					ArrayList<HashMap<String, Object>> assignedCourses = (ArrayList<HashMap<String, Object>>) semester
							.get("courses_list");
					JSONArray assignedCoursesArray = new JSONArray();
					for (int i = 0; i < assignedCourses.size(); i++) {
						JSONObject assignedCoursesObject = new JSONObject();
						HashMap<String, Object> currentCourse = assignedCourses.get(i);
						assignedCoursesObject.put("course_id", currentCourse.get("course_id"));
						assignedCoursesObject.put("teacher_assigned", currentCourse.get("teacher_assigned"));
						assignedCoursesObject.put("experience", currentCourse.get("experience"));
						assignedCoursesObject.put("availability", currentCourse.get("availability"));
						assignedCoursesObject.put("backgroundRequirement", currentCourse.get("backgroundRequirement"));
						
						if ((int) currentCourse.get("course_id") == courseId) {
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

			this.writeDatabaseFile(databaseObj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
