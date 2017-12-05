package app.wytalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class Main_Chat_ProfileActivity extends AppCompatActivity {

    ImageView imageView;
    TextView nTextView;
    TextView cTextView;
    TextView bTextView;
    ImageButton imageButton;

    String check;
    String image;
    String name;
    String chat;
    String id;

    ImageLoader imageLoader = ImageLoader.getInstance();
    ImageLoaderConfiguration config = null;
    DisplayImageOptions options = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__chat__profile);

       // imageLoader.displayImage(list.get(position).url, imageView, options); //이미지 처리

        setImageLoader(options, config, getBaseContext());

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageView = (ImageView) findViewById(R.id.profile_image); //프로필 사진
        nTextView = (TextView) findViewById(R.id.name_textView); //이름
        cTextView = (TextView) findViewById(R.id.chat_textView); //상태 메시지
        bTextView = (TextView) findViewById(R.id.but_textView);


        Intent intent = getIntent(); //이 액티비티를 부른 인텐트를 받는다.

        check = intent.getStringExtra("Check");

        if(check.equals("myP")){ //frP, myP
            imageButton.setImageResource(R.drawable.icon2);
            bTextView.setText("프로필 관리");
        }

        image = intent.getStringExtra("User_Profile");
        name =  intent.getStringExtra("User_Name");//"이름"문자 받아옴
        chat = intent.getStringExtra("User_Chat");//"상메"문자 받아옴
        id = intent.getStringExtra("User_ID"); //id 문자 받아옴



        nTextView.setText(name);
        cTextView.setText(chat);

        imageLoader.displayImage(image,imageView);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.equals("myP")){ //myP

                }
                else{//frP

                    Intent intent2 = new Intent(getApplicationContext(),ChatActivity.class);
                    intent2.putExtra("User_Name",name); //이름 전달
                    intent2.putExtra("User_ID",id); //id 전달

                    startActivity(intent2);
                }  //else끝
            }
        });
    }


    public void setImageLoader(DisplayImageOptions options,
                               ImageLoaderConfiguration config, Context context) { //이미지 처리
        options = new DisplayImageOptions.Builder()
                .considerExifParams(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .writeDebugLogs().build();

        options = new DisplayImageOptions.Builder().considerExifParams(true).cacheInMemory(true)
                .build();
        ImageLoader.getInstance().init(config);
    }

}
