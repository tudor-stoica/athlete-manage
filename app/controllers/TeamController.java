package controllers;

import models.Team;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import javax.inject.Inject;

public class TeamController extends Controller {

    @Inject
    FormFactory formFactory;

    //Show all teams to user
    public Result index(){
        return ok(views.html.teams.index.render(Team.allTeams()));
    }

    //Creates a team
    public Result createTeam(){
        Form<Team> teamForm = formFactory.form(Team.class);
        return ok(views.html.teams.create.render(teamForm));
    }

    //Saves a team
    public Result saveTeam(){
        Form<Team> teamForm = formFactory.form(Team.class).bindFromRequest();
        Team team = teamForm.get();
        team.save();

        return redirect(routes.TeamController.index());
    }

    //Edits a team
    public Result editTeam(){
        return TODO;
    }

    //Deletes a team
    public Result removeTeam(){
        return TODO;
    }

    //Adds a student to a team
    public Result addStudent(){
        return TODO;
    }

    //Removes a student from a team
    public Result removeStudent(){
        return TODO;
    }

}