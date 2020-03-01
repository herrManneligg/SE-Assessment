import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Administrator extends Person {

	private ArrayList<Course> listOfCourses;
	private ArrayList<Teacher> listOfUnassignedTeachers;
	private ClassDirector classDirector;

	public Administrator(String name, String email, ClassDirector classDirector) {
		super(name, email);
		listOfUnassignedTeachers = new ArrayList<>();
		this.classDirector = classDirector;
//		listOfCourses = new ArrayList<>();
		this.listOfCourses = classDirector.getListOfCourses();
	}

	public ArrayList<Course> getListOfCourses() {
		return listOfCourses;
	}

	public void setListOfCourses(ArrayList<Course> listOfCourses) {
		this.listOfCourses = listOfCourses;
	}

	public ArrayList<Teacher> getListOfUnassignedTeachers() {
		return listOfUnassignedTeachers;
	}

	public void setListOfUnassignedTeachers(ArrayList<Teacher> listOfUnassignedTeachers) {
		this.listOfUnassignedTeachers = listOfUnassignedTeachers;
	}

	public ClassDirector getClassDirector() {
		return classDirector;
	}

	public void setClassDirector(ClassDirector classDirector) {
		this.classDirector = classDirector;
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
				listOfUnassignedTeachers.remove(teacher);
			}
		}
	}

	public String getAssignedTeachersList() {
		String list = "List of assigned teachers: \n";

		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i).getTeacher() != null) {
				list = list + " - " + listOfCourses.get(i).getCourseName() + ": "
						+ listOfCourses.get(i).getTeacher().getName() + "\n";
			}
		}
		return list;
	}

	public String getUnassignedTeachersList() {
		String list = "List of unassigned teachers: \n";

		for (int i = 0; i < listOfUnassignedTeachers.size(); i++) {
			list = list + " - " + listOfUnassignedTeachers.get(i).getName() + "\n";
		}

		return list;
	}

	public void createTeacher() {

		String teacherName = "";
		String teacherEmail = "";
		int experience = 0;
		String semester = "";
		int av;
		String background = "";
		boolean isAccepted = false;

		Scanner in = new Scanner(System.in);

		while (!isAccepted) {
			System.out.print("Name: ");
			try {
				if (in.hasNextLine()) {

					teacherName = in.nextLine();
					if (isLetter(teacherName) == true && teacherName.length() < 15) {
						teacherName = teacherName.substring(0, 1).toUpperCase()
								+ teacherName.substring(1).toLowerCase();
						isAccepted = true;
					} else {
						System.out.println(
								"Enter a name with alphabetical values with no accents, numbers, symbols or spaces (max. 15 characters).");
					}
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a valid name.");
			}
		}

		isAccepted = false;
		while (!isAccepted) {
			System.out.print("Email: ");
			try {
				if (in.hasNextLine()) {
					teacherEmail = in.nextLine();
					if (isEmail(teacherEmail) == true) {
						isAccepted = true;
					} else {
						System.out.println("Enter a real email.");
					}
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a valid email.");
			}
		}

		isAccepted = false;
		while (!isAccepted) {
			System.out.print("Experience (in years): ");
			try {
				if (in.hasNextInt()) {
					experience = in.nextInt();

					if (isLetter(Integer.toString(experience)) == false && experience >= 0 && experience <= 35) {
						isAccepted = true;
						in.nextLine();
					} else {
						System.out.println("Please enter a positive value and less than 36 years.");
					}

				} else {
					System.out.println("Please enter the years of experience.");
					in.next();
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, years of experience.");
			}
		}

		System.out.print("When is available?\n(1) Semester 1\n(2) Semester 2\n(3) All academic year\n");
		isAccepted = false;
		while (!isAccepted) {

			try {
				av = in.nextInt();
				if (1 <= av && av <= 3) {
					if (av == 1) {
						semester = "Semester I";
					} else if (av == 2) {
						semester = "Semester II";
					} else if (av == 3) {
						semester = "All academic year";
					}
					isAccepted = true;
					in.nextLine();
				} else {
					System.out.println("Please, select one of the given options.");
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter the semester availability pressing 1, 2 or 3 in the keyboard.");
				in.next();
			}
		}

		System.out.print("Background: ");
		isAccepted = false;
		while (!isAccepted) {

			try {
				if (in.hasNextLine()) {
					isAccepted = true;
					background = in.nextLine();
				}

			} catch (InputMismatchException e) {
				System.err.println("Please, enter a background.");
			}
		}

		listOfUnassignedTeachers.add(new Teacher(teacherName, teacherEmail, experience, semester, background));
		in.close();
	}

	public boolean isLetter(String checkText) {
		String textUpper = checkText.toUpperCase();

		if (checkText.length() < 3) {
			return false;
		}

		for (int i = 0; i < textUpper.length(); i++) {
			if (textUpper.charAt(i) < 'A' || textUpper.charAt(i) > 'Z' || textUpper.charAt(1) == ' ') {
				return false;
			}

		}
		return true;
	}

	public boolean isEmail(String checkText) {

		if (checkText.length() > 6) {
			for (int i = 0; i < checkText.length(); i++) {
				if ((checkText.charAt(0) != '@'
						&& (checkText.charAt(i) == '@' && ((checkText.charAt(checkText.length() - 4) == '.')
								&& ((checkText.charAt(checkText.length() - 5)) != '@')
								|| ((checkText.charAt(checkText.length() - 3) == '.'
										&& (checkText.charAt(checkText.length() - 4)) != '@')))))) {
					return true;
				}
			}
		}
		return false;
	}
}
