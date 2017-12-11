package app.wytalk;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by SJE on 2017-11-22.
 */

public class Data {

    public static Vector<User> userVector;
    public static HashMap<Integer, ChatData> chatDatahash;


    public Data() {

        if (userVector == null)
            userVector = new Vector<User>();

        if (chatDatahash == null)
            chatDatahash = new HashMap<Integer, ChatData>();

    }

    public void initUserInfo(String id, String name, String stateMsg) {

        User user = new User(id, name, stateMsg);
        userVector.add(user); //0:사용자,1~: 친구
    }

    public void initchatData(Integer chatNum) {

        ChatData chatData = new ChatData(chatNum);
        chatDatahash.put(chatNum, chatData);

    }

    public void addInchat(Integer chatNum, String chatIn) {

        chatDatahash.get(chatNum).chatIn += (chatIn + "@@");
        chatDatahash.get(chatNum).lastMsg = chatIn;

        chatDatahash.get(chatNum).check = 1;
        System.out.println("===========확인");
        System.out.println(chatDatahash.get(chatNum).chatNum);
        System.out.println(chatDatahash.get(chatNum).chatIn);
        System.out.println(chatDatahash.get(chatNum).check);

        System.out.println("===========" + chatNum + "번 방 대화내용 저장소");

        System.out.println(chatDatahash.get(chatNum).chatIn);
        System.out.println("===========" + chatDatahash.get(chatNum).lastMsg + "last 메시지");
        //asdfadf@@asdfasdf@@asdfadsf@@
    }

}

