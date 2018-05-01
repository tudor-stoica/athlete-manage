package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student extends Model {
    
    @Id
    public int studentNumber;
    public int oen;
    public String firstName;
    public String lastName;
    public int grade;
    public String email;
    public String sex;
    public Payment[] payments;
    
    public String toString() {
        return lastName + firstName;
    }
}
