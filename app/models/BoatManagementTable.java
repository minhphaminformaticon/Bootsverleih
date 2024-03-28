package models;

import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "boat_management")
public class BoatManagementTable extends Model {

    @Id
    @Column(name ="boat_ID")
    public int boatID;

    @Column(name = "type_of_boat")
    public String typeOfBoat;

    @Column(name = "number_of_seats")
    public int numberOfSeats;

    @Column(name = "horse_power")
    public int horsePower;
}

