package models.view;


import models.UserTable;

public class UserViewAdapter {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public UserViewAdapter(UserTable userTable){
        this.id = userTable.userID;
        this.firstName = userTable.firstName;
        this.lastName = userTable.lastName;
        this.email = userTable.email;
        this.password = userTable.password;
    }
}
