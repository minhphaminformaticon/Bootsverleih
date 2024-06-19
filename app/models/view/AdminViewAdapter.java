package models.view;

import models.AdminTable;

public class AdminViewAdapter {
    public int id;

    public String username;

    public String password;


    public AdminViewAdapter(AdminTable adminTable){
        this.id = adminTable.getAdminID();
        this.username = adminTable.getUsername();
        this.password = adminTable.getPassword();
    }
}
