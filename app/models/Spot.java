package models;

import javax.persistence.Entity;

@Entity
public class Spot extends BaseModel {
    Team team;
    int points;
}
