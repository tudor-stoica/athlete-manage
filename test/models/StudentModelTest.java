package models;

import helpers.SchoolYear;
import org.junit.*;
import play.test.WithApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class StudentModelTest extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void testShouldDBSaveAndRecallOk(){
        //String firstName, String lastName, String email, int studentNumber, int oen, int grade, String sex
        new Student ("John", "Doe", "jd@test.com", 12345, 4321, 11, "male").save();
        Student student = Student.find.query().where().eq("oen", 4321).findOne();
        assertEquals("John", student.firstName);
    }

    @Test
    public void testToString() {
        Student student = new Student("John", "Doe");
        assertEquals("Doe, John", student.toString());
    }

    @Test
    public void testGetPointsShouldBe_11() {
        Student student = new Student("John", "Doe");
        Spot s1 = new Spot();
        Spot s2 = new Spot();
        s1.points = 5;
        s2.points = 6;
        student.spots.add(s1);
        student.spots.add(s2);
        assertEquals(11, student.getPoints().intValue());
    }

    @Test
    public void testGetPointsAcrossAllSchoolYears() {
        Student student = new Student("John", "Doe");
        Spot s1 = new Spot();
        Spot s2 = new Spot();
        s1.points = 5;
        s2.points = 6;

        Team t1 = new Team();
        t1.schoolYear = SchoolYear.toSchoolYear(LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        s1.team = t1;

        Team t2 = new Team();
        t2.schoolYear = SchoolYear.toSchoolYear(LocalDateTime.of(2011, 1, 1, 0, 0, 0));
        s2.team = t2;

        student.spots.add(s1);
        student.spots.add(s2);

        assertEquals(11, student.getPoints().intValue());
    }

    @Test
    public void testGetPointsWithSchoolYear() {
        Student student = new Student("John", "Doe");
        Spot s1 = new Spot();
        Spot s2 = new Spot();
        s1.points = 5;
        s2.points = 6;

        Team team = new Team();
        team.schoolYear = SchoolYear.toSchoolYear(LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        s1.team = team;
        s2.team = team;
        student.spots.add(s1);
        student.spots.add(s2);

        assertEquals(11, student.getPoints(SchoolYear.toSchoolYear(LocalDateTime.of(2000, 1, 1, 0, 0, 0))).intValue());
    }

    @Test
    public void testGetPointsWithDifferentSchoolYears() {
        Student student = new Student("John", "Doe");
        Spot s1 = new Spot();
        Spot s2 = new Spot();
        s1.points = 5;
        s2.points = 6;

        Team t1 = new Team();
        t1.schoolYear = SchoolYear.toSchoolYear(LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        s1.team = t1;

        Team t2 = new Team();
        t2.schoolYear = SchoolYear.toSchoolYear(LocalDateTime.of(2011, 1, 1, 0, 0, 0));
        s2.team = t2;

        student.spots.add(s1);
        student.spots.add(s2);

        assertEquals(5, student.getPoints(SchoolYear.toSchoolYear(LocalDateTime.of(2000, 1, 1, 0, 0, 0))).intValue());
    }

}
