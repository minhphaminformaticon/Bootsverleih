package models.view;
import models.ReservationRequests;
import models.finder.ReservationFinder;

import java.time.LocalTime;
import java.time.LocalDate;
public class ReservationsViewAdapter {

    public int id;
    public LocalTime timeFrom;
    public LocalTime timeTo;
    public LocalDate reservationDate;
    public int boatID;

    public ReservationsViewAdapter(ReservationRequests reservationRequests){
        this.id = reservationRequests.getId();
        this.timeFrom = reservationRequests.getTimeFrom();
        this.timeTo = reservationRequests.getTimeTo();
        this.reservationDate = reservationRequests.getReservationDate();
        this.boatID = reservationRequests.boatTable.getBoatID();

    }
}
