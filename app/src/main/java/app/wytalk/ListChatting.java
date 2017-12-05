package app.wytalk;

import java.util.HashMap;

/**
 * Created by SJE on 2017-12-04.
 */

public class ListChatting {

   // private Vector<Integer> chatNumVec;
    static HashMap<String, Integer> userToChatNum;  //친구, 방번호

    public ListChatting() {
       // chatNumVec = new Vector<Integer>();
        userToChatNum = new HashMap<String, Integer>();
    }

    public boolean hasChatRoom(String userID) {
        return userToChatNum.containsKey(userID);
    }

    public Integer getChatNum(String userID) {
        return userToChatNum.get(userID);
    }

}
