import java.util.ArrayList;
import java.util.InputMismatchException;

public class PttDirector {
	
	private View viewObject;
	private ArrayList<Course> listOfCourses;
	
	public PttDirector(View view) {
//		super(name, email);
		this.viewObject = view;
		this.showSelectedOptionFromScreen();
	}
	
	public void approve(Course approvedCourse) {
		approvedCourse.approve();
	}
	
	public void reject(Course approvedCourse) {
		approvedCourse.reject();
	}
	
	public void showSelectedOptionFromScreen() {
		boolean finishAction = false;
		while(!finishAction) {
			String message = "---------------------------\n" +
				 	   		 "|    PTT Director View     |\n" +
				 	   		 "---------------------------\n" +
				 	   		 "--------- Actions ---------\n\n" +
				 	   		 " 1: See teacher requests\n" +
				 	   		 " 2: Go back\n\n" +
				 	   		 "Enter the number for your selection: ";
			int input = this.viewObject.getUserInputInteger(message);
			try {
				if (input == 1) {
					// Here is where the logic to create the new semester with the models and the database goes
				} else if(input == 2) {
					finishAction = true;
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				System.out.println("noob");
			}
		}
	}
	
}
