package app.wytalk;

import static app.wytalk.MainActivity.dataInputStream;

/**
 * Created by SJE on 2017-12-03.
 */

public class NetworkThread extends Thread {


    Data dataClass = new Data();
    String input = null;


    public void run() {

        try {

            System.out.println("Thread Start");
            input = dataInputStream.readUTF();

            System.out.println("receive-" + input);

            if (input != null) {
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

                }
                else if (data[0].equals("[FLIST_END]")) {
                    // listFriends.setFriendPanel();
                }

            /*******************[MSG]  메시지 수신 *******************/
                else if (data[0].equals("[MSG]")) {
                    // listFriends.setFriendPanel();
                }








            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("접근실패");
            return;
        }


    }
}
