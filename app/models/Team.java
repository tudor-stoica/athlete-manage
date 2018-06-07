package models;

import io.ebean.Finder;
import io.ebean.annotation.DbArray;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseModel{
    @Constraints.Required
    public String division;
    @Constraints.Required
    public String gender;
    @Constraints.Required
    public String sport;
    @Constraints.Required
    public String season;

    @OneToMany(cascade = CascadeType.REMOVE)
    public List<Spot> spots = new ArrayList<>();

    @Constraints.Required
    public LocalDateTime schoolYear;
    public String banquetInfo;
    @DbArray
    public List<String> coaches = new ArrayList<>();
    public Student MVP;
    public Student MIP;
    @Constraints.Required
    public Integer defaultPoints;

    public Team(String division, String gender, String sport, Integer defaultPoints, LocalDateTime schoolYear, String season){
        this.division = division;
        this.gender = gender;
        this.sport = sport;
        this.defaultPoints = defaultPoints;
        this.schoolYear = schoolYear;
        this.season = season;
    }

    public String toString(){
        return division + " " + gender+ " " + sport;
    }

    public void addPlayer(Integer id) {
        addPlayer(Student.find.byId(id));
    }

    public void addPlayer(Student student) {
        Spot newPlayer = new Spot();
        newPlayer.student = student;
        spots.add(newPlayer);
    }

    public void removePlayer(Integer id) {
        removePlayer(Student.find.byId(id));
    }

    public void removePlayer(Student student){
        for(int i = 0; i < spots.size(); i++) {
            if (spots.get(i).student.id == student.id) {
                Spot spot = spots.get(i);
                spots.remove(spot);
            }
        }
    }

    public static List<Team> findByCoach(String email){
        return find.query().where().contains("coaches", email).findList();
    }

    public static Finder<Integer, Team> find = new Finder<>(Team.class);

    public static List<Team> allTeams(){
        return find.all();
    }
}
