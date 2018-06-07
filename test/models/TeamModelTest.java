package models;

import helpers.SchoolYear;
import org.junit.*;
import play.test.WithApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class TeamModelTest extends WithApplication{

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void testCanSaveAndFindTeam() {
        new Team("Junior", "Male", "Basketball", 0, LocalDateTime.now(), "Winter").save();
        Team team = Team.find.query().where()
                .eq("division", "Junior")
                .findOne();
        assertEquals("Junior", team.division);
        assertEquals("Male", team.gender);
        assertEquals("Basketball", team.sport);
    }

    @Test
    public void testToString() {
        Team team = new Team("Senior", "Female", "Basketball", 0, LocalDateTime.now(), "Winter");
        assertEquals("Senior Girls' Basketball", team.toString());
    }

    @Test
    public void testAddPlayer() {
        Student student = new Student("Joey", "Test", "email", 0, 0, 11, "Male");
        student.save();
        Team team = new Team("Senior", "Male", "Soccer", 0, LocalDateTime.now(), "Winter");

        team.addPlayer(student.id);
        team.save();

        Integer id = team.spots.get(team.spots.size() -  1).student.id;
        assertEquals(student.id, id);
    }

    @Test
    public void testDeletePlayer(){
        Student student = new Student("Joey", "Test", "email", 0, 0, 11, "Male");
        student.save();
        Team team = new Team("Senior", "Male", "Soccer", 0, LocalDateTime.now(), "Winter");

        team.addPlayer(student.id);
        team.save();

        assertEquals(1, team.spots.size());

        team.removePlayer(student.id);
        team.save();

        assertEquals(0, team.spots.size());
    }

    @Test
    public void testFindByCoach(){
        Team basketball = new Team("", "", "Basketball", 0, LocalDateTime.now(), "Winter");
        basketball.coaches.add("emailOne");
        basketball.coaches.add("emailTwo");
        basketball.save();

        Team baseball = new Team("", "", "Baseball", 0, LocalDateTime.now(), "Winter");
        baseball.coaches.add("emailThree");
        baseball.coaches.add("emailOne");
        baseball.save();

        Team rugby = new Team("", "", "Rugby", 0, LocalDateTime.now(), "Winter");
        rugby.coaches.add("EmailTwo");
        rugby.coaches.add("EmailThree");
        rugby.save();

        List<Team> teams = Team.findByCoach("emailOne");
        assertEquals(2, teams.size());
    }
}
