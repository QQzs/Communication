package com.zs.demo.communication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.zs.demo.communication.service.BindService;

public class BindActivity extends AppCompatActivity {

    private BindService bindService;
    private ServiceConnection serviceConnection;
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

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //返回一个MsgService对象
                bindService = ((BindService.MsgBinder)service).getService();
                bindService.setProgressListener(new BindService.ProgressListener() {
                    @Override
                    public void setProgress(int progress) {
                        mProgressBar.setProgress(progress);
                    }
                });


            }
        };

        // 绑定Service 方式
        Intent intent = new Intent(this,BindService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    /**
     * 绑定Service方式
     * @param view
     */
    public void start(View view){
        bindService.startDownLoad();
    }

    /**
     * 取消服务
     * @param view
     */
    public void cancel(View view){
        mProgressBar.setProgress(0);
        unbindService(serviceConnection);
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
        unbindService(serviceConnection);
        super.onDestroy();
    }
}