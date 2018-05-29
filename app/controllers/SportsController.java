package controllers;

import models.Sport;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.sports.*;

import javax.inject.Inject;
import java.util.List;

public class SportsController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result index() {
        List<Sport> sports = Sport.find.all();
        return ok(index.render(sports));
    }

    public Result create() {
        Form<Sport> sportForm = formFactory.form(Sport.class);
        return ok(create.render(sportForm));
    }

    public Result save() {
        Form<Sport> sportForm = formFactory.form(Sport.class).bindFromRequest();
        if (sportForm.hasErrors()) {
            return badRequest(create.render(sportForm));
        }
        Sport sport = sportForm.get();
        sport.save();
        return redirect(routes.SportsController.index());
    }

    public Result edit(Integer id) {
        Sport sport = Sport.find.byId(id);
        if (sport == null)
            return notFound();
        Form<Sport> sportForm = formFactory.form(Sport.class).fill(sport);
        return ok(edit.render(sportForm));
    }

    public Result update() {
        Form<Sport> sportForm = formFactory.form(Sport.class).bindFromRequest();
        if (sportForm.hasErrors())
            return badRequest();

        Sport updatedSport = sportForm.get();
        Sport oldSport = Sport.find.byId(updatedSport.id);

        if (oldSport == null)
            return notFound();

        oldSport.name = updatedSport.name;
        oldSport.pointValue = updatedSport.pointValue;
        oldSport.save();
        return ok();
    }

    public Result destroy(Integer id) {
        Sport sport = Sport.find.byId(id);
        if (sport == null)
            return notFound();

        sport.delete();
        return ok();
    }
}
