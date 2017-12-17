package app.wytalk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static app.wytalk.MyService.dataInputStream;
import static app.wytalk.MyService.dataOutputStream;
import static app.wytalk.MyService.inputStream;
import static app.wytalk.MyService.outputStream;
import static app.wytalk.MyService.socket;

/**
 * Created by SJE on 2017-12-03.
 */

public class NetworkThread extends Thread {


/*    static Socket socket = null;
    static DataOutputStream dataOutputStream = null;
    static DataInputStream dataInputStream = null;
    static OutputStream outputStream = null;
    static InputStream inputStream = null;*/


    //static String host = "192.168.0.39"; //102
    static String host = "192.168.0.30"; //랩실
    static int port = 30015;


    ListChatting listChatting = new ListChatting();
    Data dataClass = new Data();
    String input = null;

    Handler handler;
    boolean isRun = true;

    public NetworkThread(Handler handler) {

        this.handler = handler;

    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {


        try {
            socket = new Socket(host, port);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataInputStream = new DataInputStream(inputStream);

            //  inputStream2 = socket.getInputStream();
            //  dataInputStream2 = new DataInputStream(inputStream2);


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("start");


        while (isRun) {
            try {


                //  dataInputStream.
                //System.out.println("Thread Start--");
                input = dataInputStream.readUTF();


                if (input != null) {

                    System.out.println("receive--" + input);

                    input = input.trim(); //앞뒤공백제거
                    String[] data = input.split("::");

                    for (String s : data) {
                        System.out.println(s);
                    }

/******************* 프로토콜에 따라 처리하기 *******************/


                    /*******************[LOGIN] 로그인*******************/
                    if (data[0].equals("[LOGIN]")) {
                        if (data[1].equals("OK")) {//Login 성공시
                            handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄

                            System.out.println("test ok");

                            //MainActivity.login = 1;

                           /* Intent intent = new Intent(getApplicationContext(),MainViewActivity.class);
                            startActivity(intent);*/

                        } else {
                            System.out.println("test nok");
                            //Toast.makeText(MainActivity.this,"잘못된 정보입니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    /*******************[FLIST]  사용자 및 친구 정보 가져오기 *******************/
                    if (data[0].equals("[FLIST]")) {
                        for (int i = 1; i < data.length; i++) {
                            String[] info = data[i].split(":");
                            System.out.println(info[0] + "/" + info[1] + "/" + info[2]);
                            dataClass.initUserInfo(info[0], info[1], info[2]);
                        }

                        for (User user : dataClass.userVector) {
                            System.out.println(user.name);
                        }

                    } else if (data[0].equals("[FLIST_END]")) {
                        // listFriends.setFriendPanel();
                    }
                    /*******************[PI] 프로필 사진 가져오기*******************/
                    else if (data[0].equals("[PI]")) {
                        System.out.println("Profile Icon..!!");
                        int size = Integer.parseInt(data[1]);
                        byte[] bytes = new byte[size];

                        dataInputStream.readFully(bytes, 0, size);
                        String userID = data[2];
                        System.out.println("dis.read(bytes) ok");

                        Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        dataClass.addInimg(userID, image);
                        System.out.println("Success");

                    }
                    /*******************[IMG] 이미지 가져오기*******************/
                    else if (data[0].equals("[IMG]")) {
                        System.out.println("img send.....");
                        int size = Integer.parseInt(data[5]);
                        byte[] bytes = new byte[size];
                        String userID = data[2];

                        dataInputStream.readFully(bytes, 0, size);
                        System.out.println("dis.read(bytes) ok");

                        Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


                        System.out.println("img수신");
                        handler.sendEmptyMessage(1);//쓰레드에 있는 핸들러에게 메세지를 보냄

                        //[MSG] 방번호 수신자 송신자 시간 메시지


                        System.out.println("방 번호========" + data[1]);


                        /*******************방번호 없으면 (첫 대화시)*******************/
                        if (listChatting.hasNumChatRoom(Integer.parseInt(data[1])) == false) {

                            // 0    1       2       3     4     5
                            //[MSG] 방번호 수신자 송신자 시간  바이트


                            //수신자 = 본인 , 내가 처음 받았을 때
                            if (data[2].equals(dataClass.userVector.elementAt(0).id)) {

                                System.out.println("ok1");
                                listChatting.init(data[3], Integer.parseInt(data[1])); //방 추가
                                System.out.println("ok2");
                                dataClass.initchatData(Integer.parseInt(data[1])); //내용저장 가능하게추가
                                System.out.println("ok3");
                                dataClass.addInchatimg(Integer.parseInt(data[1]), input,image); //내용 추가

                            } else {//수신자 = 친구, 내가 보냈을 때

                                System.out.println("ok1");
                                listChatting.init(data[2], Integer.parseInt(data[1])); //방 추가
                                System.out.println("ok2");
                                dataClass.initchatData(Integer.parseInt(data[1])); //내용저장 가능하게추가
                                System.out.println("ok3");
                                dataClass.addInchatimg(Integer.parseInt(data[1]), input,image); //내용 추가
                            }


                        } else {
                            /*******************방번호 있으면(n번째 대화)*******************/


                            System.out.println("ok4");
                            //내용에 저장
                            dataClass.addInchatimg(Integer.parseInt(data[1]), input,image);
                            System.out.println("ok5");
                            // 및 출력
                        }












                        // dataClass.addInimg(userID, image);

                        System.out.println("Success");

                    }
                    /*******************[MSG]  메시지 수신 *******************/
                    else if (data[0].equals("[MSG]")) {

                        System.out.println("msg수신");
                        handler.sendEmptyMessage(1);//쓰레드에 있는 핸들러에게 메세지를 보냄

                        //[MSG] 방번호 수신자 송신자 시간 메시지

                        System.out.println("방 번호========" + data[1]);


                        /*******************방번호 없으면 (첫 대화시)*******************/
                        if (listChatting.hasNumChatRoom(Integer.parseInt(data[1])) == false) {

                            // 0    1       2       3     4     5
                            //[MSG] 방번호 수신자 송신자 시간 메시지


                            //수신자 = 본인 , 내가 처음 받았을 때
                            if (data[2].equals(dataClass.userVector.elementAt(0).id)) {

                                System.out.println("ok1");
                                listChatting.init(data[3], Integer.parseInt(data[1])); //방 추가
                                System.out.println("ok2");
                                dataClass.initchatData(Integer.parseInt(data[1])); //내용저장 가능하게추가
                                System.out.println("ok3");
                                dataClass.addInchat(Integer.parseInt(data[1]), input); //내용 추가

                            } else {//수신자 = 친구, 내가 보냈을 때

                                System.out.println("ok1");
                                listChatting.init(data[2], Integer.parseInt(data[1])); //방 추가
                                System.out.println("ok2");
                                dataClass.initchatData(Integer.parseInt(data[1])); //내용저장 가능하게추가
                                System.out.println("ok3");
                                dataClass.addInchat(Integer.parseInt(data[1]), input); //내용 추가
                            }


                        } else {
                            /*******************방번호 있으면(n번째 대화)*******************/


                            System.out.println("ok4");
                            //내용에 저장
                            dataClass.addInchat(Integer.parseInt(data[1]), input);
                            System.out.println("ok5");
                            // 및 출력
                        }


                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("접근실패");
                return;
            }

        }
    }
}
