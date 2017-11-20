package app.wytalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by SJE on 2017-11-09.
 */

public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstancdState){
        super.onCreate(savedInstancdState);

        try{
            Thread.sleep(600);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,MainActivity.class));
        //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();

    }
}
