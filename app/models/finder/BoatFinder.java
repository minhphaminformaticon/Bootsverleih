package models.finder;

import forms.Boat;
import io.ebean.Finder;
import models.BoatTable;
import models.view.BoatTableViewAdapter;

import java.util.List;

public class BoatFinder extends Finder<String, BoatTable> {
    public BoatFinder() {
        super(BoatTable.class);
    }

    public List<BoatTable> findBoats(){
        return query().where().findList();
    }
}
