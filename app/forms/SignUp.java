package forms;

import models.UserTable;
import models.finder.UserFinder;
import models.view.UserViewAdapter;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Constraints.Validate
public class SignUp implements Constraints.Validatable<List<ValidationError>>{
    public String firstName;
    public String lastName;
    public String password;
    public String email;
    private Boolean isEmailValid;
    private Boolean isNumberValid;
    public UserViewAdapter userViewAdapter;

    @Override
    public List<ValidationError> validate() {

        UserFinder userFinder = new UserFinder();
        List<UserTable> userTables = userFinder.findUsers();

        List<ValidationError> validationErrors = new ArrayList<>();
        if (firstName == null || Objects.equals(firstName, "")) {
            validationErrors.add(new ValidationError("firstName", "you need to enter your first name"));
        } else if (!firstName.matches("^[A-Za-zäöüÄÖÜ][a-zöäü]{3,20}")) {
            validationErrors.add(new ValidationError("firstName", "the first name isn't valid"));
        }
        if (lastName == null || Objects.equals(lastName, "")) {
            validationErrors.add(new ValidationError("lastName", "you need to enter your last name"));
        } else if (!lastName.matches("^[A-Za-zäöüÄÖÜ][a-zöäü]{3,20}")) {
            validationErrors.add(new ValidationError("lastName", "the last name isn't valid"));
        }
        Pattern mailPattern = Pattern.compile("\\S+@\\S+(\\.ch|\\.com)");
        Matcher matcher = mailPattern.matcher(email);
        if (email == null || Objects.equals(email, "")){
            validationErrors.add(new ValidationError("email", "you need to enter your email"));
        } else if (!matcher.matches()) {
            validationErrors.add(new ValidationError("email", "Email isn't valid"));
        }
        if (password == null || Objects.equals(password, "")){
            validationErrors.add(new ValidationError("phone", "you need to enter your number"));
        } else if (!password.matches("^[A-Za-z]{8,}")) {
            validationErrors.add(new ValidationError("phone", "The number isn't valid"));
        }
            isEmailValid = true;
            isNumberValid = true;
            for (int i = 0; i < userTables.size(); i++) {
                userViewAdapter = new UserViewAdapter(userTables.get(i));

                if (email != null) {
                    if (password != null) {
                        if (email.equals(userViewAdapter.email)) {
                            isEmailValid = false;
                        }
                        if (password.equals(userViewAdapter.password)) {
                            isNumberValid = true;
                        }
                        if (isEmailValid && isNumberValid) {
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!isEmailValid) {
                validationErrors.add(new ValidationError("email", "the email is already in use"));
            }
            if (!isNumberValid) {
                validationErrors.add(new ValidationError("phone", "The number is already in use"));
            }
        return validationErrors;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
