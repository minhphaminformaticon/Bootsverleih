package forms;

import models.UserTable;
import models.finder.UserFinder;
import models.view.UserViewAdapter;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Constraints.Validate
public class ChangeNames implements Constraints.Validatable<List<ValidationError>> {
    String newFirstName;
    String newLastName;
    public UserViewAdapter userViewAdapter;

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        UserFinder userFinder = new UserFinder();
        List<UserTable> userTables = userFinder.findUsers();

        if (newFirstName == null && newLastName == null) {
            validationErrors.add(new ValidationError("newFirstName", "Need to change a name."));
            validationErrors.add(new ValidationError("newLastName", "Need to change a name"));
        } else {
            if (Objects.equals(newFirstName, "") && Objects.equals(newLastName, "")) {
                validationErrors.add(new ValidationError("newFirstName", "Need to change a name"));
                validationErrors.add(new ValidationError("newLastName", "Need to change a name"));
            }
        }
        return validationErrors;
    }

    public String getNewFirstName() {
        return newFirstName;
    }

    public void setNewFirstName(String newFirstName) {
        this.newFirstName = newFirstName;
    }

    public String getNewLastName() {
        return newLastName;
    }

    public void setNewLastName(String newLastName) {
        this.newLastName = newLastName;
    }

    public UserViewAdapter getUserViewAdapter() {
        return userViewAdapter;
    }

    public void setUserViewAdapter(UserViewAdapter userViewAdapter) {
        this.userViewAdapter = userViewAdapter;
    }
}
