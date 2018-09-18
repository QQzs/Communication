package com.zs.demo.communication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.zs.demo.communication.service.StartService;

public class StartActivity extends AppCompatActivity {

    private Intent serviceIntent;
    private MsgReceiver msgReceiver;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mProgressBar = (ProgressBar) findViewById(R.id.main_progress);

        // 动态注册广播接收器
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zs.communication.RECEIVER");
        registerReceiver(msgReceiver, intentFilter);

        serviceIntent = new Intent(this, StartService.class);

    }

    /**
     * staryService方式
     * @param view
     */
    public void start(View view){
        startService(serviceIntent);
    }

    /**
     * 取消服务
     * @param view
     */
    public void cancel(View view){
        stopService(serviceIntent);
    }


    class MsgReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //拿到进度，更新UI
            int progress = intent.getIntExtra("progress", 0);
            mProgressBar.setProgress(progress);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }
}
