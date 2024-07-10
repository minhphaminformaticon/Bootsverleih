package models;

import io.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserTable extends Model {

    @Id
    @Column(name ="id")
    public Integer userID;

    @Column(name = "firstname")
    public String firstName;

    @Column(name = "lastname")
    public String lastName;

    @Column(name = "email")
    public String email;

    @Column(name = "passwort")
    public String password;

    public UserTable(String firstName) {
        this.firstName = firstName;
    }

    public UserTable() {

    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
