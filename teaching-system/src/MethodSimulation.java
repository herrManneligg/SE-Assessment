//testing the methods from ListOfRequirements & Course
public class MethodSimulation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Course courseOne = new Course("First Course");
		Course courseTwo = new Course("Second Course");
		Teacher ronPoet = new Teacher("Ron Poet", "ron.poet@student.gla.ac.uk",7,"Spring 2020","Software Engineering");
		Teacher nothingBert = new Teacher ("Nothing Bert", "nothing@knownothing.com",0, "Never", "Nothing");
		ListOfRequirements listForSoftwareEnginering = new ListOfRequirements(courseOne, 4, "Spring 2020");
		ListOfRequirements listForTesting = new ListOfRequirements( 3, "Spring 2121");
	
	//Testing Methods of Course-Class
		System.out.println(courseOne.getCourseInfo());
		System.out.println(courseOne.readList());
		courseOne.approve();
		courseOne.assingTeacher(ronPoet);
		System.out.println(courseOne.getCourseInfo());
		courseOne.reject();
		System.out.println(courseOne.getCourseInfo());
		courseOne.update("Slight Name Change and New LoR", 4, "Never");
		System.out.println(courseOne.getCourseInfo());
		System.out.println(courseOne.readList());
		courseOne.assignRequirementsList(listForSoftwareEnginering);
		System.out.println(courseOne.readList());
		courseOne.update("Only Name updated", null, null);
		System.out.println(courseOne.getCourseInfo());
		courseOne.update("Experience + Name updated", 5, null);
		System.out.println(courseOne.getCourseInfo());
		System.out.println(courseOne.readList());
		courseOne.update("Experience + Name + Availability updated", 6, "Summer 2020");
		System.out.println(courseOne.getCourseInfo());
		System.out.println(courseOne.readList());
		courseOne.update(null, 0, "Summeroni 2020");
		System.out.println(courseOne.readList());
		courseOne.update(null, null, "Winteroni 2020");
		System.out.println(courseOne.readList());
		courseOne.update(null, 3, null);
		System.out.println(courseOne.readList());
		courseOne.update("Name + Availability updated", null, "2020");
		System.out.println(courseOne.getCourseInfo());
		System.out.println(courseOne.readList());
		courseOne.update(null, null, null);
		System.out.println(courseOne.readList());
		
	//Testing Methods of ListOfRequirements Class	
		System.out.println(listForTesting.isReady(null));
		System.out.println(listForTesting.isReady(ronPoet));
		System.out.println(listForTesting.isReady(nothingBert));
		System.out.println(listForSoftwareEnginering.isReady(null));
		System.out.println(listForSoftwareEnginering.isReady(ronPoet));
		System.out.println(listForSoftwareEnginering.isReady(nothingBert));
		System.out.println(listForTesting.filter());
		System.out.println(listForSoftwareEnginering.filter());
		courseTwo.assignRequirementsList(listForTesting);
		System.out.println(listForTesting.filter());
		//update Method was already Tested through the Update Methods in the Course Class, which are partly calling the update methods in the ListOfRequirementsClass
	}

}
