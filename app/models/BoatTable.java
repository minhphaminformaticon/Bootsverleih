package models;
import akka.actor.typed.javadsl.Adapter;
import io.ebean.Model;
import models.finder.BoatFinder;
import models.view.BoatTableViewAdapter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "boat")
public class BoatTable extends Model{
    @Id
    @Column(name ="id")
    public int boatID;
    @Column(name = "model")
    public String model;
    @Column(name = "vehiclelicenseplate")
    public String vehicleLicensePlate;
    @OneToMany(mappedBy = "boatTable")
    List<ReservationRequests> reservationRequests;
    public int getBoatID() {
        return boatID;
    }

    public void setBoatID(int boatID) {
        this.boatID = boatID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    public void setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
    }

    public  static final BoatFinder FINDER = new BoatFinder();
}
