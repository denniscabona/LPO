package lab05;

public class Person implements PersonInterface {

	private static String validName = "[A-Z][a-z]+(\\s+[A-Z][a-z]+)*";
	private String firstName, lastName;
	private String middleName; // optional, allowed to be null

	// @ invariant firstName.matches(Person.validName) && lastName.matches(Person.validName)

	public Person(String firstName, String lastName) {
		if (!firstName.matches(Person.validName) || !lastName.matches(Person.validName))
			throw new IllegalArgumentException("invalid name");
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Person(String firstName, String middleName, String lastName) {
		this(firstName, lastName);
		if (!middleName.matches(Person.validName))
			throw new IllegalArgumentException("invalid name");
		this.middleName = middleName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

}
