package controllers;

import actions.AdminAction;
import actions.LoginAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import forms.*;
import models.*;
import models.finder.BoatFinder;
import models.finder.ReservationFinder;
import models.view.BoatTableViewAdapter;
import models.view.ReservationsViewAdapter;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;
import play.routing.JavaScriptReverseRouter;
import play.twirl.api.Html;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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
        if (submitForm.hasErrors()) {
            Http.Headers headers = request.getHeaders();
            Optional<String> optReferer = headers.get("Referer");
            if (optReferer.isPresent()) {
                String value = optReferer.get();
                String shortenedValue = value.substring(value.lastIndexOf("/"));
                getLogged(request);
                switch (shortenedValue) {
                    case "/drinks":
                        return badRequest(views.html.drinks.render(submitForm, logged, getBoatTableViewAdapter(), request, messages.preferred(request), true));
                    case "/aboutUs":
                        return badRequest(views.html.aboutUs.render(submitForm, logged, getBoatTableViewAdapter(), request, messages.preferred(request), true));
                    case "/manage":
                        return badRequest(views.html.manage.render(getBoatTableViewAdapter(), getBoatManagementForm(), submitForm, logged, request, messages.preferred(request), true));
                    default:
                        return badRequest(renderIndexView(request, submitForm, logged, true));
                }

            } else {
                return badRequest(renderIndexView(request, submitForm, getLogged(request), true));
            }
        }
        ReservationForm reservationForm = submitForm.get();
        ReservationRequests reservationRequests = new ReservationRequests();
        reservationRequests.timeFrom = reservationForm.timeFromLocalTime;
        reservationRequests.timeTo = reservationForm.timeToLocalTime;
        reservationRequests.reservationDate = reservationForm.dateLocalDate;
        reservationRequests.boatTable.boatID = reservationForm.getBoatID();
        reservationRequests.save();
        return ok(views.html.showSubmittedReservation.render(reservationForm, submitForm, getLogged(request), getBoatTableViewAdapter(), request, messages.preferred(request), true));
    }



    public Result submitBoatManagement(Http.Request request) throws FileNotFoundException {
        Form<BoatManagement> submitBoatManagement = formFactory
                .form(BoatManagement.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);
        BoatTable boatTable = new BoatTable();
        BoatTableViewAdapter boatTableViewAdapter = new BoatTableViewAdapter(boatTable);
        getLogged(request);
        if (submitBoatManagement.hasErrors()) {
            return badRequest(views.html.manage.render(getBoatTableViewAdapter(), submitBoatManagement, getNewReservationForm(), logged, request, messages.preferred(request), true));
        }
        BoatManagement boatManagement = submitBoatManagement.get();
        BoatManagementTable boatManagementTable = new BoatManagementTable();

        boatManagementTable.typeOfBoat = boatManagement.typeOfBoat;

        if (boatManagement.numberOfHorsePower != null) {
            boatManagementTable.horsePower = boatManagement.numberOfHorsePower;
        }
        if (boatManagement.numberOfSeats != null) {
            boatManagementTable.numberOfSeats = boatManagement.numberOfSeats;
        }
        return ok(views.html.manage.render(getBoatTableViewAdapter(), submitBoatManagement, getNewReservationForm(), logged, request, messages.preferred(request), false));
    }
    public Result submitAdmin(Http.Request request){
        Form<AdminForm> adminFormForm = formFactory
                .form(AdminForm.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);

        AdminTable adminTable = new AdminTable();
        if (adminFormForm.hasErrors()){
            return badRequest(views.html.adminLogin.render(adminFormForm, request, messages.preferred(request)));
        }

        return ok(views.html.employeeView.render(request, messages.preferred(request))).addingToSession(request, "admin", "admin");
    }
    public Result submitSignUp(Http.Request request){
        Form<SignUp> signUpForm = formFactory
                .form(SignUp.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);

        if (signUpForm.hasErrors()){
            return badRequest(views.html.signup.render(signUpForm, request, messages.preferred(request)));
        }

        try{
            SignUp signUp = signUpForm.get();
            UserTable userTable = new UserTable();
            userTable.firstName = signUp.firstName;
            userTable.lastName = signUp.lastName;
            userTable.email = signUp.email;
            userTable.password = toHexString(getSHA(signUp.password));
            userTable.save();
        }catch(NoSuchAlgorithmException e){
            return badRequest(views.html.signup.render(signUpForm, request, messages.preferred(request)));
        }




        return ok(views.html.signUpComplete.render(getNewReservationForm(), "🔐", getBoatTableViewAdapter(), request, messages.preferred(request), false));
    }
    public Result submitLogin(Http.Request request) throws FileNotFoundException {
        Form<Login> loginForm = formFactory
                .form(Login.class)
                .withDirectFieldAccess(true)
                .bindFromRequest(request);
        BoatTable boatTable = new BoatTable();
        BoatTableViewAdapter boatTableViewAdapter = new BoatTableViewAdapter(boatTable);
        if (loginForm.hasErrors()) {
            return badRequest(views.html.login.render(loginForm, request, messages.preferred(request)));
        }

        System.out.println(request.session().get("desiredSite"));
        String value = String.valueOf(request.session().get("desiredSite"));
        String shortenValue;
        try {
             shortenValue = value.substring(value.lastIndexOf("/"));
        } catch (StringIndexOutOfBoundsException e){
            shortenValue = "";
        }
        switch (shortenValue) {
            case "/drinks]":
                return ok(views.html.drinks.render(getNewReservationForm(), "🔐", getBoatTableViewAdapter(), request, messages.preferred(request), false))
                        .addingToSession(request, "logged", "hallo");
            case "/aboutUs]":
                return ok(views.html.aboutUs.render(getNewReservationForm(), "🔐", getBoatTableViewAdapter(), request, messages.preferred(request), false))
                        .addingToSession(request, "logged", "hallo");
            case "/manage]":
                return ok(views.html.manage.render(getBoatTableViewAdapter(), getBoatManagementForm(), getNewReservationForm(), "🔐", request, messages.preferred(request), false))
                        .addingToSession(request, "logged", "hallo");
            case "/manage/configuration]":
                Form<Boat> boat = formFactory.form(Boat.class);
                return ok(views.html.addingNewBoat.render(boat, getNewReservationForm(), "🔐", getBoatTableViewAdapter(), request, messages.preferred(request), false)).addingToSession(request, "logged", "hallo");
            default:
                return ok(views.html.loginConfirmed.render( getNewReservationForm(), "🔐", getBoatTableViewAdapter(), request, messages.preferred(request), false))
                        .addingToSession(request, "logged", "hallo");
        }

    }

    @With(LoginAction.class)
    public Result submitBoatConfiguration(Http.Request request) {
        Form<Boat> boat = formFactory.form(Boat.class).withDirectFieldAccess(true)
                .bindFromRequest(request);
        if (boat.hasErrors()) {
            return badRequest(views.html.addingNewBoat.render(boat, getNewReservationForm(), getLogged(request), getBoatTableViewAdapter(), request, messages.preferred(request), false));
        }
        Boat submitBoat = boat.get();
        BoatTable boatTable = new BoatTable();

        boatTable.model = submitBoat.getModel();
        boatTable.vehicleLicensePlate = submitBoat.getKfz();
        boatTable.save();
        return ok(views.html.manage.render(getBoatTableViewAdapter(), getBoatManagementForm(), getNewReservationForm(), getLogged(request), request, messages.preferred(request), false));
    }

    public Result showReservation(Http.Request request) {
        Form<ReservationForm> reservationForm = formFactory.form(ReservationForm.class);
        return ok(renderIndexView(request, reservationForm, getLogged(request), true));
    }

    public Result signup(Http.Request request) {
        Form<SignUp> signUpForm = formFactory.form(SignUp.class);
        return ok(views.html.signup.render(signUpForm, request, messages.preferred(request)));
    }
    public Result signUpComplete(Http.Request request) {
        Form<SignUp> signUpForm = formFactory.form(SignUp.class);
        return ok(views.html.signUpComplete.render(getNewReservationForm(), getLogged(request), getBoatTableViewAdapter(), request, messages.preferred(request), false));
    }
    @With(LoginAction.class)
    public Result drinks(Http.Request request) {

        return ok(views.html.drinks.render(getNewReservationForm(), getLogged(request), getBoatTableViewAdapter(), request, messages.preferred(request), false));
    }

    @With(LoginAction.class)
    public Result coffee(Http.Request request){
        return ok(views.html.coffee.render(getNewReservationForm(), getBoatTableViewAdapter(), getLogged(request), request, messages.preferred(request), false));
    }

    @With(LoginAction.class)
    public Result aboutUs(Http.Request request) {
        getLogged(request);
        return ok(views.html.aboutUs.render(getNewReservationForm(), getLogged(request), getBoatTableViewAdapter(), request, messages.preferred(request), false));
    }

    @With(LoginAction.class)
    public Result manage(Http.Request request) {
        Form<BoatManagement> boatManagementForm = formFactory.form(BoatManagement.class);
        boatManagementForm.withDirectFieldAccess(true);
        getLogged(request);
        return ok(views.html.manage.render(getBoatTableViewAdapter(), boatManagementForm, getNewReservationForm(), getLogged(request), request, messages.preferred(request), false));
    }

    @With(AdminAction.class)
    public Result admin(Http.Request request) {
        return ok(views.html.employeeView.render(request, messages.preferred(request)));
    }

    public Result adminLogin(Http.Request request){
        Form<AdminForm> adminFormForm = formFactory.form(AdminForm.class);
        return ok(views.html.adminLogin.render(adminFormForm, request, messages.preferred(request)));
    }

    public Result login(Http.Request request) {
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
    public Result addingBoat(Http.Request request) {
        Form<Boat> boat = formFactory.form(Boat.class);
        return ok(views.html.addingNewBoat.render(boat, getNewReservationForm(), getLogged(request), getBoatTableViewAdapter(), request, messages.preferred(request), false));
    }

    @With(LoginAction.class)
    public Result pong(Http.Request request){
        return ok(views.html.pong.render(request, messages.preferred(request)));
    }

    @With(LoginAction.class)
    public Result reservation(Http.Request request){
        return ok(views.html.displayReservation.render(getNewReservationForm(), getBoatTableViewAdapter(), getLogged(request), request, messages.preferred(request), false));
    }


    public Result javascriptRoutes(Http.Request request) {
        return ok(JavaScriptReverseRouter.create(
                        "javascriptRoutes",
                        "xhr",
                        request.host(),
                        routes.javascript.HomeController.boatDelete()

                )
        ).as(Http.MimeTypes.JAVASCRIPT);
    }

    public Result javaScriptRoutesDisplayReservationsOnCalendar(Http.Request request){
        return ok(JavaScriptReverseRouter.create(
                "javaScriptRoutesDisplayReservationsOnCalendar",
                "xhr",
                request.host(),
                routes.javascript.HomeController.displayReservationsOnCalendar()
        )
        ).as(Http.MimeTypes.JAVASCRIPT);
    }

    public Result javaScriptRoutesAdminCalendar(Http.Request request){
        return ok(JavaScriptReverseRouter.create(
                "javaScriptRoutesAdminCalendar",
                "xhr",
                request.host(),
                routes.javascript.HomeController.adminCheckReservations()

            )
        ).as(Http.MimeTypes.JAVASCRIPT);
    }

    public Result displayReservationsOnCalendar(Http.Request request){
        String jsonReservations = "";

        try {
              jsonReservations = new ObjectMapper().writeValueAsString(ReservationRequests.FINDER.findReservations());
        } catch (JsonProcessingException jpe){
            System.out.println(jpe);
        }
        return ok(jsonReservations);
    }

    public Result adminCheckReservations(Http.Request request){
        String jsonReservations = "";

        try {
            jsonReservations = new ObjectMapper().writeValueAsString(ReservationRequests.FINDER.findCurrentReservations());
        } catch (JsonProcessingException jpe){
            System.out.println(jpe);
        }
        return ok(jsonReservations);
    }

    public Result boatDelete(String id, Http.Request request) {
        String jsonBoat = "";
        try {
        BoatTable boatTable;
        boatTable = BoatTable.FINDER.getKfz(id);
        if (boatTable == null) {
            return badRequest();
        } else {
            boatTable.delete();
            boatTable.save();
        }
            jsonBoat = new ObjectMapper().writeValueAsString(boatTable);
        } catch ( JsonProcessingException jpe) {
            System.out.println(jpe);
        }
        return ok(jsonBoat);
    }

    //helper methods
    private Html renderIndexView(Http.Request request, Form<ReservationForm> form, String logged, boolean showReservation) {
        return views.html.index.render(form, logged, getBoatTableViewAdapter(), request, messages.preferred(request), showReservation);
    }

    private Form<ReservationForm> getNewReservationForm() {
        Form<ReservationForm> reservationForm = formFactory.form(ReservationForm.class);
        reservationForm.withDirectFieldAccess(true);
        return reservationForm;
    }

    private Form<BoatManagement> getBoatManagementForm() {
        Form<BoatManagement> boatManagementForm = formFactory.form(BoatManagement.class);
        boatManagementForm.withDirectFieldAccess(true);
        return boatManagementForm;
    }

    private List<BoatTableViewAdapter> getBoatTableViewAdapter() {
        BoatFinder boatFinder = new BoatFinder();

        List<BoatTableViewAdapter> boatTableViewAdapterList = new ArrayList<>();

        List<BoatTable> boatList = boatFinder.findBoats();
        for (int i = 0; i < boatList.size(); i++) {
            BoatTableViewAdapter boatTableViewAdapter = new BoatTableViewAdapter(boatList.get(i));
            boatTableViewAdapterList.add(boatTableViewAdapter);
        }
        return boatTableViewAdapterList;
    }

    private List<ReservationsViewAdapter> getReservationViewAdapter(){
        ReservationFinder finder = new ReservationFinder();

        List<ReservationsViewAdapter> viewAdapter = new ArrayList<>();

        List<ReservationRequests> r = finder.findReservations();
        for (int i = 0; i < r.size(); i++){
            ReservationsViewAdapter reservationsViewAdapter = new ReservationsViewAdapter(r.get(i));
            viewAdapter.add(reservationsViewAdapter);
        }
        return viewAdapter;
    }


    private String getLogged(Http.Request request) {
        Optional session = request.session().get("logged");
        if (session.isPresent()) {
            logged = "🔐";
        } else {
            logged = "\uD83D\uDD13";
        }
        return logged;
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash){

        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while(hexString.length()  < 64){
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

}