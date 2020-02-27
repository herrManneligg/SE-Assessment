
public abstract class Person {
	
	String name;
	String email;

	public Person(String n, String em) {
		this.name = n;
		this.email = em;
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
}
