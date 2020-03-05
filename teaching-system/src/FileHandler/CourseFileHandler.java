package FileHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Class intended to retrieve info about all courses registered in the system
 * Inherits from the FileHandler class
 */
public class CourseFileHandler extends FileHandler {

	/**
	 * Constructor of the class
	 * @throws IOException
	 */
	public CourseFileHandler() throws IOException {
		super();
	}
	
	/**
	 * Retrieve all the "rows" of the courses. All courses ever stored in the system will be returned
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public ArrayList<HashMap<String, Object>> getAll() throws ParseException, IOException {
		// call the parent method and ask for the info of the courses only
		JSONArray courses = super.readDatabaseFile("courses");
		
		// We use an iterator to loop the retrieved info
		Iterator<?> it = courses.iterator();
		
		// set an array list with the type hashmap (String, Object) to order all the info and return it in a better structure
		ArrayList<HashMap<String, Object>> coursesList = new ArrayList<HashMap<String, Object>>();
		while(it.hasNext()) {
			// store each "row" in a HashMap object that will have an index and a value
			HashMap<String, Object> row = new HashMap<String, Object>();
			
			// obtain the current row in iteration
			JSONObject object = (JSONObject) it.next();
			
			// store the information in the HashMap object
			row.put("name", (String) object.get("name"));
			row.put("id", (int) (long) object.get("id"));
			
			// add the previous info to the array list
			coursesList.add(row);
		}
		
		return coursesList;
	}
	
	/**
	 * Create a new course in the file
	 * @param name
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void create(String name) throws ParseException, IOException {
		// Obtain the info of all courses, because we will need to 
		// re write everything and we don't want to loose what is already there
		ArrayList<HashMap<String, Object>> coursesList = this.getAll();
		
		// Obtain the last index and add one for the ID of the new course
		int lastIndex = coursesList.size() + 1;
		
		// create the new JSON Array of the courses
		// Since we are adding a new item, we'll need to rebuild it with the new one added
		JSONArray coursesArray = new JSONArray();
		
		// Set the JSON Arrays of the other fields of the database to re store the info
		JSONArray teachersArray = this.readDatabaseFile("teachers");
		JSONArray semesterInfoArray = this.readDatabaseFile("semester_info");
		
		// Read all data of the courses and store it in objects
		for(HashMap<String, Object> course : coursesList) {
			
			// Initialize the JSON Object to store the courses info
			JSONObject tempCourse = new JSONObject();
			
			// set the info of the courses inside the JSON Object
			tempCourse.put("id", (int) course.get("id"));
			tempCourse.put("name", (String) course.get("name"));
			
			// Add the info to the JSON Array
			coursesArray.add(tempCourse);
		}
		
		// Add the info of the new item to the JSON Object
		JSONObject tempCourse = new JSONObject();
		tempCourse.put("id", lastIndex);
		tempCourse.put("name", name);
		
		// and after to the previous courses JSON Array
		coursesArray.add(tempCourse);
		
		// create the new JSON Object that is going to be stored in the file
		JSONObject databaseObj = new JSONObject();
		
		// pass all info
		databaseObj.put("courses", coursesArray);
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);
		
		// call the method to write into the file
		this.writeDatabaseFile(databaseObj);
	}
	
	public HashMap<String, Object> getLastRegister() throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> coursesList = this.getAll();
		int lastIndex = coursesList.size() - 1;
		return coursesList.get(lastIndex);
	}
	
	/**
	 * Method that will return a single course by its ID
	 * @param courseId
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public HashMap<String, Object> find(int courseId) throws IOException, ParseException {
		// retrieve all info of the courses
		ArrayList<HashMap<String, Object>> coursesList = this.getAll();
		
		// Create the hashmap that will have the info of the course
		HashMap<String, Object> selectedCourse = new HashMap<String, Object>();
		
		// iterate to find the course
		for(HashMap<String, Object> course : coursesList) {
			// compare the current id with the id we are looking for
			if(courseId == (int) course.get("id")) {
				// when the course is found we store the info in the hashmap
				selectedCourse.put("id", (int) course.get("id"));
				selectedCourse.put("name", (String) course.get("name"));
			}
		}
		return selectedCourse;
	}
	
	/**
	 * Update an item of the courses. Only the name can be updated
	 * @param courseId
	 * @param name
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void update(int courseId, String name) throws ParseException, IOException {
		// Obtain all info of the courses
		ArrayList<HashMap<String, Object>> coursesList = this.getAll();
		
		// Create the JSON Array that will store the new info for the courses
		JSONArray coursesArray = new JSONArray();

		// Create the JSON arrays with the info of all other things in the file
		JSONArray teachersArray = this.readDatabaseFile("teachers");
		JSONArray semesterInfoArray = this.readDatabaseFile("semester_info");
		
		// Iterate and find the course that is going to be updated
		for(HashMap<String, Object> course : coursesList) {
			JSONObject tempCourse = new JSONObject();
			// Once we find it we pass the new name
			if(courseId == (int) course.get("id")) {
				tempCourse.put("id", (int) course.get("id"));
				tempCourse.put("name", name);
			} else { 
				// all other will remain the same
				tempCourse.put("id", (int) course.get("id"));
				tempCourse.put("name", (String) course.get("name"));
			}
			// Add the new info of the courses to the JSON Array
			coursesArray.add(tempCourse);
		}
		
		// Create the JSON Object for the file and store all the info
		JSONObject databaseObj = new JSONObject();
		databaseObj.put("courses", coursesArray);
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);
		
		// call the method to write into the file
		this.writeDatabaseFile(databaseObj);
	}

}
