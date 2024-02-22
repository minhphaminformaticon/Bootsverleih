package controllers;
import forms.BoatManagement;
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

        Form<ReservationForm> reservationForm = formFactory.form(ReservationForm.class);
        reservationForm.withDirectFieldAccess(true);
        return ok(renderIndexView(request, reservationForm, false));
    }

    public Result submitReservation(Http.Request request) throws FileNotFoundException {
        Form<ReservationForm> submitForm = formFactory
                .form(ReservationForm.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);

        if(submitForm.hasErrors()){
            return badRequest(renderIndexView(request, submitForm, true));
        }
        ReservationForm reservationForm = submitForm.get();
        ReservationRequests reservationRequests = new ReservationRequests();
        reservationRequests.firstName = reservationForm.firstName;
        reservationRequests.lastName = reservationForm.lastName;
        reservationRequests.email = reservationForm.email;
        reservationRequests.number = reservationForm.phone;
        reservationRequests.timeFrom = reservationForm.timeFromLocalTime;
        reservationRequests.timeTo = reservationForm.timeToLocalTime;
        reservationRequests.reservationDate = reservationForm.dateLocalDate;
        reservationRequests.save();

        return ok(views.html.showReservation.render(reservationForm));
    }

    private Html renderIndexView(Http.Request request, Form form, boolean showReservation){
        return views.html.index.render(form, request, messages.preferred(request), showReservation);
    }
    public Result showReservation(Http.Request request){
        Form<ReservationForm> reservationForm = formFactory.form(ReservationForm.class);
        return ok(renderIndexView(request, reservationForm, true));
    }
    public Result drinks(Http.Request request){
        return ok(views.html.drinks.render());
    }

    public Result aboutUs(Http.Request request){
        return ok(views.html.aboutUs.render());
    }

    public Result manage(Http.Request request){
        Form<BoatManagement> boatManagementForm = formFactory.form(BoatManagement.class);
        boatManagementForm.withDirectFieldAccess(true);
        return ok(views.html.manage.render(boatManagementForm, request, messages.preferred(request)));
    }

    public Result submitBoatManagement(Http.Request request) throws FileNotFoundException{
        Form<BoatManagement> submitBoatManagement = formFactory
                .form(BoatManagement.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);

        if (submitBoatManagement.hasErrors()){
            return badRequest(views.html.manage.render(submitBoatManagement, request, messages.preferred(request)));
        }
        return ok(views.html.manage.render(submitBoatManagement, request, messages.preferred(request)));
    }
}