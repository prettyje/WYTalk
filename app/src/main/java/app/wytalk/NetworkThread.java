package app.wytalk;

import static app.wytalk.MainActivity.dataInputStream;

/**
 * Created by SJE on 2017-12-03.
 */

public class NetworkThread extends Thread {


    ListChatting listChatting = new ListChatting();
    Data dataClass = new Data();
    String input = null;


    public void run() {

        while (true) {
            try {


                System.out.println("Thread Start");
                input = dataInputStream.readUTF();


                if (input != null) {

                    System.out.println("receive--" + input);

                    input = input.trim(); //앞뒤공백제거
                    String[] data = input.split("::");

                    for (String s : data) {
                        System.out.println(s);
                    }

/******************* 프로토콜에 따라 처리하기 *******************/


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

                    /*******************[MSG]  메시지 수신 *******************/
                    else if (data[0].equals("[MSG]")) {

                        System.out.println("msg수신");

                        //[MSG] 방번호 수신자 송신자 시간 메시지

                        System.out.println("방 번호========"+data[1]);


                        /*******************방번호 없으면 (첫 대화시)*******************/
                        if (listChatting.hasNumChatRoom(Integer.parseInt(data[1])) == false) {

                            // 0    1       2       3     4     5
                            //[MSG] 방번호 수신자 송신자 시간 메시지


                            //수신자 = 본인 , 내가 처음 받았을 때
                            if(data[2].equals(dataClass.userVector.elementAt(0).id)){

                                System.out.println("ok1");
                                listChatting.init(data[3], Integer.parseInt(data[1])); //방 추가
                                System.out.println("ok2");
                                dataClass.initchatData(Integer.parseInt(data[1])); //내용저장 가능하게추가
                                System.out.println("ok3");
                                dataClass.addInchat(Integer.parseInt(data[1]), input); //내용 추가

                            }else{//수신자 = 친구, 내가 보냈을 때

                                System.out.println("ok1");
                                listChatting.init(data[2], Integer.parseInt(data[1])); //방 추가
                                System.out.println("ok2");
                                dataClass.initchatData(Integer.parseInt(data[1])); //내용저장 가능하게추가
                                System.out.println("ok3");
                                dataClass.addInchat(Integer.parseInt(data[1]), input); //내용 추가
                            }




                        }
                        else{
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
