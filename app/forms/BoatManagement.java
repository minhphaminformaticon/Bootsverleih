package forms;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Constraints.Validate
public class BoatManagement implements Constraints.Validatable<List<ValidationError>> {
    public String typeOfBoat;
    public Integer numberOfSeats;

    public Integer numberOfHorsePower;
    @Override
    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        if(typeOfBoat == null){
            validationErrors.add(new ValidationError("typeOfBoat", "You must select one of the options"));
        }
        if (Objects.equals(typeOfBoat, "Rowing Boat") && (numberOfSeats == null || numberOfSeats.equals(0))){
            validationErrors.add(new ValidationError("numberOfSeats", "You must enter the amount of seats for your rowing boat"));
        }
        if (Objects.equals(typeOfBoat, "Motor Boat") && (numberOfHorsePower == null || numberOfHorsePower.equals(0))){
            validationErrors.add(new ValidationError("numberOfHorsePower", "You must enter the amount of Horsepower for your motor boat"));
        }
        if (Objects.equals(typeOfBoat, "Rowing Boat") && numberOfHorsePower != null){
            validationErrors.add(new ValidationError("numberOfHorsePower", "A rowing boat can't have a motor"));
        }
        if (Objects.equals(typeOfBoat, "Motor Boat") && numberOfSeats != null){
            validationErrors.add(new ValidationError("numberOfSeats", "A motor boat can't have multiple seats"));
        }
        return validationErrors;
    }
}
