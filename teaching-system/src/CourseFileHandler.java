import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class CourseFileHandler extends FileHandler {

	public CourseFileHandler() throws IOException {
		super();
	}
	
	public ArrayList<HashMap<String, Object>> getAll() throws ParseException, IOException {
		JSONArray courses = super.readDatabaseFile("courses");
		Iterator<?> it = courses.iterator();
		
		ArrayList<HashMap<String, Object>> coursesList = new ArrayList<HashMap<String, Object>>();
		
		while(it.hasNext()) {
			HashMap<String, Object> row = new HashMap<String, Object>();
			JSONObject object = (JSONObject) it.next();
			row.put("name", (String) object.get("name"));
			row.put("id", (int) (long) object.get("id"));
			coursesList.add(row);
		}
		
		return coursesList;
	}
	
	@SuppressWarnings("unchecked")
	public void create(String name) throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> coursesList = this.getAll();
		int lastIndex = coursesList.size() + 1;
		
		JSONArray coursesArray = new JSONArray();
		JSONArray teachersArray = this.readDatabaseFile("teachers");
		JSONArray semesterInfoArray = this.readDatabaseFile("semester_info");
		
		for(HashMap<String, Object> course : coursesList) {
			JSONObject tempCourse = new JSONObject();
			tempCourse.put("id", (int) course.get("id"));
			tempCourse.put("name", (String) course.get("name"));
			coursesArray.add(tempCourse);
		}
		
		JSONObject tempCourse = new JSONObject();
		tempCourse.put("id", lastIndex);
		tempCourse.put("name", name);
		coursesArray.add(tempCourse);
		
		JSONObject databaseObj = new JSONObject();
		databaseObj.put("courses", coursesArray);
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);
		
		@SuppressWarnings("resource")
		FileWriter file = new FileWriter(this.getPath());
		file.write(databaseObj.toJSONString());
		file.flush();
	}
	
	public HashMap<String, Object> find(int courseId) throws IOException, ParseException {
		ArrayList<HashMap<String, Object>> coursesList = this.getAll();
		HashMap<String, Object> selectedCourse = new HashMap<String, Object>();
		for(HashMap<String, Object> course : coursesList) {
			if(courseId == (int) course.get("id")) {
				selectedCourse.put("id", (int) course.get("id"));
				selectedCourse.put("name", (String) course.get("name"));
			}
		}
		return selectedCourse;
	}
	
	@SuppressWarnings("unchecked")
	public void update(int courseId, String name) throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> coursesList = this.getAll();
		int lastIndex = coursesList.size() + 1;
		
		JSONArray coursesArray = new JSONArray();
		JSONArray teachersArray = this.readDatabaseFile("teachers");
		JSONArray semesterInfoArray = this.readDatabaseFile("semester_info");
		
		for(HashMap<String, Object> course : coursesList) {
			JSONObject tempCourse = new JSONObject();
			if(courseId == (int) course.get("id")) {
				tempCourse.put("id", (int) course.get("id"));
				tempCourse.put("name", name);
			} else { 
				tempCourse.put("id", (int) course.get("id"));
				tempCourse.put("name", (String) course.get("name"));
			}
			coursesArray.add(tempCourse);
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

}
