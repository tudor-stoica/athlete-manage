package controllers;

import helpers.SchoolYear;
import models.Team;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.sports.create;
import views.html.sports.edit;

import javax.inject.Inject;

public class TeamController extends Controller {

    @Inject
    FormFactory formFactory;

    //Show all teams to user
    public Result index(){
        return ok(views.html.teams.index.render(Team.find.all()));
    }

    //Creates a team
    public Result create() {
        Team team = new Team(SchoolYear.currentSchoolYear());
        Form<Team> teamForm = formFactory.form(Team.class);
        return ok(views.html.teams.create.render(teamForm.fill(team)));
    }

    //Saves a team
    public Result save(){
        Form<Team> teamForm = formFactory.form(Team.class).bindFromRequest();
        teamForm.allErrors().forEach(System.out::println);
        // why is 2017-09-01T00:00 invalid for schoolyear?
        if (teamForm.hasErrors()) {
            return badRequest(views.html.teams.create.render(teamForm));
        }

        Team team = teamForm.get();
        team.save();

        return redirect(routes.TeamController.index());
    }

    //Edits a team
    public Result edit(Integer id){
        Team team = Team.find.byId(id);

        if (team == null) {
            return notFound();
        }

        Form<Team> teamForm = formFactory.form(Team.class).fill(team);
        return ok(views.html.teams.edit.render(teamForm));
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