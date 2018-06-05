package models;

import io.ebean.Finder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseModel {
    public LocalDateTime schoolYear;
    public String sport;
    public int defaultPoints;

    public Team(String sport, int defaultPoints) {
        this.sport = sport;
        this.defaultPoints = defaultPoints;
    }


    public static Finder<Integer, Team> find = new Finder<>(Team.class);
}
