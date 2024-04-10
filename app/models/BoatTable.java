package models;
import akka.actor.typed.javadsl.Adapter;
import io.ebean.Model;
import models.view.BoatTableViewAdapter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
}
