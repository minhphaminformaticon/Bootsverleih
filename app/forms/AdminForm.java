package forms;


import models.AdminTable;
import models.finder.AdminFinder;
import models.view.AdminViewAdapter;
import models.view.BoatTableViewAdapter;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import javax.validation.Constraint;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


@Constraints.Validate
public class AdminForm implements Constraints.Validatable<List<ValidationError>>{
    public String username;
    public String password;

    private boolean isAdminUsernameCorrect;
    private boolean isAdminPasswordCorrect;

    public AdminViewAdapter adminViewAdapter;

    @Override
    public List<ValidationError> validate() {
        AdminFinder adminFinder = new AdminFinder();
        List<AdminTable> adminTables = adminFinder.findAdmins();
        List<ValidationError> validationErrors = new ArrayList<>();


        if(username == null || Objects.equals(username, "")){
            validationErrors.add(new ValidationError("username", "Enter an username"));
        }
        if (password == null || Objects.equals(password, "")){
            validationErrors.add(new ValidationError("password", "Enter a password"));
        }
        if (adminTables.isEmpty()){
            validationErrors.add(new ValidationError("password", "ERROR"));
        } else {
            isAdminUsernameCorrect = false;
            isAdminPasswordCorrect = false;
            for (int i = 0; i < adminTables.size(); i++) {
                adminViewAdapter = new AdminViewAdapter(adminTables.get(i));
                if (!username.equals(adminViewAdapter.username)) {
                    isAdminUsernameCorrect = false;
                }
                if (!password.equals(adminViewAdapter.password)) {
                    isAdminPasswordCorrect = false;
                }
                if (username.equals(adminViewAdapter.username)) {
                    isAdminUsernameCorrect = true;
                }
                if (password.equals(adminViewAdapter.password)) {
                    isAdminPasswordCorrect = true;
                }
                if (username.equals(adminViewAdapter.username) && password.equals(adminViewAdapter.password)) {
                    break;
                }
            }
            if (!isAdminPasswordCorrect) {
                validationErrors.add(new ValidationError("password", "The password isn't incorrect"));
            }
            if (!isAdminUsernameCorrect) {
                validationErrors.add(new ValidationError("username", "The username isn't correct"));
            }
        }


        return validationErrors;
    }
}
