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
        Team t1 = new Team();
        //new Spot(t1,0,stu,false,false).save();
        new Student("vic", "ko").save();
        new Spot(t1,0,Student.find.query().where().eq("firstName", "vic").findOne(),false,false).save();

        Spot spot2 = Spot.find.query().where()
                .eq("student.firstName", "vic")
                .findOne();
        assertNotNull(spot2);
        //assertEquals(t1, spot2.team); //errors (b/c needs to be in DB)?
        assertEquals("vic", spot2.student.firstName);
        assertEquals(0, spot2.points);
    }
    
}

