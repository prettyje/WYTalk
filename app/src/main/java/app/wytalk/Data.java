package app.wytalk;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by SJE on 2017-11-22.
 */

public class Data {

    public static Vector<User> userVector;
    public static HashMap<Integer, ChatData> chatDatahash;
    public static HashMap<String,Bitmap> imgdatahash;


    public Data() {

        if (userVector == null)
            userVector = new Vector<User>();

        if (chatDatahash == null)
            chatDatahash = new HashMap<Integer, ChatData>();


        if (imgdatahash == null)
            imgdatahash = new HashMap<String,Bitmap>();

    }

    public void initUserInfo(String id, String name, String stateMsg) {

        User user = new User(id, name, stateMsg);
        userVector.add(user); //0:사용자,1~: 친구
    }

    public void initchatData(Integer chatNum) {// 채팅 목록 추가

        ChatData chatData = new ChatData(chatNum);
        chatDatahash.put(chatNum, chatData);

    }

    public void addInimg(String id, Bitmap img){

        imgdatahash.put(id,img);

    }

    public void addInchat(Integer chatNum, String chatIn) { //채팅 내용 추가

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

    public void addInchatimg(Integer chatNum, String chatIn, Bitmap bitmap) { //채팅 내용 추가



        String s;
        s = chatIn.trim(); //앞뒤공백제거
        String[] data = s.split("::");

        System.out.println("*****"+data[5]);
        data[5] = "++bitmap";
        System.out.println("*****"+data[5]);

        chatIn = data[0]+"::"+data[1]+"::"+data[2]+"::"+data[3]+"::"+data[4]+"::"+data[5];

        chatDatahash.get(chatNum).chatIn += (chatIn + "@@");
        chatDatahash.get(chatNum).lastMsg = chatIn;
        chatDatahash.get(chatNum).bitmapVector.add(bitmap);


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

