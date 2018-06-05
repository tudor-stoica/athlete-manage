package models;

import io.ebean.Finder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

// A TON OF ERRORS
// java.sql.Statement.execute: create table spot
// Column "team" not found;
//@Table(
//        uniqueConstraints =
//        @UniqueConstraint(columnNames = {"team", "student"})
//)

@Entity
public class Spot extends BaseModel {

    @ManyToOne
    public Team team;
    @ManyToOne
    public Student student;

    public int points;
    public Boolean ofsaa;
    public Boolean paid;


    public Spot(Team team, int points, Student student, Boolean ofsaa, Boolean paid) {
        this.team = team;
        this.points = points;
        this.student = student;
        this.ofsaa = ofsaa;
        this.paid = paid;
    }

    public static Finder<Integer, Spot> find = new Finder<>(Spot.class);

}
