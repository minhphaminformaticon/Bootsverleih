package models.finder;

import io.ebean.Finder;
import models.AdminTable;
import java.util.List;

public class AdminFinder extends Finder<String, AdminTable> {

   public AdminFinder(){
       super(AdminTable.class);
   }

   public List<AdminTable> findAdmins(){
       return query().where().findList();
   }

   public AdminTable getUserName(String username){
       return query().where().eq("username", username).findOne();
   }

   public AdminTable getPassword(String password){
        return query().where().eq("password", password).findOne();
    }
}
