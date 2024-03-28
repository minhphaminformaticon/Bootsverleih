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
    @Column(name = "id")
    public int id;

    @Column(name = "timefrom")
    public LocalTime timeFrom;

    @Column(name = "timeto")
    public LocalTime timeTo;

    @Column(name = "reservationdate")
    public LocalDate reservationDate;

    @Column(name = "fk_boat_id")
    public Integer boatFKID;

    @Column(name = "fk_customer_id")
    public Integer customerFKID;
}


