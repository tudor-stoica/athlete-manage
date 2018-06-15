package controllers;

import helpers.SchoolYear;
import models.Sport;
import models.Team;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.sports.create;
import views.html.sports.edit;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamController extends Controller {

    @Inject
    FormFactory formFactory;

    //Show all teams to user
    public Result index(){
        return ok(views.html.teams.index.render(Team.find.all()));
    }

    //Creates a team
    public Result create() {
        List<Sport> sports = Sport.find.all();


        SchoolYear schoolYear = new SchoolYear();
        String formattedSchoolYear = schoolYear.formatSchoolYear(LocalDateTime.now());
        Team team = new Team(formattedSchoolYear);
        Form<Team> teamForm = formFactory.form(Team.class);
        return ok(views.html.teams.create.render(teamForm.fill(team), sports));
    }

    //Saves a team
    public Result save(){
        List<Sport> sports = Sport.find.all();

        Form<Team> teamForm = formFactory.form(Team.class).bindFromRequest();
        if (teamForm.hasErrors()) {
            return badRequest(views.html.teams.create.render(teamForm, sports));

        }
        Team team = teamForm.get();
        team.save();

        return redirect(routes.TeamController.index());
    }

    //Edits a team
    public Result edit(Integer id){
        Team team = Team.find.byId(id);
        List<Sport> sports = Sport.find.all();

        if (team == null) {
            return notFound();
        }

        Form<Team> teamForm = formFactory.form(Team.class).fill(team);
        return ok(views.html.teams.edit.render(teamForm, sports));
    }

    //Updates a team
    public Result update() {
        Form<Team> teamForm = formFactory.form(Team.class).bindFromRequest();
        if (teamForm.hasErrors())
            return badRequest();

        Team updatedTeam = teamForm.get();
        Team oldTeam = Team.find.byId(updatedTeam.id);

        if (oldTeam == null)
            return notFound();

        oldTeam.division = updatedTeam.division;
        oldTeam.gender = updatedTeam.gender;
        oldTeam.sportName = updatedTeam.sportName;
        oldTeam.defaultPoints = updatedTeam.defaultPoints;
        oldTeam.schoolYear = updatedTeam.schoolYear;
        oldTeam.season = updatedTeam.season;
        oldTeam.save();
        return ok();
    }

    //Deletes a team
    public Result remove(Integer id) {
        Team team = Team.find.byId(id);

        if (team == null) {
            return notFound();
        }

        team.delete();
        return redirect(routes.TeamController.index());
    }
}