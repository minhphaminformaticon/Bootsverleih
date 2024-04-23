package models;

import io.ebean.Model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservationrequests")
public class ReservationRequests extends Model {

    @Id
    @Column(name = "id")
    public int id;

    @Column(name = "timefrom")
    public LocalTime timeFrom;

    @Column(name = "timeto")
    public LocalTime timeTo;

    @Column(name = "reservationdate")
    public LocalDate reservationDate;


    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_boat_id", referencedColumnName = "id")
    public BoatTable boatTable = new BoatTable();



    public void setBoatID(int boatID) {
        this.boatTable.boatID = boatID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public BoatTable getBoatTable() {
        return boatTable;
    }

    public void setBoatTable(BoatTable boatTable) {
        this.boatTable = boatTable;
    }
}
