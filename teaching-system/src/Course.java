import java.util.Date;

public class Course {
	
	private boolean approved;

	public void update(String name, Date timeExp, String availability) {
		// TODO Auto-generated method stub
		
	}

	public void approve() {
		approved = true;
	}
	
	public void reject() {
		approved = false;
	}

}
