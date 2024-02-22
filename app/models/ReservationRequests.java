package models;

import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservationsrequests")
public class ReservationRequests extends Model {

    @Id
    @Column(name = "customer_ID")
    public int id;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "email")
    public String email;

    @Column(name = "number")
    public String number;

    @Column(name = "time_from")
    public LocalTime timeFrom;

    @Column(name = "time_to")
    public LocalTime timeTo;

    @Column(name = "reservation_date")
    public LocalDate reservationDate;

    @Column(name = "boat_FKID")
    public int boatFKID;
}


