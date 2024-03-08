package controllers;
import actions.LoginAction;
import forms.BoatManagement;
import forms.Login;
import forms.ReservationForm;
import models.ReservationRequests;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;
import play.twirl.api.Html;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Optional;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */

public class HomeController extends Controller {

    @Inject
    protected FormFactory formFactory;


    @Inject
    protected MessagesApi messages;
    PrintStream printingFile;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) {
        return ok(renderIndexView(request, getNewReservationForm(), false)).addingToSession(request, "test-cookie", "egal");
    }

    @With(LoginAction.class)
    public Result drinks(Http.Request request){
        return ok(views.html.drinks.render( getNewReservationForm(), request, messages.preferred(request), false));
    }


    public Result aboutUs(Http.Request request){
        if (1 == 1) {
            throw new RuntimeException("hallo");
        }
        return ok(views.html.aboutUs.render( getNewReservationForm(), request, messages.preferred(request), false));
    }

//    @Security.Authenticated
    @With(LoginAction.class)
    public Result submitReservation(Http.Request request) throws FileNotFoundException {
        Form<ReservationForm> submitForm = formFactory
                .form(ReservationForm.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);

        if(submitForm.hasErrors()){
            Http.Headers headers = request.getHeaders();
            Optional<String> optReferer = headers.get("Referer");
            if (optReferer.isPresent()) {
                String value = optReferer.get();
                String shortenedValue = value.substring(value.lastIndexOf("/"));
                switch (shortenedValue) {
                    case "/drinks":
                        return badRequest(views.html.drinks.render(submitForm, request, messages.preferred(request), true));

                    case "/aboutUs":
                        return badRequest(views.html.aboutUs.render(submitForm, request, messages.preferred(request), true));

                    default:
                        return badRequest(renderIndexView(request, submitForm, true));
                }

            } else {
                return badRequest(renderIndexView(request, submitForm, true));
            }
        }
        ReservationForm reservationForm = submitForm.get();
        ReservationRequests reservationRequests = new ReservationRequests();
        reservationRequests.timeFrom = reservationForm.timeFromLocalTime;
        reservationRequests.timeTo = reservationForm.timeToLocalTime;
        reservationRequests.reservationDate = reservationForm.dateLocalDate;
        reservationRequests.boatFKID = null;
        reservationRequests.save();

        return ok(views.html.showReservation.render(reservationForm, submitForm, request, messages.preferred(request), true));
    }

    private Html renderIndexView(Http.Request request, Form<ReservationForm> form, boolean showReservation){
        return views.html.index.render(form, request, messages.preferred(request), showReservation);
    }
    public Result showReservation(Http.Request request){
        Form<ReservationForm> reservationForm = formFactory.form(ReservationForm.class);
        return ok(renderIndexView(request, reservationForm, true));
    }

    public Result manage(Http.Request request){
        Form<BoatManagement> boatManagementForm = formFactory.form(BoatManagement.class);
        boatManagementForm.withDirectFieldAccess(true);
        return ok(views.html.manage.render(boatManagementForm, getNewReservationForm(), request, messages.preferred(request), true));
    }
    public Result login(Http.Request request){
        Form<Login> loginForm = formFactory.form(Login.class);
        loginForm.withDirectFieldAccess(true);
        return ok(views.html.login.render(loginForm, request, messages.preferred(request)));
    }
    public Result submitBoatManagement(Http.Request request) throws FileNotFoundException{
        Form<BoatManagement> submitBoatManagement = formFactory
                .form(BoatManagement.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);
        if (submitBoatManagement.hasErrors()){
            return badRequest(views.html.manage.render(submitBoatManagement, getNewReservationForm(), request, messages.preferred(request), true));
        }
        return ok(views.html.manage.render(submitBoatManagement, getNewReservationForm(), request, messages.preferred(request), false));
    }

    public Result submitLogin(Http.Request request) throws FileNotFoundException{
        // TODO
        Form<BoatManagement> submitBoatManagement = formFactory
                .form(BoatManagement.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);
        if (submitBoatManagement.hasErrors()){
            return badRequest(views.html.manage.render(submitBoatManagement, getNewReservationForm(), request, messages.preferred(request), true));
        }
        return ok(views.html.manage.render(submitBoatManagement, getNewReservationForm(), request, messages.preferred(request), false));
    }

    private Form<ReservationForm> getNewReservationForm(){
        Form<ReservationForm> reservationForm = formFactory.form(ReservationForm.class);
        reservationForm.withDirectFieldAccess(true);
        return reservationForm;
    }
}