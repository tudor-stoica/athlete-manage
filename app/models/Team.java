package models;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Team extends BaseModel {
    LocalDateTime schoolYear;
}
