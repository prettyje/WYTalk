package app.wytalk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class MyService extends Service {

    NotificationManager Notifi_M;
    ServiceThread thread;
    NetworkThread networkThread;
    Notification Notifi;

    static Socket socket = null;
    static DataOutputStream dataOutputStream = null;
    static DataInputStream dataInputStream = null;
    static OutputStream outputStream = null;
    static InputStream inputStream = null;




/*    IBinder mBinder = new MyBinder();

    class MyBinder extends Binder {
        MyService getService() { // 서비스 객체를 리턴
            return MyService.this;
        }
    }*/



    @Override
    public IBinder onBind(Intent intent) {
        // 액티비티에서 bindService() 를 실행하면 호출됨
        // 리턴한 IBinder 객체는 서비스와 클라이언트 사이의 인터페이스 정의한다
        //return mBinder; // 서비스 객체를 리턴

        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       /* myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();*/
        Log.i("kkkkkkk","start");
        myServiceHandler handler = new myServiceHandler();
        networkThread = new NetworkThread(handler);
        networkThread.start();

        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {
        networkThread.stopForever();
        networkThread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {


            if (msg.what == 0) {

                Intent intent1 = new Intent(getApplicationContext(), MainViewActivity.class);
                PendingIntent pendingIntent1 = PendingIntent.getActivity(MyService.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                //startActivity(intent1);
                try{
                    pendingIntent1.send();
                } catch(Exception e) {
                    //익셉션
                }



            } else {
                Intent intent = new Intent(MyService.this, MainViewActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notifi = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Content Title")
                        .setContentText("톡이 도착했어요~")
                        .setSmallIcon(R.drawable.icon1)
                        .setTicker("알림!!!")
                        .setContentIntent(pendingIntent)
                        .build();

                //소리추가
                //Notifi.defaults = Notification.DEFAULT_SOUND;

                Notifi.defaults |= Notification.DEFAULT_SOUND;

                //알림 소리를 한번만 내도록
                Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;

                //확인하면 자동으로 알림이 제거 되도록
                Notifi.flags = Notification.FLAG_AUTO_CANCEL;


                Notifi_M.notify(777, Notifi);

                //토스트 띄우기
                Toast.makeText(MyService.this, "문자왓숑", Toast.LENGTH_LONG).show();

            }

        }

    }

}
