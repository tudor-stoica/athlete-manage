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
}
