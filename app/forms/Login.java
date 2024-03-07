package forms;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Constraints.Validate
public class Login implements Constraints.Validatable<List<ValidationError>>{

    public String firstName;
    public String lastName;
    public String phone;
    public String email;
    @Override
    public List<ValidationError> validate() {

        List<ValidationError> validationErrors = new ArrayList<>();
        if (!firstName.matches("^[A-Za-zäöüÄÖÜ][a-zöäü]{3,20}")) {
            validationErrors.add(new ValidationError("firstName", "the first name isn't valid"));
        }
        if (!lastName.matches("^[A-Za-zäöüÄÖÜ][a-zöäü]{3,20}")) {
            validationErrors.add(new ValidationError("lastName", "the last name isn't valid"));
        }
        Pattern mailPattern = Pattern.compile("\\S+@\\S+(\\.ch|\\.com)");
        Matcher matcher = mailPattern.matcher(email);
        if (!matcher.matches()) {
            validationErrors.add(new ValidationError("email", "Email isn't valid"));
        }
        if (!phone.matches("^\\+\\d{11}$")) {
            validationErrors.add(new ValidationError("phone", "The number isn't valid"));
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

