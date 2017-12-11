package app.wytalk;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by SJE on 2017-12-04.
 */

public class ListChatting {

   // private Vector<Integer> chatNumVec;
    public static HashMap<String, Integer> userToChatNum;  //친구, 방번호

    public ListChatting() {
       // chatNumVec = new Vector<Integer>();
        if(userToChatNum == null)
            userToChatNum = new HashMap<String, Integer>();
    }

    public boolean hasIdChatRoom(String userID) {
        return userToChatNum.containsKey(userID);
    }

    public boolean hasNumChatRoom(Integer chatNum) {
        return userToChatNum.containsValue(chatNum);
    }

    public Integer getChatNum(String userID) {
        return userToChatNum.get(userID); //방번호
    }

    public void init(String userID, Integer chatNum ){
        userToChatNum.put(userID,chatNum); //사용자, 방번호
        System.out.println("------사용자, 방번호 목록-------");

        Iterator<String> iterator = userToChatNum.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next(); // 키 얻기
            System.out.println("key="+key+" / value="+userToChatNum.get(key));  // 출력
        }

    }
}
