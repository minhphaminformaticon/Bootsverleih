package models.finder;

import io.ebean.Expr;
import io.ebean.Finder;
import models.ReservationRequests;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;

public class ReservationFinder extends Finder<String, ReservationRequests> {

    private final Date currentDate = new Date(new java.util.Date().getTime());
    public ReservationFinder(){super(ReservationRequests.class);}

    public List<ReservationRequests> findReservations(){return query().where().findList();}

    public List<ReservationRequests> findCurrentReservations(){
        return query().where().add(Expr.ge("reservationdate", currentDate)).findList();
    }

    public ReservationRequests getID(int id){return query().where().eq("id", id).findOne();}

    public ReservationRequests getTimeFrom(LocalTime timeFrom){return query().where().eq("timeFrom", timeFrom).findOne();}

    public ReservationRequests getTimeTo(LocalTime timeTo){return query().where().eq("timeTo", timeTo).findOne();}

    public ReservationRequests getReservationDate(LocalDate reservationDate){return query().where().eq("reservationDate", reservationDate).findOne();}



}
