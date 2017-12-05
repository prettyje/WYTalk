package app.wytalk;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import static app.wytalk.MainActivity.dataOutputStream;

public class ChatActivity extends AppCompatActivity {


    ListChatting listChatting; //채팅방 목록
    Data dataClass; //정보 모음

    int check;
    int chattingnum; //채팅방 넘버


    String name; //친구이름
    String id; //친구 아이디
    String myid; //나의 아이디
    String output; //메시지 형식

    FrameLayout frameLayout;
    LinearLayout itemLinear;
    LinearLayout imoticonLinear;

    ImageButton button1;  //파일 전송 버튼
    ImageButton button2;  //이모티콘 버튼
    Button sendButton;  //send버튼

    EditText editText; // 텍스트 창


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listChatting = new ListChatting();
        dataClass = new Data();
        myid = dataClass.userVector.elementAt(0).id;



        frameLayout = (FrameLayout) findViewById(R.id.layout);
        itemLinear = (LinearLayout) findViewById(R.id.item_linear);
        imoticonLinear = (LinearLayout) findViewById(R.id.imoticon_linear);

        frameLayout.setVisibility(View.GONE);
        check = 0; //basic


        button1 = (ImageButton) findViewById(R.id.imageButton1);
        button2 = (ImageButton) findViewById(R.id.imageButton2);
        sendButton = (Button) findViewById(R.id.sendbutton);
        editText = (EditText) findViewById(R.id.editText);


        Intent intent = getIntent();
        name = intent.getStringExtra("User_Name");//"이름"문자 받아옴
        setTitle(name);
        id = intent.getStringExtra("User_ID"); //id 문자 받아옴

/****************** 기존 채팅방 or 새로운 채팅방 확인 ******************/
        if (listChatting.hasChatRoom(id)) { //기존 채팅방
            chattingnum = listChatting.getChatNum(id); //채팅방 넘버

            //화면에 이전 메시지 출력

        } else { //새로운 채팅방
            chattingnum = 0; //채팅방 넘버 = 0으로 임의 지정
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 0) { //basic
                    check = 1;
                    frameLayout.setVisibility(View.VISIBLE);
                    itemLinear.bringToFront();
                } else if (check == 1) {
                    check = 0;
                    frameLayout.setVisibility(View.GONE);
                } else if (check == 2) {
                    check = 1;
                    itemLinear.bringToFront();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 0) {
                    check = 2;
                    frameLayout.setVisibility(View.VISIBLE);
                    imoticonLinear.bringToFront();
                } else if (check == 1) {
                    check = 2;
                    imoticonLinear.bringToFront();
                } else if (check == 2) {
                    check = 0;
                    frameLayout.setVisibility(View.GONE);
                }

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                //yyyy/MM/dd HH:mm:ss
                String getTime = sdf.format(date); //현재 시간
                output = "[MSG]::"+chattingnum+"::"+myid+"::"+id+"::"+getTime+"::"+editText.getText().toString();

                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    dataOutputStream.writeUTF(output);
                    // dataOutputStream.write(output.getBytes());
                    dataOutputStream.flush();
                    editText.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        if (check == 0) { //basic
            super.onBackPressed();
        } else {
            frameLayout.setVisibility(View.GONE);
            check = 0;
        }

    }

}
