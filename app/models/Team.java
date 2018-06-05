package models;

import io.ebean.Finder;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Team extends BaseModel {
    LocalDateTime schoolYear;


    public static Finder<Integer, Team> find = new Finder<>(Team.class);
}
