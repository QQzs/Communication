package com.zs.demo.communication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by zs
 * Date：2018年 09月 18日
 * Time：16:47
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view){
        Intent intent = new Intent(this,StartServiceActivity.class);
        startActivity(intent);
    }

    public void bindService(View view){
        Intent intent = new Intent(this,BindServiceActivity.class);
        startActivity(intent);
    }

    public void intentService(View view){
        Intent intent = new Intent(this,IntentServiceActivity.class);
        startActivity(intent);
    }

}
