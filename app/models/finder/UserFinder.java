package models.finder;

import io.ebean.Finder;
import models.UserTable;
import java.util.List;

public class UserFinder extends Finder<String, UserTable> {

    public UserFinder(){
        super(UserTable.class);
    }

    public List<UserTable> findUsers(){
        return query().where().findList();
    }

    public UserTable getID(int id){
        return query().where().eq("id", id).findOne();
    }
}
