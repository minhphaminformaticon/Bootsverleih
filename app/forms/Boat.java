package forms;


import akka.pattern.package$;
import models.BoatTable;
import models.finder.BoatFinder;
import models.view.BoatTableViewAdapter;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Constraints.Validate
public class Boat implements Constraints.Validatable<List<ValidationError>> {

    public String model;
    public String kfz;

    BoatTableViewAdapter boatTableViewAdapter;

    @Override
    public List<ValidationError> validate() {
        BoatFinder boatFinder = new BoatFinder();

        List<BoatTable> boatList = boatFinder.findBoats();


        List<ValidationError> validationErrors = new ArrayList<>();
            if (model == null || Objects.equals(model, "")){
                validationErrors.add(new ValidationError("model", "you need to enter the boat's model."));
            }
            if (kfz == null || Objects.equals(kfz, "")){
                validationErrors.add(new ValidationError("kfz", "you need to enter the boat's license plate"));
            } else if (!kfz.matches("^[A-Za-zäöüÄÖÜ]{2}\\d{6}")){
                validationErrors.add(new ValidationError("kfz", "invalid vehicle license plate"));
            }
            for (int i = 0; i < boatList.size(); i++){
                boatTableViewAdapter = new BoatTableViewAdapter(boatList.get(i));
                if (Objects.equals(kfz, boatTableViewAdapter.vehicleLicensePlate)){
                    validationErrors.add(new ValidationError("kfz", "The vehicle license plate is already placed."));
                }
            }

        return validationErrors;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getKfz() {
        return kfz;
    }

    public void setKfz(String kfz) {
        this.kfz = kfz;
    }
}
