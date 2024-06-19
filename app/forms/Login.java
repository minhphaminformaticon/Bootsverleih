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
public class Login implements Constraints.Validatable<List<ValidationError>>{

    public String firstName;
    public String lastName;
    public String phone;
    public String email;


    private Boolean isEmailValid;
    private Boolean isNumberValid;
    private Boolean isFirstNameValid;
    private Boolean isLastNameValid;

    public UserViewAdapter userViewAdapter;

    @Override
    public List<ValidationError> validate() {

        UserFinder userFinder = new UserFinder();
        List<UserTable> userTables = userFinder.findUsers();


        List<ValidationError> validationErrors = new ArrayList<>();
        Pattern mailPattern = Pattern.compile("\\S+@\\S+(\\.ch|\\.com)");
        Matcher matcher = mailPattern.matcher(email);
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
        if (email == null || Objects.equals(email, "")){
            validationErrors.add(new ValidationError("email", "you need to enter your email"));
        } else if (!matcher.matches()) {
            validationErrors.add(new ValidationError("email", "Email isn't valid"));
        }
        if (phone == null || Objects.equals(phone, "")){
            validationErrors.add(new ValidationError("phone", "you need to enter your number"));
        } else if (!phone.matches("^\\+\\d{11}$")) {
            validationErrors.add(new ValidationError("phone", "The number isn't valid"));
        }

        if (userTables.isEmpty()){
            validationErrors.add(new ValidationError("firstName", "There's no User, Please Sign up"));
        } else {
            for (int i = 0; i < userTables.size(); i++){
                userViewAdapter = new UserViewAdapter(userTables.get(i));
                isFirstNameValid = false;
                isLastNameValid = false;
                isEmailValid = false;
                isNumberValid = false;

                if (firstName != null) {
                    if (lastName != null) {
                            if (email != null) {
                                if (phone != null) {
                                    if (!firstName.equals(userViewAdapter.firstName)) {
                                        isFirstNameValid = false;
                                    }

                                    if (!lastName.equals(userViewAdapter.lastName)) {
                                        isFirstNameValid = false;
                                    }

                                    if (!email.equals(userViewAdapter.email)) {
                                        isFirstNameValid = false;
                                    }

                                    if (!phone.equals(userViewAdapter.number)) {
                                        isFirstNameValid = false;
                                    }
                                    if (firstName.equals(userViewAdapter.firstName)) {
                                        isFirstNameValid = true;
                                    }
                                    if (lastName.equals(userViewAdapter.lastName)) {
                                        isLastNameValid = true;
                                    }
                                    if (email.equals(userViewAdapter.email)) {
                                        isEmailValid = true;
                                    }
                                    if (phone.equals(userViewAdapter.number)) {
                                        isNumberValid = true;
                                    }
                                    if (isFirstNameValid && isLastNameValid && isEmailValid && isNumberValid) {
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }else {
                                break;
                            }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!isFirstNameValid){
                validationErrors.add(new ValidationError("firstName", "The first name is incorrect."));
            }
            if (!isLastNameValid){
                validationErrors.add(new ValidationError("lastName", "The last name is incorrect"));
            }
            if (!isEmailValid){
                validationErrors.add(new ValidationError("email", "The email is incorrect."));
            }
            if (!isNumberValid){
                validationErrors.add(new ValidationError("phone", "The number is incorrect"));
            }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

