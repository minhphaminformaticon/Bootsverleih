package forms;

import models.UserTable;
import models.finder.UserFinder;
import models.view.UserViewAdapter;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Constraints.Validate
public class Login implements Constraints.Validatable<List<ValidationError>>{


    public String password;
    public String email;


    private Boolean isEmailValid;
    private Boolean isPasswordValid;

    public UserViewAdapter userViewAdapter;

    @Override
    public List<ValidationError> validate() {

        UserFinder userFinder = new UserFinder();
        List<UserTable> userTables = userFinder.findUsers();


        List<ValidationError> validationErrors = new ArrayList<>();
        Pattern mailPattern = Pattern.compile("\\S+@\\S+(\\.ch|\\.com)");
        Matcher matcher = mailPattern.matcher(email);
        if (email == null || Objects.equals(email, "")){
            validationErrors.add(new ValidationError("email", "you need to enter your email"));
        } else if (!matcher.matches()) {
            validationErrors.add(new ValidationError("email", "Email isn't valid"));
        }
        if (password == null || Objects.equals(password, "")){
            validationErrors.add(new ValidationError("password", "you need to enter your number"));
        } else if (!password.matches("^[A-Za-z]{8,}")) {
            validationErrors.add(new ValidationError("password", "The number isn't valid"));
        }

        if (userTables.isEmpty()){
            validationErrors.add(new ValidationError("password", "There's no User, Please Sign up"));
        } else {
            for (int i = 0; i < userTables.size(); i++){
                userViewAdapter = new UserViewAdapter(userTables.get(i));
                isEmailValid = false;
                isPasswordValid = false;
                            if (email != null) {
                                if (password != null) {
                                    try{
                                        String passwordInHash = toHexString(getSHA(password));
                                        if (email.equals(userViewAdapter.email)) {
                                            isEmailValid = true;
                                        }
                                        if (passwordInHash.equals(userViewAdapter.password)) {
                                            isPasswordValid = true;
                                        }
                                        if (isEmailValid && isPasswordValid) {
                                            break;
                                        }
                                    }catch(NoSuchAlgorithmException e){
                                        validationErrors.add(new ValidationError("password", "ERROR"));
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }else {
                                break;
                            }
            }
            if (!isEmailValid){
                validationErrors.add(new ValidationError("email", "The email is incorrect."));
            }
            if (!isPasswordValid){
                validationErrors.add(new ValidationError("password", "The password is incorrect"));
            }

        }

        return validationErrors;
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

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash){

        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while(hexString.length()  < 64){
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}

