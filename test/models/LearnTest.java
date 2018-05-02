package models;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LearnTest {
    @Test
    public void myFirstTest(){
        int expected = 4;
        int actual = 2*2;
        assertEquals(expected, actual);
    }
}
