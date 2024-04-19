package models;

import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserTable extends Model {

    @Id
    @Column(name ="id")
    public int userID;

    @Column(name = "firstname")
    public String firstName;

    @Column(name = "lastname")
    public String lastName;

    @Column(name = "email")
    public String email;

    @Column(name = "phone")
    public String number;

    public UserTable(String firstName) {
        this.firstName = firstName;
    }

    public UserTable() {

    }

}
