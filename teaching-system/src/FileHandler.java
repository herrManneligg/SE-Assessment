import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileHandler {
	
	protected String path = "./info-file.json";
	
	protected FileHandler() throws IOException {
		if(!this.doesDatabaseFileExists()) {
			this.createNewJsonFile();
		}
	}
	
	protected boolean doesDatabaseFileExists() {
		File databaseFile = new File(this.path);
		return databaseFile.exists();
	}
	
	@SuppressWarnings("unchecked")
	private void createNewJsonFile() throws IOException {
		
		JSONArray coursesArray = new JSONArray();
		JSONArray teachersArray = new JSONArray();
		JSONArray semesterInfoArray = new JSONArray();
		
		JSONObject databaseObj = new JSONObject();
		databaseObj.put("courses", coursesArray);		
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);
		
		@SuppressWarnings("resource")
		FileWriter file = new FileWriter(this.path);
		file.write(databaseObj.toJSONString());
		file.flush();

	}

	protected String getPath() {
		return path;
	}

	protected void setPath(String path) {
		this.path = path;
	}
	
	protected JSONArray readDatabaseFile(String index_name) throws IOException, ParseException {
		FileReader reader = new FileReader(this.path);
		JSONParser jsonParser = new JSONParser();
		JSONObject databaseInfo = (JSONObject) jsonParser.parse(reader);
		return (JSONArray) databaseInfo.get(index_name);
	}
	
}
