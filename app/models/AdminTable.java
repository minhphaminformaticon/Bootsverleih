package models;

import models.finder.AdminFinder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class AdminTable {
    @Id
    @Column(name = "id")
    public int adminID;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;


    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static final AdminFinder FINDER = new AdminFinder();
}


