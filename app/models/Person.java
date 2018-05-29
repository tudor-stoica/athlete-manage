package models;

import io.ebean.Finder;

import javax.persistence.MappedSuperclass;

/**
 * Super class of student, admin and super admin
 * Will return email and formatted names
 */
@MappedSuperclass
public class Person extends BaseModel{
    public String firstName;
    public String lastName;
    public String email;

    /**
     * Creates a person with a specified name.
     * @param firstName
     *          The person's first name
     * @param lastName
     *          The person's last name
     * @param email
     *          The person's email
     */
    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    /**
     * Get the person's full name in the order of "last, first".
     * @return A String representing the person's last name, first name.
     */
    public String getLastFirst() {
        return this.lastName + ", " + this.firstName;
    }
    
    /**
     * Get the person's full name in the order of "first last".
     * @return A String representing the person's first name and last name.
     */
    public String getFirstLast() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Get the person's email
     * @return A String representing the person's email
     */
    public String getEmail() {
        return this.email;
    }

    public static Finder<String, Person> find = new Finder<>(Person.class);
}
