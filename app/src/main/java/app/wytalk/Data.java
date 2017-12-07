package app.wytalk;

import java.util.Vector;

/**
 * Created by SJE on 2017-11-22.
 */

public class Data{

    public static Vector<User> userVector;
    public static Vector<ChatData> chatDataVector;

    public Data(){

        if(userVector == null)
            userVector = new Vector<User>();

        if(chatDataVector == null)
            chatDataVector = new Vector<ChatData>();
    }

    public void initUserInfo(String id, String name, String stateMsg){

        User user = new User(id,name,stateMsg);
        userVector.add(user); //0:사용자,1~: 친구
    }

    public void initchatData(Integer chatNum){

        ChatData chatData = new ChatData(chatNum);
        chatDataVector.add(chatData);
    }

    public void addInchat(Integer chatNum, String chatIn){

        chatDataVector.get(chatNum).chatIn += chatIn+"@@";
        //asdfadf@@asdfasdf@@asdfadsf@@
    }

}

