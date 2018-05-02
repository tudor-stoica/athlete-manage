package models;

import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static junit.framework.TestCase.assertEquals;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

public class StudentModelTest extends WithApplication{

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void testTest(){
        int out = 4;
        int in = 2*2;
        assertEquals(out, in);
    }

}
