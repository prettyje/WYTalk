package app.wytalk;

import android.graphics.Bitmap;

import java.util.Vector;

/**
 * Created by SJE on 2017-12-07.
 */

public class ChatData {

    public Integer chatNum;
    public String chatIn;
    public String lastMsg;
    public int check;
    public Vector<Bitmap> bitmapVector;


    public  ChatData(Integer chatNum){
        this.chatNum = chatNum;
        chatIn = "";
        lastMsg = "";
        check = 0; //0이면 출력할 데이터가 없음
        bitmapVector = new Vector<Bitmap>();
    }

}
