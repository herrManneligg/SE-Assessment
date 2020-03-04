import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import org.json.simple.parser.ParseException;

public abstract class Person {
	
	private String name;
	private String email;
	private View viewObject; // view for printing in the console
	private Semester semester;

	public Person(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public Person(String name, String email, View view) {
		this.name = name;
		this.email = email;
		this.viewObject = view;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public View getViewObject() {
		return viewObject;
	}

	public void setViewObject(View view) {
		this.viewObject = view;
	}
	
	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	private void setSemesterInfo(HashMap<String, Object> selectedSemester) {
		this.semester = new Semester((int) selectedSemester.get("year"), (int) selectedSemester.get("semester_no"));
		this.semester.setDatasbaseId((int) selectedSemester.get("id"));
	}
	
	protected void openSemester() throws IOException, ParseException {
		String message = "\n ----------------\n" +
				  		 "|    Semesters   |\n" +
				  		 " ----------------\n\n";
		
		ArrayList<HashMap<String, Object>> semesters = Semester.getSemesters();
		
		if(semesters.size() > 0) {
			String semesterTpml = " %1d: year: %s - semester: %1d \n";
			for(int i = 0; i < semesters.size(); i++) {
				HashMap<String, Object> row = semesters.get(i);
				message += String.format(semesterTpml, (i + 1), row.get("year"), row.get("semester_no"));
			}
			message += "\n";
			this.viewObject.printScreen(message);
			int selection = this.viewObject.getUserInputInteger("Enter the number of the semester you want to see: ");
			
			this.setSemesterInfo(semesters.get(selection - 1));
			
			message = "\n -------------------------\n" +
			   		  "| Year %4d - Semester %2d |\n" +
			   		  " -------------------------\n\n";
	
			this.viewObject.printScreen(String.format(message, this.semester.getYear(), this.semester.getSemesterNo()));
		} else {
			this.viewObject.printScreen("\n--- There are no Semesters, create one! ---\n\n");
		}
		
	}
	
}
