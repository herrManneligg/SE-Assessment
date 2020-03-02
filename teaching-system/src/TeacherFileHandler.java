import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class TeacherFileHandler extends FileHandler {

	public TeacherFileHandler() throws IOException {
		super();
	}
	
	public ArrayList<HashMap<String, Object>> getAll() throws ParseException, IOException {
		JSONArray teachers = this.readDatabaseFile("teachers");
		Iterator<?> it = teachers.iterator();
		
		ArrayList<HashMap<String, Object>> teachersList = new ArrayList<HashMap<String, Object>>();
		
		while(it.hasNext()) {
			HashMap<String, Object> row = new HashMap<String, Object>();
			JSONObject object = (JSONObject) it.next();
			row.put("id", (int) (long) object.get("id"));
			row.put("name", (String) object.get("name"));
			row.put("email", (String) object.get("email"));
			row.put("time_experience", (int) (long) object.get("time_experience"));
			row.put("background", (String) object.get("background"));
			row.put("availability", (String) object.get("availability"));
			teachersList.add(row);
		}
		
		return teachersList;
	}
	
	@SuppressWarnings("unchecked")
	public void create(String name, String email, int time_experience, String background, String availability) throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> teachersList = this.getAll();
		int lastIndex = teachersList.size() + 1;
		
		JSONArray teachersArray = new JSONArray();
		JSONArray coursesArray = this.readDatabaseFile("courses"); 
		JSONArray semesterInfoArray = this.readDatabaseFile("semester_info");
		
		for(HashMap<String, Object> teacher : teachersList) {
			JSONObject tempTeacher = new JSONObject();
			tempTeacher.put("id", teacher.get("id"));
			tempTeacher.put("name", teacher.get("name"));
			tempTeacher.put("email", teacher.get("email"));
			tempTeacher.put("time_experience", teacher.get("time_experience"));
			tempTeacher.put("background", teacher.get("background"));
			tempTeacher.put("availability", teacher.get("availability"));
			teachersArray.add(tempTeacher);
		}
		
		JSONObject tempTeacher = new JSONObject();
		tempTeacher.put("id", lastIndex);
		tempTeacher.put("name", name);
		tempTeacher.put("email", email);
		tempTeacher.put("time_experience", time_experience);
		tempTeacher.put("background", background);
		tempTeacher.put("availability", availability);
		teachersArray.add(tempTeacher);
		
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
		ArrayList<HashMap<String, Object>> teachersList = this.getAll();
		HashMap<String, Object> selectedTeacher = new HashMap<String, Object>();
		for(HashMap<String, Object> teacher : teachersList) {
			if(courseId == (int) teacher.get("id")) {
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
	
	@SuppressWarnings("unchecked")
	public void update(int teacherId, String name, String email, int time_experience, String background, String availability) throws ParseException, IOException {
		ArrayList<HashMap<String, Object>> teachersList = this.getAll();
		int lastIndex = teachersList.size() + 1;
		
		JSONArray teachersArray = new JSONArray();
		JSONArray coursesArray = this.readDatabaseFile("courses");
		JSONArray semesterInfoArray = this.readDatabaseFile("semester_info");
		
		for(HashMap<String, Object> teacher : teachersList) {
			JSONObject tempTeacher = new JSONObject();
			if(teacherId == (int) teacher.get("id")) {
				tempTeacher.put("id", (int) teacher.get("id"));
				tempTeacher.put("name", name);
				tempTeacher.put("email", email);
				tempTeacher.put("time_experience", time_experience);
				tempTeacher.put("background", background);
				tempTeacher.put("availability", availability);
			} else { 
				tempTeacher.put("id", (int) teacher.get("id"));
				tempTeacher.put("name", (String) teacher.get("name"));
				tempTeacher.put("email", (String) teacher.get("email"));
				tempTeacher.put("time_experience", (int) teacher.get("time_experience"));
				tempTeacher.put("background", (String) teacher.get("background"));
				tempTeacher.put("availability", (String) teacher.get("availability"));
			}
			teachersArray.add(tempTeacher);
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
