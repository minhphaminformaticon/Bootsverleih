package models.view;

import models.BoatTable;

public class BoatTableViewAdapter {

    public String model;
    public String vehicleLicensePlate;

    public BoatTableViewAdapter(BoatTable boatTable){
        this.model = boatTable.getModel();
        this.vehicleLicensePlate = boatTable.getVehicleLicensePlate();
    }
}
