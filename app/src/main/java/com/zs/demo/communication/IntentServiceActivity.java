package com.zs.demo.communication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zs.demo.communication.service.MyIntentService;

/**
 * Created by zs
 * Date：2018年 09月 19日
 * Time：9:20
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class IntentServiceActivity extends AppCompatActivity {

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

    }

    /**
     * staryService方式
     * @param view
     */
    public void start(View view){
        Intent intent = new Intent(this, MyIntentService.class);
        Bundle bundle = new Bundle();
        bundle.putString("key", "当前值：" + i++);
        intent.putExtras(bundle);
        startService(intent);

    }

    /**
     * 取消服务
     * @param view
     */
    public void cancel(View view){

    }

}
