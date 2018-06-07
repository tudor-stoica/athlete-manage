package models;

import io.ebean.Finder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The spot class that are part of the
 * positions in a team. Contains the points, student, team, ofsaa, and whether it was paid for
 *
 * @author vic
 */
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"team_id", "student_id"})
)
@Entity
public class Spot extends BaseModel {

    @ManyToOne
    public Team team;
    @ManyToOne
    public Student student;

    public int points;
    public Boolean ofsaa;
    public Boolean paid;

    /**
     * the constructor
     * @param team
     *         the object of the team
     * @param student
     *          an object of the student that belongs in that spot
     */
    public Spot(Team team,Student student) {
        this.team = team;
        this.points = team.defaultPoints;
        this.student = student;
        ofsaa = false;
        paid = false;

    }

    public static Finder<Integer, Spot> find = new Finder<>(Spot.class);

}
