package models;

import io.ebean.Finder;
import io.ebean.annotation.DbArray;
import play.data.format.Formats;
import play.data.validation.Constraints;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Constraint;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Team extends BaseModel{
    @Constraints.Required
    public String division;

    @Constraints.Required
    public String gender;

    @Constraints.Required
    public String sportName;

    @Constraints.Required
    public String season;

    @OneToMany
    public List<Spot> roster = new ArrayList<>();

    @Constraints.Required
    public LocalDateTime schoolYear;

    @Constraints.Required
    public Integer defaultPoints;

    @DbArray
    public List<String> coaches = new ArrayList<>();

    public Student mvp;
    public Student mip;
    public String banquetInfo;


    public Team(String division, String gender, String sportName, Integer defaultPoints, LocalDateTime schoolYear, String season){
        this.division = division;
        this.gender = gender;
        this.sportName = sportName;
        this.defaultPoints = defaultPoints;
        this.schoolYear = schoolYear;
        this.season = season;
    }

    public Team() {}

    public Team(LocalDateTime schoolYear) {
        this.schoolYear = schoolYear;
    }

    public static Team create(String division, String gender, String sportName, Integer defaultPoints, LocalDateTime schoolYear, String season) {
        Team t = new Team(division, gender, sportName, defaultPoints, schoolYear, season);
        t.save();
        return t;
    }


    public String toString(){
        Map<String, String> genderMap = new HashMap<String, String>() {{
            put("Female", "Girls'");
            put("Male", "Boys'");
        }};

        return division + " " + genderMap.get(gender) + " " + sportName;
    }

    public void addPlayer(Integer id) {
        addPlayer(Student.find.byId(id));
    }

    public void addPlayer(Student student) {
        roster.add(Spot.create(this, student));
    }

    public void removePlayer(Integer id) {
        removePlayer(Student.find.byId(id));
    }

    public void removePlayer(Student student){
        Spot spot = Spot.find.query().where()
                .eq("team", this)
                .eq("student", student)
                .findOne();

        roster.remove(spot);
        spot.delete();
    }

    public void addCoach(String email) {
        coaches.add(email);
        this.save();
    }

    public void removeCoach(String email) {
        coaches.remove(email);
        this.save();
    }

    public static List<Team> findByCoach(String email){
        return find.query().where().contains("coaches", email).findList();
    }

    public static Finder<Integer, Team> find = new Finder<>(Team.class);
}
