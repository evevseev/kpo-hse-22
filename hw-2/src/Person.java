import com.fasterxml.jackson.annotation.JsonIgnore;

public class Person {
    private final String firstName;
    private final String lastName;

    /**
     * Creates a new person.
     *
     * @param firstName the first name of the person
     * @param lastName  the last name of the person
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {
        this.firstName = "";
        this.lastName = "";
    }

    /**
     * Returns the first name of the person.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the person.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the full name of the person.
     */
    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
