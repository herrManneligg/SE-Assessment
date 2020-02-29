import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Administrator extends Person {

	private ArrayList<Course> listOfCourses;
	private ArrayList<Teacher> listOfUnassignedTeachers;

	public Administrator(String name, String email) {
		super(name, email);
	}

	public String getCourseInfo(Course course) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i) == course) {
				return listOfCourses.get(i).getCourseInfo();
			}
		}
		return "There is no information for the selected course.";
	}

	public void sendTeacherToTraining(Teacher teacher) {
		teacher.setInTraining(true);
	}

	public void removeTeacherFromTraining(Teacher teacher) {
		teacher.setInTraining(false);
	}

	public void assignTeacherToCourse(Course course, Teacher teacher) {
		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i) == course) {
				listOfCourses.get(i).assingTeacher(teacher);
			}
		}
	}

	public String getAssignedTeachersList() {
		String list = "";

		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i).getTeacher() != null) {
				list = list + " - " + listOfCourses.get(i).getCourseName() + ": "
						+ listOfCourses.get(i).getTeacher().getName() + "\n";
			}
		}
		return list;
	}

	public String getUnassignedTeachersList() {
		String list = "";

		for (int i = 0; i < listOfUnassignedTeachers.size(); i++) {
			list = list + listOfUnassignedTeachers.get(i).getName() + "\n";
		}

		return list;
	}
	
	public void createTeacher() {

		String teacherName = "";
		String teacherEmail = "";
		int experience = 0;
		String av = "";
		String background = "";
		boolean isAccepted = false;

		Scanner in = new Scanner(System.in);
		System.out.println("Name: ");

		while (isAccepted) {
			try {
				if (in.hasNextLine()) {
					isAccepted = true;
					teacherName = in.nextLine();
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a valid name.");
			}
		}

		System.out.println("Email: ");
		isAccepted = false;
		while (isAccepted) {
			try {
				if (in.hasNextLine()) {
					isAccepted = true;
					teacherEmail = in.nextLine();
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a valid email.");
			}
		}

		System.out.println("Experience (in years): ");
		isAccepted = false;
		while (isAccepted) {
			try {
				if (in.hasNextInt()) {
					isAccepted = true;
					experience = in.nextInt();
					in.nextLine();
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, years of experience.");
			}
		}

		System.out.println("Availability: ");
		isAccepted = false;
		while (isAccepted) {

			try {
				if (in.hasNextLine()) {
					isAccepted = true;
					av = in.nextLine();
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter semester availability.");
			}
		}

		System.out.println("Background: ");
		while (isAccepted) {

			try {
				if (in.hasNextLine()) {
					isAccepted = true;
					background = in.nextLine();
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a background.");
			}
		}
		in.close();
		listOfUnassignedTeachers.add(new Teacher(teacherName, teacherEmail, experience, av, background));
	}
}
