package com.zs.demo.communication.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zs
 * Date：2018年 09月 19日
 * Time：9:33
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class MyIntentService extends IntentService{

    private final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("My_Log", bundle.getString("key", "默认值"));
            }
        }

    }

}
