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

}
