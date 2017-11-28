package app.wytalk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Button button1;

    EditText idText; //id
    EditText pwText; //pw

    private Socket socket;
    FirstConnectThread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE
            }, 466);
        }//인터넷 허가



        button1 = (Button) findViewById(R.id.button1);
        idText = (EditText)findViewById(R.id.editText1);
        pwText = (EditText)findViewById(R.id.editText2);


        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("----test1");
                thread = new FirstConnectThread();
                System.out.println("----test2");
                thread.start();
                System.out.println("----test3");

                //Intent intent = new Intent(getApplicationContext(),MainViewActivity.class);
                //startActivity(intent);
            }
        });



    }

    class FirstConnectThread extends Thread {

/*      int port = 80;
        Object input;
        String output_id;
        String output_pw;
        String output_num;
        SharedPreferences id_pref;
        SharedPreferences.Editor id_commit;*/

        String output_id;
        String output_pw;
        String output; //서버로 보낸 데이터
        String input; //서버로부터 받은 데이터

        public void run() {


            String host = "192.168.0.30";
            int port = 30015;

//            memberPref = getSharedPreferences("memberPref", MODE_PRIVATE);
//          final SharedPreferences.Editor memberEditor = memberPref.edit();
            try {
                System.out.println("----testa");
                Socket socket = new Socket(host, port);
                System.out.println("----testb");
                System.out.println("서버로 연결되었습니다. : " + host + ", " + port);

                output_id = idText.getText().toString();
                output_pw = pwText.getText().toString();

                output = "[LOGIN]:"+output_id+"/"+output_pw;

                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.write(output.getBytes());
                dataOutputStream.flush();

                System.out.println("send-" + output);


                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                System.out.println("----test4");

                byte [] b = new byte[1024];
                dataInputStream.read(b);
                input = new String(b);

                System.out.println("receive-" + input);


                System.out.println("----test5");
                if (input.toString().equals("[LOGIN]:OK")) {

                    System.out.println("test  ok");

                    dataInputStream.close();
                    dataOutputStream.close();
                    socket.close();

                    Intent intent = new Intent(getApplicationContext(),MainViewActivity.class);
                    startActivity(intent);

                }else{
                    System.out.println("test  nok");
                    //Toast.makeText(MainActivity.this,"잘못된 정보입니다.",Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("접근실패");
                Toast.makeText(MainActivity.this,"접근실패",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

}
