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
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import static app.wytalk.MainActivity.dataOutputStream;

public class ChatActivity extends AppCompatActivity {

    ListChatting listChatting; //채팅방 목록
    Data dataClass; //정보 모음
    ChatThread chatThread;

    int check;
    int chattingnum; //채팅방 넘버
    String chatin; //채팅 기록

    ListView m_ListView;
    CustomAdapter m_Adapter;

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

        System.out.println("test1");

        listChatting = new ListChatting();
        dataClass = new Data();
        myid = dataClass.userVector.elementAt(0).id;


        System.out.println("test2");
        // 커스텀 어댑터 생성
        m_Adapter = new CustomAdapter();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);


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


        System.out.println("test3");
/****************** 기존 채팅방 or 새로운 채팅방 확인 ******************/

        System.out.println("============id"+id);

        Iterator<String> iterator = listChatting.userToChatNum.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next(); // 키 얻기
            System.out.println("key="+key+" / value="+listChatting.userToChatNum.get(key));  // 출력
        }

        /****************** 기존 채팅방 ******************/
        if (listChatting.hasIdChatRoom(id)) { //기존 채팅방


            System.out.println("test4");

            chattingnum = listChatting.getChatNum(id); //채팅방 넘버
            System.out.println("채팅방 넘버 = " + chattingnum);

            chatin = dataClass.chatDatahash.get(chattingnum).chatIn; //채팅목록 가져오기


            //화면에 이전 메시지 출력
            if (chatin != null) {
                chatwrite(chatin);
            }
        }
        /******************새로운 채팅방******************/
        else { //새로운 채팅방

            System.out.println("test5");
            chattingnum = 0; //채팅방 넘버 = 0으로 임의 지정
        }


        System.out.println("test6");
        chatThread = new ChatThread();
        chatThread.start();


        System.out.println("test7");

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

/****************** 전송버튼 클릭 시 ******************/
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (editText.getText().equals("") == false) {
                    System.out.println("check--------");
                    if (listChatting.hasIdChatRoom(id)) { //기존 채팅방
                        chattingnum = listChatting.getChatNum(id); //채팅방 넘버
                    }

                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    //yyyy/MM/dd HH:mm:ss
                    String getTime = sdf.format(date); //현재 시간
                    output = "[MSG]::" + chattingnum + "::" + id + "::" + myid + "::" + getTime + "::" + editText.getText().toString();

                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        System.out.println("test-send 1 chattingnum = " + chattingnum);

                        System.out.println("test-send" + output);
                        dataOutputStream.writeUTF(output);
                        // dataOutputStream.write(output.getBytes());
                        dataOutputStream.flush();

                        editText.setText("");


                        //chatwrite(dataClass.chatDatahash.get(chattingnum).lastMsg);
                        System.out.println("test-send 2 chattingnum = " + chattingnum);

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

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


    private void refresh(String inputValue,int _str){
        m_Adapter.add(inputValue,_str);
        m_Adapter.notifyDataSetChanged();


       // m_ListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
       // m_ListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        //m_ListView.smoothScrollToPosition(m_ListView.getLastVisiblePosition());
        //m_ListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    public void chatwrite(String input) {

        chatin = input.trim(); //앞뒤공백제거
        String[] data = chatin.split("@@");

        for (String s : data) {   //@@처리
            System.out.println(s);


            if (s != null) {
                s = s.trim(); //앞뒤공백제거
                String[] data2 = s.split("::");   //::처리

                for (String s2 : data2) {   //data2 =  [MSG] 채팅번호 수신자 송신자 시간 메시지
                    System.out.println(s2);
                }

                if (data2[5] != null) {

                    System.out.println("수신자 id " + data2[2] + " 친구id " + id);
                    if (data2[3].equals(id)) { //송신자 = 친구
                        refresh(data2[5],0);
                        //m_Adapter.add(data2[5], 0);
                    } else { //송신자 = 나
                        refresh(data2[5],1);
                        //m_Adapter.add(data2[5], 1);
                    }


                }


            }
        }
    }

    class ChatThread extends Thread {

        public void run() {
            while (true) {


                try {


                    if (listChatting.hasIdChatRoom(id)) { //기존 채팅방, 채팅방 넘버 있을 때

                        chattingnum = listChatting.getChatNum(id);
                        if(dataClass.chatDatahash.get(chattingnum).check==1){
                            dataClass.chatDatahash.get(chattingnum).check = 0;
                            System.out.println("마지막 내용"+dataClass.chatDatahash.get(chattingnum).lastMsg);
                            chatwrite(dataClass.chatDatahash.get(chattingnum).lastMsg);
                            System.out.println("출력반복");
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }



/*                    try{
                        if (dataClass.chatDatahash.get(id).check == 1) {
                            chatwrite(dataClass.chatDatahash.get(id).lastMsg);
                        }
                    }catch (NullPointerException e){
                        //e.printStackTrace();
                    }*/





            }
        } //run 끝


    } //스레드 끝


}

