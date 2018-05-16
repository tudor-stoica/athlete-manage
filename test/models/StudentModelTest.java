package models;

import org.junit.*;
import play.test.WithApplication;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class StudentModelTest extends WithApplication {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void testStudentIdInfo(){
        Student test = new Student("John", "Doe");
        test.studentNumber = 1034012;
        test.oen = 365726342;
        test.email = "john.doe19@ycdsbk12.ca";
        assertEquals(1034012, test.studentNumber);
        assertEquals(365726342, test.oen);
        assertEquals("john.doe19@ycdsbk12.ca", test.email);
    }

    @Test
    public void testStudentBasicInfo(){
        Student test = new Student("John", "Doe");
        test.sex = "Male";
        test.grade = 12;
        assertEquals("John",test.firstName );
        assertEquals("Doe", test.lastName);
        assertEquals(12, test.grade);
    }

    @Test
    public void testToString() {
        Student test = new Student("John", "Doe");
        assertEquals("Doe, John", test.toString());
    }

    @Test
    public void dataBaseTest(){
        new Student ("John", "Doe").save();
        Student test = Student.find.query().where().eq("firstName", "John").findOne();
        assertEquals("John", test.firstName);
    }

    @Test
    public void testGetPoints(){
        Team teams = new Team();
    }

}
