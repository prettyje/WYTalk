package app.wytalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Button button1;

    private Socket socket;

    final String ip = "192.168.0.28"; //genymotion host
    final int port = 9436; // port number



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);


        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainViewActivity.class);
                startActivity(intent);
            }
        });






    }

}
