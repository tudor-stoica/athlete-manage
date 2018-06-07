package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Spot extends BaseModel {

    @ManyToOne
    Team team;
    int points;
    @ManyToOne
    Student student;

    public Spot(Team team, Student student) {
        this.team = team;
        this.student = student;
        this.points = team.defaultPoints;
    }
}
