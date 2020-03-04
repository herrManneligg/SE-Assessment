import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Class intended to retrieve info about all teachers registered in the system
 * Inherits from the FileHandler class
 */
public class TeacherFileHandler extends FileHandler {

	/**
	 * Constructor of the class
	 * @throws IOException
	 */
	public TeacherFileHandler() throws IOException {
		super();
	}
	
	/**
	 * Retrieve all the "rows" of the teachers. All teachers ever stored in the system will be returned
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public ArrayList<HashMap<String, Object>> getAll() throws ParseException, IOException {
		// call the parent method and ask for the info of the courses only
		JSONArray teachers = this.readDatabaseFile("teachers");
		
		// We use an iterator to loop the retrieved info
		Iterator<?> it = teachers.iterator();
		
		// set an array list with the type hashmap (String, Object) to order all the info and return it in a better structure
		ArrayList<HashMap<String, Object>> teachersList = new ArrayList<HashMap<String, Object>>();
		while(it.hasNext()) {
			// store each "row" in a HashMap object that will have an index and a value
			HashMap<String, Object> row = new HashMap<String, Object>();
			
			// obtain the current row in iteration
			JSONObject object = (JSONObject) it.next();
			
			// store the information in the HashMap object
			row.put("id", (int) (long) object.get("id"));
			row.put("name", (String) object.get("name"));
			row.put("email", (String) object.get("email"));
			row.put("time_experience", (int) (long) object.get("time_experience"));
			row.put("background", (String) object.get("background"));
			row.put("availability", (String) object.get("availability"));
			
			// add the previous info to the array list
			teachersList.add(row);
		}
		
		return teachersList;
	}
	
	/**
	 * Create a new teacher in the file
	 * @param name
	 * @param email
	 * @param time_experience
	 * @param background
	 * @param availability
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void create(String name, String email, int time_experience, String background, String availability) throws ParseException, IOException {
		// Obtain the info of all teachers, because we will need to 
		// re write everything and we don't want to loose what is already there
		ArrayList<HashMap<String, Object>> teachersList = this.getAll();
		
		// Obtain the last index and add one for the ID of the new course
		int lastIndex = teachersList.size() + 1;
		
		// create the new JSON Array of the teachers
		// Since we are adding a new item, we'll need to rebuild it with the new one added
		JSONArray teachersArray = new JSONArray();
		
		// Set the JSON Arrays of the other fields of the database to re store the info
		JSONArray coursesArray = this.readDatabaseFile("courses"); 
		JSONArray semesterInfoArray = this.readDatabaseFile("semester_info");
		
		// Read all data of the teachers and store it in objects
		for(HashMap<String, Object> teacher : teachersList) {
			// Initialize the JSON Object to store the teachers info
			JSONObject tempTeacher = new JSONObject();
			
			// set the info of the teachers inside the JSON Object
			tempTeacher.put("id", teacher.get("id"));
			tempTeacher.put("name", teacher.get("name"));
			tempTeacher.put("email", teacher.get("email"));
			tempTeacher.put("time_experience", teacher.get("time_experience"));
			tempTeacher.put("background", teacher.get("background"));
			tempTeacher.put("availability", teacher.get("availability"));
			
			// Add the info to the JSON Array
			teachersArray.add(tempTeacher);
		}
		
		// Add the info of the new item to the JSON Object
		JSONObject tempTeacher = new JSONObject();
		tempTeacher.put("id", lastIndex);
		tempTeacher.put("name", name);
		tempTeacher.put("email", email);
		tempTeacher.put("time_experience", time_experience);
		tempTeacher.put("background", background);
		tempTeacher.put("availability", availability);
		
		// and after to the previous courses JSON Array
		teachersArray.add(tempTeacher);
		
		// create the new JSON OBject that is going to be stored in the file
		JSONObject databaseObj = new JSONObject();
		
		// pass all info
		databaseObj.put("courses", coursesArray);
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);
		
		// call the method to write into the file
		this.writeDatabaseFile(databaseObj);
	}
	
	/**
	 * Method that will return a single teacher by its ID
	 * @param teacherId
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public HashMap<String, Object> find(int teacherId) throws IOException, ParseException {
		// retrieve all info of the teachers
		ArrayList<HashMap<String, Object>> teachersList = this.getAll();
		
		// Create the hashmap that will have the info of the teacher
		HashMap<String, Object> selectedTeacher = new HashMap<String, Object>();
		for(HashMap<String, Object> teacher : teachersList) {
			// compare the current id with the id we are looking for
			if(teacherId == (int) teacher.get("id")) {
				// when the course is found we store the info in the hashmap
				selectedTeacher.put("id", teacher.get("id"));
				selectedTeacher.put("name", teacher.get("name"));
				selectedTeacher.put("email", teacher.get("email"));
				selectedTeacher.put("time_experience", teacher.get("time_experience"));
				selectedTeacher.put("background", teacher.get("background"));
				selectedTeacher.put("availability", teacher.get("availability"));
				
			}
		}
		return selectedTeacher;
	}
	
	/**
	 * Update an item of the teachers
	 * @param teacherId
	 * @param name
	 * @param email
	 * @param time_experience
	 * @param background
	 * @param availability
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public void update(int teacherId, String name, String email, int time_experience, String background, String availability) throws IOException, ParseException {
		// Obtain all info of the teachers
		ArrayList<HashMap<String, Object>> teachersList = this.getAll();
		
		// Create the JSON Array that will store the new info for the teachers
		JSONArray teachersArray = new JSONArray();
		
		// Create the JSON arrays with the info of all other things in the file
		JSONArray coursesArray = this.readDatabaseFile("courses");
		JSONArray semesterInfoArray = this.readDatabaseFile("semester_info");
		
		// Iterate and find the teacher that is going to be updated
		for(HashMap<String, Object> teacher : teachersList) {
			JSONObject tempTeacher = new JSONObject();
			
			// Once we find it we pass the new name
			if(teacherId == (int) teacher.get("id")) {
				tempTeacher.put("id", (int) teacher.get("id"));
				tempTeacher.put("name", name);
				tempTeacher.put("email", email);
				tempTeacher.put("time_experience", time_experience);
				tempTeacher.put("background", background);
				tempTeacher.put("availability", availability);
			} else { 
				// all other will remain the same
				tempTeacher.put("id", (int) teacher.get("id"));
				tempTeacher.put("name", (String) teacher.get("name"));
				tempTeacher.put("email", (String) teacher.get("email"));
				tempTeacher.put("time_experience", (int) teacher.get("time_experience"));
				tempTeacher.put("background", (String) teacher.get("background"));
				tempTeacher.put("availability", (String) teacher.get("availability"));
			}
			// Add the new info of the courses to the JSON Array
			teachersArray.add(tempTeacher);
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
