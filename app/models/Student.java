package models;

import io.ebean.Finder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import helpers.SchoolYear;

/**
 * This program establishes the "Student" class for the athlete management app. The student class
 * will establish important attributes for each student athlete and obtain the number of points they
 * have accumulated based on what teams they have played on.
 *
 *
 * @version 1.0
 * @author Scrum Group 1 (Brandon, Simon, Nathan, Tudor, Paris)
 */

@Entity
public class Student extends Person {

    public int studentNumber;
    public int oen;
    public int grade;
    public String sex;

    @OneToMany(cascade=CascadeType.ALL)
    public List <Spot> spots = new ArrayList<>();

    public Student(String firstName, String lastName){
        super(firstName, lastName, "email@email.com");
    }

    public Student(String firstName, String lastName, String email, int studentNumber, int oen, int grade, String sex) {
        super(firstName, lastName, email);
        this.studentNumber = studentNumber;
        this.oen = oen;
        this.grade = grade;
        this.sex = sex;
    }

    /**
     * Displays the last name and first name of a student
     * @return String value of the students name in the format lastName,firstName
     */
    public String toString() {
        return super.getLastFirst();
    }

    /**
     * Totals the number of points a student accumulated throughout their high school career
     * @return integer value of the total number of points scored by a particular student
     */
    public Integer getPoints() {
        int total = 0;
        for (int i = 0; i < spots.size(); i++) {
            total += spots.get(i).points;
        }
        return total;
    }

    /**
     * Totals the number of points a student accumulated depending on their position(s) on various teams for a specific year
     * @param schoolYear The school year (Sep 01 referenced)
     * @return integer value of the  number of points earned by a particular student this year
     */
    public Integer getPoints(LocalDateTime schoolYear) {
        int total = 0;
        for (int i = 0; i < spots.size(); i++) {
            if (spots.get(i).team.schoolYear.equals(schoolYear)) {
                total += spots.get(i).points;
            }
        }
        return total;
    }


    //Finder for Student class
    public static Finder<Integer, Student> find = new Finder<>(Student.class);
}
