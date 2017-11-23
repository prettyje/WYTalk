package app.wytalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ChatActivity extends AppCompatActivity {

    String name;
    LinearLayout iconLayout;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        iconLayout = (LinearLayout)findViewById(R.id.layout);
        iconLayout.setVisibility(View.GONE);

        button = (ImageButton)findViewById(R.id.imageButton1);

        Intent intent = getIntent();
        name =  intent.getStringExtra("User_Name");//"이름"문자 받아옴
        setTitle(name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
