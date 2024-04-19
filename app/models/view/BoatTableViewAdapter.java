package models.view;

import models.BoatTable;

public class BoatTableViewAdapter {

    public int id;

    public String model;
    public String vehicleLicensePlate;

    public BoatTableViewAdapter(BoatTable boatTable){
        this.id = boatTable.getBoatID();
        this.model = boatTable.getModel();
        this.vehicleLicensePlate = boatTable.getVehicleLicensePlate();
    }
}
