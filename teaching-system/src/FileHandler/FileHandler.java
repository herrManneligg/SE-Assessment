package FileHandler;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * This class is going to be used as main class to read the file where all info is stored
 */
public class FileHandler {
	
	// path where the file will be
	protected String path = "./info-file.json";
	
	
	/**
	 * The constructor will create the file in case this is not created
	 * @throws IOException
	 */
	protected FileHandler() throws IOException {
		// Check if the database file exists
		if(!this.doesDatabaseFileExists()) {
			// create the file in case it does not exists
			this.createNewJsonFile();
		}
	}
	
	/**
	 * Method to check is the database file exists
	 * @return
	 */
	protected boolean doesDatabaseFileExists() {
		// instantiate the file
		File databaseFile = new File(this.path);
		
		// check is the file exists and returns the response
		return databaseFile.exists();
	}
	
	/**
	 * Method that creates the database file with the pertinent info to start
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void createNewJsonFile() throws IOException {
		
		// We use JSON Arrays to create the first structure
		JSONArray coursesArray = new JSONArray();
		JSONArray teachersArray = new JSONArray();
		JSONArray semesterInfoArray = new JSONArray();
		
		// then create a JSON Object to store the JSON arrays
		// So the file can have a nice structure
		JSONObject databaseObj = new JSONObject();
		
		// Store the JSON Arrays inside the JSON Object
		databaseObj.put("courses", coursesArray);		
		databaseObj.put("teachers", teachersArray);
		databaseObj.put("semester_info", semesterInfoArray);
		
		// call the method to write the info in the file
		this.writeDatabaseFile(databaseObj);

	}

	/**
	 * Getter of the path of the file
	 * @return
	 */
	protected String getPath() {
		return path;
	}
	
	/**
	 * Read the database file and returns the info of a specific part of the file
	 * @param index_name
	 * @return info returned as a JSON Array
	 * @throws IOException
	 * @throws ParseException
	 */
	protected JSONArray readDatabaseFile(String index_name) throws IOException, ParseException {
		// Read the file
		FileReader reader = new FileReader(this.path);
		
		// Initialize the JSON parser to get all info of the file
		JSONParser jsonParser = new JSONParser();
		
		// parse the info of the database file and store it in a JSON Object file
		JSONObject databaseInfo = (JSONObject) jsonParser.parse(reader);
		
		// return the info of the requested part of the file
		return (JSONArray) databaseInfo.get(index_name);
	}
	
	/**
	 * Write new info to the database file
	 * This method re write all info into the file
	 * @param data
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	protected void writeDatabaseFile(JSONObject data) throws IOException {
		// read the database file
		FileWriter file = new FileWriter(this.path);
		
		// write info passed in this method parsed as a JSON String
		file.write(data.toJSONString());
		
		// Populate the file
		file.flush();
	}
	
}
