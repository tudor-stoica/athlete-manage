package models;

import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

public class SpotModelTest extends WithApplication {

    @Before
    public void setUp() {start(fakeApplication(inMemoryDatabase()));}

    @Test
    public void testStoreSpotDB() {
        Team team = new Team("baseball",100);
        team.save();

        Student student = new Student("vic", "ko");
        student.save();

        new Spot(team, student).save();

        Spot spot = Spot.find.query().where()
                .eq("student.firstName", "vic")
                .findOne();

        assertNotNull(spot);
        assertEquals("baseball", spot.team.sport);
        assertEquals("vic", spot.student.firstName);
        assertEquals(100, spot.points);
    }

    // test not allowed to have two spots with same team and student
    @Test(expected = io.ebean.DuplicateKeyException.class)
    public void testDuplicate() {
        new Team("baseball",100).save();
        new Student("vic", "ko").save();

        Team team = Team.find.query().where().eq("sport", "baseball").findOne();
        Student student = Student.find.query().where().eq("firstName", "vic").findOne();

        new Spot(team, student).save();
        new Spot(team, student).save();

    }

    
}

