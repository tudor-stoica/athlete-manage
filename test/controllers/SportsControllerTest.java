package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.TestHelpers;
import io.ebean.Ebean;
import models.Sport;
import org.junit.Before;
import org.junit.Test;
import play.api.test.CSRFTokenHelper;
import play.mvc.Http;
import play.mvc.Http.RequestBuilder;
import play.mvc.Result;
import play.test.WithApplication;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class SportsControllerTest extends WithApplication {

    @Before
    public void setUp() {
        Sport.find.all().forEach(Ebean::delete);
    }
//     Index Tests -----------------------------------------------------------

    @Test
    public void testIndexOK() {
        Result result = new SportsController().index();
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        assertTrue("Index should have 'Sports' in the title", contentAsString(result).contains("Sports"));
    }

    @Test
    public void testIndexShowsListOfSports() {
        Sport sport1 = new Sport("sport1", 10);
        Sport sport2 = new Sport("sport2", 5);
        sport1.save();
        sport2.save();

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/sports");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("sport1"));
        assertTrue(contentAsString(result).contains("10"));
        assertTrue(contentAsString(result).contains("sport2"));
        assertTrue(contentAsString(result).contains("5"));
    }

//  Create/Save Tests -----------------------------------------------------------

    @Test
    public void testCreateOK() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/sports/create");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("<h1>Create Sport</h1>"));
    }

    @Test
    public void testSaveToDBAndRedirectToSportIndex() throws IOException {

        JsonNode jsonNode = TestHelpers.createJsonNode("{\"name\":\"Volleyball\", \"pointValue\": \"10\"}");

        RequestBuilder request = new RequestBuilder()
                .method(POST)
                .bodyJson(jsonNode)
                .uri(controllers.routes.SportsController.save().url());

        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());

        // check if saved sport appears on index
        request = new RequestBuilder()
                .method(GET)
                .uri(controllers.routes.SportsController.index().url());

        result = route(app, request);
        assertTrue(contentAsString(result).contains("Volleyball"));
    }

    @Test
    public void testAttemptSaveWithoutNameRejected() throws IOException{
        JsonNode jsonNode = TestHelpers.createJsonNode("{\"name\":\"\", \"pointValue\": \"10\"}");

        RequestBuilder request = new RequestBuilder()
                .method(POST)
                .bodyJson(jsonNode)
                .uri(controllers.routes.SportsController.save().url());
        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testAttemptSaveWithoutPointValueRejected() throws IOException{
        JsonNode jsonNode = TestHelpers.createJsonNode("{\"name\":\"Volleyball\", \"pointValue\": \"\"}");

        RequestBuilder request = new RequestBuilder()
                .method(POST)
                .bodyJson(jsonNode)
                .uri(controllers.routes.SportsController.save().url());
        request = CSRFTokenHelper.addCSRFToken(request);
        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

//    Edit/Update Tests -------------------------------------------------
    @Test
    public void testEditOKAndRendersProperly() {
        new Sport("Volley", 10).save();
        Sport sport = Sport.find.query().where().eq("name", "Volley").findOne();

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/sports/edit/" + sport.id);

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("<h1>Edit</h1>"));
        assertTrue(contentAsString(result).contains(sport.name));
        assertTrue(contentAsString(result).contains("<input type=\"hidden\" name=\"id\" value=\'" + sport.id + "\'>"));
    }

    @Test
    public void testEditGives404OnBadId() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/sports/edit/90876986");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

    @Test
    public void testUpdateOK() throws IOException {
        new Sport("VBall", 8).save();
        Sport sport = Sport.find.query().where().eq("name", "VBall").findOne();

        RequestBuilder request = getSportUpdateRequest("{\"id\": \"" + sport.id + "\", \"name\":\"Volleyball\", \"pointValue\": \"10\"}");
        Result result = route(app, request);

        assertEquals(OK, result.status());

        Sport updatedSport = Sport.find.byId(sport.id);
        assertEquals("Volleyball", updatedSport.name);
        assertEquals(10, updatedSport.pointValue.intValue());
    }

    @Test
    public void testUpdateGives404OnBadId() throws IOException {
        new Sport("VBall", 8).save();
        Sport sport = Sport.find.query().where().eq("name", "VBall").findOne();

        RequestBuilder request = getSportUpdateRequest("{\"id\": \"4657689\", \"name\":\"Volleyball\", \"pointValue\": \"10\"}");
        Result result = route(app, request);

        assertEquals(NOT_FOUND, result.status());
    }

    @Test
    public void testUpdateGivesBadRequestOnHasFormError() throws IOException {
        new Sport("VBall", 8).save();
        Sport sport = Sport.find.query().where().eq("name", "VBall").findOne();

        RequestBuilder request = getSportUpdateRequest("{\"id\": \"" + sport.id + "\", \"name\":\"\", \"pointValue\": \"10\"}");
        Result result = route(app, request);

        assertEquals(BAD_REQUEST, result.status());
    }

    private RequestBuilder getSportUpdateRequest(String jsonString) throws IOException {
        JsonNode jsonNode = TestHelpers.createJsonNode(jsonString);

        return new RequestBuilder()
                .method(PUT)
                .bodyJson(jsonNode)
                .uri(controllers.routes.SportsController.update().url());
    }

//    Destroy Tests -------------------------------------------------
    @Test
    public void testDestroyOk() {
        new Sport("Hockey", 10).save();
        Sport sport = Sport.find.query().where().eq("name", "Hockey").findOne();
        RequestBuilder request = new RequestBuilder()
                .method(DELETE)
                .uri(controllers.routes.SportsController.destroy(sport.id).url());

        Result result = route(app, request);
        assertEquals(OK, result.status());

        Sport deletedSport = Sport.find.byId(sport.id);
        assertNull(deletedSport);
    }

    @Test
    public void testDestroyReturnsNotFoundOnBadId() {
        RequestBuilder request = new RequestBuilder()
                .method(DELETE)
                .uri(controllers.routes.SportsController.destroy(5678).url());

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

}
