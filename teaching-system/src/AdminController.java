
import java.io.PrintStream;
import java.sql.PseudoColumnUsage;
import java.util.InputMismatchException;

public class AdminController {
	
	private Model modelobject;
	private View viewObject;
	private Administrator administrator;
	PrintStream pStream; 

	
	
	public AdminController(Model model, PrintStream ps) {
		this.modelobject = model;
		this.viewObject = new View(ps);
		this.showSelectedOptionFromScreen();
	}
	
	public void showSelectedOptionFromScreen() {
		boolean finishAction = false;
		
		while(!finishAction) {
			int input = this.viewObject.getUserInputInteger(administratorView());
			int i = 1;
			
			try {
				if (input == 1) {

					administrator.createTeacher();
									
					
					// Here is where the logic to create the new semester with the models and the database goes
					
				} else if(input == 2) {
					for( Teacher t : this.administrator.getListOfUnassignedTeachers()) {
						pStream.print(i+" : "+t.getName()+"\n");
						i++;
					}
				} else if(input == 3) {
				} else if(input == 4) {
					finishAction = true;
				} else {
					System.out.println("Enter a numerical value within the range");
				}
			} catch (InputMismatchException e) {
				System.out.println("noob");
			}
		}
	}
	
	public String administratorView() {
		return "---------------------------\n" +
		 	   "|    Administrator View     |\n" +
		       "---------------------------\n" +
		       "--------- Actions ---------\n\n" +
		       " 1: Add Teacher to System\n" +
		       " 2: Assign Teacher to Courses with matching requirements\n" +
		       " 3: Go back to the role-selecting-view\n\n" +
		       "Enter the number for your selection and press 'Enter': ";
	}

}
