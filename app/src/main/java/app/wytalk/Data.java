package app.wytalk;

import java.util.Vector;

/**
 * Created by SJE on 2017-11-22.
 */

public class Data{

    public static Vector<User> userVector;


    public Data(){

        if(userVector == null)
            userVector = new Vector<User>();
    }

    public void initUserInfo(String id, String name, String stateMsg){

        User user = new User(id,name,stateMsg);
        userVector.add(user); //0:사용자,1~: 친구

    }


}

