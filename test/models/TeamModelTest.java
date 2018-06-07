package models;

import helpers.SchoolYear;
import org.junit.*;
import play.test.WithApplication;

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
        assertEquals("Basketball", team.sportName);
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
        team.save();

        team.addPlayer(student);

        Spot spot = Spot.find.query()
                .fetch("team")
                .fetch("student")
                .where()
                .eq("team.id", team.id)
                .eq("student.id", student.id)
                .findOne();

        assertNotNull("Spot entry should be found.", spot);
        assertEquals(student.firstName, spot.student.firstName);
        assertEquals(team.toString(), spot.team.toString());
    }

    @Test
    public void testDeletePlayer(){
        Student student = Student.create("Joey", "Test", "email", 0, 0, 11, "Male");
        Team team = Team.create("Senior", "Male", "Soccer", 5, SchoolYear.currentSchoolYear(), "Winter");

        team.addPlayer(student.id);
        assertEquals(1, team.roster.size());

        team.removePlayer(student.id);
        assertEquals(0, team.roster.size());

        Spot removedSpot = Spot.find.query().where()
                .eq("team", team)
                .eq("student", student)
                .findOne();
        assertNull("Spot should be removed from Database.", removedSpot);
    }

    @Test
    public void testFindByCoach(){
        Team basketball = Team.create("", "", "Basketball", 0, SchoolYear.currentSchoolYear(), "Winter");
        basketball.addCoach("email@One.com");
        basketball.addCoach("email@Two.com");

        Team baseball = Team.create("", "", "Baseball", 0, SchoolYear.currentSchoolYear(), "Winter");
        baseball.addCoach("email@Three.com");
        baseball.addCoach("email@One.com");

        Team rugby = Team.create("", "", "Rugby", 0, SchoolYear.currentSchoolYear(), "Winter");
        rugby.addCoach("Email@Two.com");
        rugby.addCoach("Email@Three.com");

        List<Team> teams = Team.findByCoach("email@One.com");
        assertEquals(2, teams.size());
        assertEquals("Winter", teams.get(0).season);
    }
}
