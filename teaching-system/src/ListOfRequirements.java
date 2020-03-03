import java.util.ArrayList;

public class ListOfRequirements {
	private int timeExp; // years of experience
	private String availability; // the semester in which it should be available
	private String backgroundRequirement;
	private ArrayList<String> listOfBackgroundsRequirements; // -Marjan added for Filter-Method

// is there any case where we would have a list of requirements without a course?

	// When a new course is created, it can be created without requirements; in that case it will have default requirements
	public ListOfRequirements(int timeExp, String availability, String backgroundRequirement) {		
		this.timeExp = timeExp;
		this.availability = availability;	
		this.backgroundRequirement = backgroundRequirement;
	}

	public void setSemester(String semester) {
		this.availability = semester;
	}

	public void updateAllRequirements(int experience, String semester, String backgroundRequirement) {
		this.timeExp = experience;
		this.availability = semester;
		this.backgroundRequirement = backgroundRequirement;
	}

	public void setExperience(int experience) {
		this.timeExp = experience;
	}

	public int getTimeExp() {
		return timeExp;
	}

	public String getAvailability() {
		return availability;
	}

	public void setTimeExp(int timeExp) {
		this.timeExp = timeExp;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getBackgroundRequirement() {
		return backgroundRequirement;
	}

	public void setBackgroundRequirement(String backgroundRequirement) {
		this.backgroundRequirement = backgroundRequirement;
	}

	public ArrayList<String> getListOfBackgroundsRequirements() {
		return listOfBackgroundsRequirements;
	}

	public void setListOfBackgroundsRequirements(ArrayList<String> listOfBackgroundsRequirements) {
		this.listOfBackgroundsRequirements = listOfBackgroundsRequirements;
	}

}
