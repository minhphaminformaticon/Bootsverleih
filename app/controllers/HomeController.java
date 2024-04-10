package controllers;
import actions.LoginAction;
import com.mysql.cj.Session;
import forms.Boat;
import forms.BoatManagement;
import forms.Login;
import forms.ReservationForm;
import io.ebeaninternal.server.expression.Op;
import io.ebeaninternal.server.util.Str;
import models.BoatManagementTable;
import models.BoatTable;
import models.ReservationRequests;
import models.UserTable;
import models.finder.BoatFinder;
import models.view.BoatTableViewAdapter;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;
import play.twirl.api.Html;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
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


    String logged = "\uD83C\uDFC3\u200D♂\uFE0F";

    String unlogged = "\uD83C\uDFC3\u200D♂\uFE0F";


    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) {
        getLogged(request);
        return ok(renderIndexView(request, getNewReservationForm(), logged, false)).addingToSession(request, "test-cookie", "egal");
    }
    public Result submitReservation(Http.Request request) throws FileNotFoundException {
        Form<ReservationForm> submitForm = formFactory
                .form(ReservationForm.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);
        BoatTable boatTable = new BoatTable();
        BoatTableViewAdapter boatTableViewAdapter = new BoatTableViewAdapter(boatTable);
        if(submitForm.hasErrors()){
            Http.Headers headers = request.getHeaders();
            Optional<String> optReferer = headers.get("Referer");
            if (optReferer.isPresent()) {
                String value = optReferer.get();
                String shortenedValue = value.substring(value.lastIndexOf("/"));
                getLogged(request);
                switch (shortenedValue) {
                    case "/drinks":
                        return badRequest(views.html.drinks.render(submitForm, logged, request, messages.preferred(request), true));
                    case "/aboutUs":
                        return badRequest(views.html.aboutUs.render(submitForm, logged, request, messages.preferred(request), true));
                    case "/manage":
                        return badRequest(views.html.manage.render(getBoatTableViewAdapter(), getBoatManagementForm(), submitForm, logged, request, messages.preferred(request), true));
                    default:
                        return badRequest(renderIndexView(request, submitForm, logged, true));
                }

            } else {
                return badRequest(renderIndexView(request, submitForm, logged, true));
            }
        }
        ReservationForm reservationForm = submitForm.get();
        ReservationRequests reservationRequests = new ReservationRequests();
        reservationRequests.timeFrom = reservationForm.timeFromLocalTime;
        reservationRequests.timeTo = reservationForm.timeToLocalTime;
        reservationRequests.reservationDate = reservationForm.dateLocalDate;
        reservationRequests.boatFKID = null;
        reservationRequests.save();
        getLogged(request);
        return ok(views.html.showReservation.render(reservationForm, submitForm, logged, request, messages.preferred(request), true));
    }

    public Result submitBoatManagement(Http.Request request) throws FileNotFoundException{
        Form<BoatManagement> submitBoatManagement = formFactory
                .form(BoatManagement.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);
        BoatTable boatTable = new BoatTable();
        BoatTableViewAdapter boatTableViewAdapter = new BoatTableViewAdapter(boatTable);
        getLogged(request);
        if (submitBoatManagement.hasErrors()){
            return badRequest(views.html.manage.render(getBoatTableViewAdapter(), submitBoatManagement, getNewReservationForm(), logged, request, messages.preferred(request), true));
        }
        BoatManagement boatManagement = submitBoatManagement.get();
        BoatManagementTable boatManagementTable = new BoatManagementTable();

        boatManagementTable.typeOfBoat = boatManagement.typeOfBoat;

        if(boatManagement.numberOfHorsePower != null) {
            boatManagementTable.horsePower = boatManagement.numberOfHorsePower;
        }
        if(boatManagement.numberOfSeats != null) {
            boatManagementTable.numberOfSeats = boatManagement.numberOfSeats;
        }
        return ok(views.html.manage.render(getBoatTableViewAdapter(), submitBoatManagement, getNewReservationForm(), logged, request, messages.preferred(request), false));
    }

    public Result submitLogin(Http.Request request) throws FileNotFoundException{
        Form<Login> loginForm = formFactory
                .form(Login.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);
        BoatTable boatTable = new BoatTable();
        BoatTableViewAdapter boatTableViewAdapter = new BoatTableViewAdapter(boatTable);
        if (loginForm.hasErrors()){
            return badRequest(views.html.login.render(loginForm, request, messages.preferred(request)));
        }
        Login loginData = loginForm.get();
        UserTable userTable = new UserTable();
        userTable.firstName = loginData.firstName;
        userTable.lastName = loginData.lastName;
        userTable.email = loginData.email;
        userTable.number = loginData.phone;
        userTable.save();
        System.out.println(request.session().get("desiredSite"));
        String value = String.valueOf(request.session().get("desiredSite"));
        String shortenValue = value.substring(value.lastIndexOf("/"));

        switch (shortenValue) {
            case "/drinks]":
                return ok(views.html.drinks.render(getNewReservationForm(), unlogged, request, messages.preferred(request), false))
                        .addingToSession(request, "logged", "hallo" );
            case "/aboutUs]":
                return ok(views.html.aboutUs.render(getNewReservationForm(), unlogged, request, messages.preferred(request), false))
                        .addingToSession(request, "logged", "hallo" );
            case "/manage]":
                return ok(views.html.manage.render(getBoatTableViewAdapter(), getBoatManagementForm(), getNewReservationForm(), logged, request, messages.preferred(request), false))
                        .addingToSession(request, "logged", "hallo" );
            case "/manage/configuration]":
                Form<Boat> boat = formFactory.form(Boat.class);
                return  ok(views.html.addingNewBoat.render(boat, getNewReservationForm(),unlogged, request, messages.preferred(request), false)).addingToSession(request, "logged", "hallo");
            default:
                return ok(views.html.loginConfirmed.render(loginData, getNewReservationForm(), unlogged, request, messages.preferred(request), false))
                        .addingToSession(request, "logged", "hallo" );
        }

    }
    @With(LoginAction.class)
    public Result submitBoatConfiguration(Http.Request request){
        Form<Boat> boat = formFactory.form(Boat.class).withDirectFieldAccess(true)
                .bindFromRequest(request);
        if (boat.hasErrors()){
            return badRequest(views.html.addingNewBoat.render(boat, getNewReservationForm(), logged, request, messages.preferred(request), false));
        }
        Boat submitBoat = boat.get();
        BoatTable boatTable = new BoatTable();

        boatTable.model = submitBoat.getModel();
        boatTable.vehicleLicensePlate = submitBoat.getKfz();
        boatTable.save();
        return ok(views.html.manage.render(getBoatTableViewAdapter(), getBoatManagementForm(), getNewReservationForm(), logged, request, messages.preferred(request), false));
    }
    public Result showReservation(Http.Request request){
        Form<ReservationForm> reservationForm = formFactory.form(ReservationForm.class);
        return ok(renderIndexView(request, reservationForm, logged, true));
    }

    @With(LoginAction.class)
    public Result drinks(Http.Request request){
        Form<ReservationForm> submitForm = formFactory
                .form(ReservationForm.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);
        getLogged(request);

        return ok(views.html.drinks.render( getNewReservationForm(), logged, request, messages.preferred(request), true));
    }

    @With(LoginAction.class)
    public Result aboutUs(Http.Request request){
        getLogged(request);
        return ok(views.html.aboutUs.render( getNewReservationForm(), logged, request, messages.preferred(request), false));
    }

    @With(LoginAction.class)
    public Result manage(Http.Request request){
        Form<BoatManagement> boatManagementForm = formFactory.form(BoatManagement.class);
        boatManagementForm.withDirectFieldAccess(true);
        getLogged(request);
        return ok(views.html.manage.render(getBoatTableViewAdapter(), boatManagementForm, getNewReservationForm(), logged, request, messages.preferred(request), true));
    }

    public Result login(Http.Request request){
        Form<Login> loginForm = formFactory.form(Login.class);
        loginForm.withDirectFieldAccess(true);
        System.out.println(request.session().get("desiredSite"));
        return ok(views.html.login.render(loginForm, request, messages.preferred(request)));
    }
    public Result logOut(Http.Request request) {
        Optional session = request.session().get("logged");
        if (session.isPresent()) {
            return redirect(routes.HomeController.index()).removingFromSession(request, "logged");
        } else {
            return redirect(routes.HomeController.login());
        }
    }

    @With(LoginAction.class)
    public Result addingBoat(Http.Request request){
        Form<Boat> boat = formFactory.form(Boat.class);
        return  ok(views.html.addingNewBoat.render(boat, getNewReservationForm(), logged, request, messages.preferred(request), false));
    }

    //helper methods
    private Html renderIndexView(Http.Request request, Form<ReservationForm> form, String logged, boolean showReservation){
        return views.html.index.render(form, logged, request, messages.preferred(request), showReservation);
    }
    private Form<ReservationForm> getNewReservationForm(){
        Form<ReservationForm> reservationForm = formFactory.form(ReservationForm.class);
        reservationForm.withDirectFieldAccess(true);
        return reservationForm;
    }
    private Form<BoatManagement> getBoatManagementForm(){
        Form<BoatManagement> boatManagementForm = formFactory.form(BoatManagement.class);
        boatManagementForm.withDirectFieldAccess(true);
        return boatManagementForm;
    }
    private List<BoatTableViewAdapter> getBoatTableViewAdapter(){
        BoatFinder boatFinder = new BoatFinder();

        List<BoatTableViewAdapter> boatTableViewAdapterList = new ArrayList<>();

        List<BoatTable> boatList = boatFinder.findBoats();
        for(int i = 0 ; i < boatList.size(); i++){
            BoatTableViewAdapter boatTableViewAdapter = new BoatTableViewAdapter(boatList.get(i));
            boatTableViewAdapterList.add(boatTableViewAdapter);
        }
        return boatTableViewAdapterList;
    }
    private String getLogged(Http.Request request){
        Optional session = request.session().get("logged");
        if (session.isPresent()){
            logged = "\uD83C\uDFC3\u200D♂\uFE0F";
        } else {
            logged = "\uD83D\uDE36\u200D\uD83C\uDF2B\uFE0F";
        }
        return logged;
    }
}