package com.zs.demo.communication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.zs.demo.communication.service.BindService;

public class BindServiceActivity extends AppCompatActivity {

    private BindService bindService;
    private ServiceConnection serviceConnection;
    private ProgressBar mProgressBar;
    private boolean mBound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mProgressBar = (ProgressBar) findViewById(R.id.main_progress);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBound = false;
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
                mBound = true;
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
        unBindService();
    }

    public void unBindService(){
        if (mBound){
            unbindService(serviceConnection);
            mBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindService();
    }
}
