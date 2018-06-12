package models;

import helpers.SchoolYear;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

public class SpotModelTest extends WithApplication {

    @Before
    public void setUp() {start(fakeApplication(inMemoryDatabase()));}

    @Test
    public void testStoreSpotDB() {
        Team team = new Team("Junior", "Male", "Basketball", 5, SchoolYear.currentSchoolYear(), "Winter");
        team.save();

        Student student = new Student("vic", "ko");
        student.save();

        team.addPlayer(student);

        Spot spot = Spot.find.query()
                .fetch("student")
                .fetch("team")
                .where()
                .eq("team.sportName", "Basketball")
                .findOne();

        assertNotNull(spot);
        assertEquals("Basketball", spot.team.sportName);
        assertEquals("vic", spot.student.firstName);
        assertEquals(5, spot.points.intValue());
    }

    // test not allowed to have two roster with same team and student
    @Test(expected = javax.persistence.PersistenceException.class)
    public void testDuplicate() {
        new Team("Junior", "Male", "Basketball", 5, SchoolYear.currentSchoolYear(), "Winter").save();
        new Student("vic", "ko").save();

        Team team = Team.find.query().where().eq("sport", "baseball").findOne();
        Student student = Student.find.query().where().eq("firstName", "vic").findOne();

        team.addPlayer(student);
        team.addPlayer(student);
        fail("Should not be able to add same student to roster");
    }

    
}

