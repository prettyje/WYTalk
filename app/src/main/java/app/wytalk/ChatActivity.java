package app.wytalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ChatActivity extends AppCompatActivity {

    int check;
    String name;
    FrameLayout frameLayout;
    LinearLayout itemLinear;
    LinearLayout imoticonLinear;

    ImageButton button1;
    ImageButton button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        frameLayout = (FrameLayout)findViewById(R.id.layout);
        itemLinear = (LinearLayout)findViewById(R.id.item_linear);
        imoticonLinear = (LinearLayout)findViewById(R.id.imoticon_linear);

        frameLayout.setVisibility(View.GONE);
        check = 0; //basic


        button1 = (ImageButton)findViewById(R.id.imageButton1);
        button2 = (ImageButton)findViewById(R.id.imageButton2);


        Intent intent = getIntent();
        name =  intent.getStringExtra("User_Name");//"이름"문자 받아옴
        setTitle(name);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check==0){ //basic
                    check=1;
                    frameLayout.setVisibility(View.VISIBLE);
                    itemLinear.bringToFront();
                }else if(check ==1) {
                    check =0;
                    frameLayout.setVisibility(View.GONE);
                }else if(check==2){
                    check=1;
                    itemLinear.bringToFront();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check==0){
                    check=2;
                    frameLayout.setVisibility(View.VISIBLE);
                    imoticonLinear.bringToFront();
                }else if(check==1){
                    check=2;
                    imoticonLinear.bringToFront();
                }else if(check==2){
                    check =0;
                    frameLayout.setVisibility(View.GONE);
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        if(check==0){ //basic
            super.onBackPressed();
        }else{
            frameLayout.setVisibility(View.GONE);
            check=0;
        }

    }

}
