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
public class ChangePassword implements Constraints.Validatable<List<ValidationError>>{

    private String email;
    private String password;

    private String passwordAgain;
    private UserViewAdapter userViewAdapter;

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = new ArrayList<>();
        UserFinder userFinder = new UserFinder();
        List<UserTable> userTables = userFinder.findUsers();

        if (password == null){
            validationErrors.add(new ValidationError("password", "you need to change the password"));
        } else {
            if (passwordAgain == null){
                validationErrors.add(new ValidationError("passwordAgain", "you need to reenter your password"));
            } else {
                if (Objects.equals(password, "")) {
                    validationErrors.add(new ValidationError("password", "you need to change the password"));
                }
                if (Objects.equals(passwordAgain, "")) {
                    validationErrors.add(new ValidationError("passwordAgain", "you need to reenter your password"));
                }
                if (!Objects.equals(password, passwordAgain)){
                    validationErrors.add(new ValidationError("password", "The passwords don't match with eachother"));
                }
            }
        }



        return validationErrors;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public UserViewAdapter getUserViewAdapter() {
        return userViewAdapter;
    }

    public void setUserViewAdapter(UserViewAdapter userViewAdapter) {
        this.userViewAdapter = userViewAdapter;
    }
}
