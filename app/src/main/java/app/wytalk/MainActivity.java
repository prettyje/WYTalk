package app.wytalk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static app.wytalk.MyService.dataOutputStream;

public class MainActivity extends AppCompatActivity {


    Button button1;

    EditText idText; //id
    EditText pwText; //pw

/*    static Socket socket = null;
    static DataOutputStream dataOutputStream = null;
    static DataInputStream dataInputStream = null;
    static OutputStream outputStream = null;
    static InputStream inputStream= null;*/




/*    //static String host = "192.168.0.39"; //102
    static String host = "192.168.0.30"; //랩실
    static int port = 30015;*/

    //FirstConnectThread firstthread;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE
            }, 466);
        }//인터넷 허가

/*******************서비스 시작***********************/
        Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this,MyService.class);
        startService(intent);


        button1 = (Button) findViewById(R.id.button1);
        idText = (EditText)findViewById(R.id.editText1);
        pwText = (EditText)findViewById(R.id.editText2);


        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();


            }
        });



    }


    public void login(){

        String output_id;
        String output_pw;
        String output = null; //서버로 보낸 데이터
        // String input = null; //서버로부터 받은 데이터

        output_id = idText.getText().toString();
        output_pw = pwText.getText().toString();
        output = "[LOGIN]:"+output_id+"/"+output_pw;


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            dataOutputStream.writeUTF(output);
            dataOutputStream.flush();

            System.out.println("send-" + output);



        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


    }


/*    class FirstConnectThread extends Thread {

        String output_id;
        String output_pw;
        String output = null; //서버로 보낸 데이터
        String input = null; //서버로부터 받은 데이터
       // String data; //서버로부터 받은 데이터

       // String input; //서버로부터 받은 데이터

        public void run() {

            try {
                System.out.println("start");



                socket = new Socket(host, port);


                System.out.println("서버로 연결되었습니다. : " + host + ", " + port);

                output_id = idText.getText().toString();
                output_pw = pwText.getText().toString();

                output = "[LOGIN]:"+output_id+"/"+output_pw;

                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();


                dataOutputStream = new DataOutputStream(outputStream);

                dataOutputStream.writeUTF(output);
                dataOutputStream.flush();

                System.out.println("send-" + output);



                dataInputStream = new DataInputStream(inputStream);
                input = dataInputStream.readUTF();
                System.out.println("receive-" + input);


                if (input.toString().equals("[LOGIN]:OK")) { //Login 성공시

                    System.out.println("test ok");


                    Intent intent = new Intent(getApplicationContext(),MainViewActivity.class);
                    startActivity(intent);

                }else{
                    System.out.println("test nok");
                    //Toast.makeText(MainActivity.this,"잘못된 정보입니다.",Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("접근실패");
                //Toast.makeText(MainActivity.this,"접근실패",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }*/

}
