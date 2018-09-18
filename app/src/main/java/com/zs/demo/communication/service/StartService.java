package com.zs.demo.communication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by zhangshuai on 16/7/14.
 */
public class StartService extends Service {

    /**
     * 进度条的最大值
     */
    public static final int MAX_PROGRESS = 100;
    /**
     * 进度条的进度值
     */
    private int progress = 0;

    private Intent intent = new Intent("com.zs.communication.RECEIVER");

    /**
     * 增加get()方法，供Activity调用
     * @return 下载进度
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 异步线程
     */
    private Thread mThread;

    /**
     * 模拟下载任务，每秒钟更新一次
     */
    public void startDownLoad(){
        mThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while(progress < MAX_PROGRESS){
                    progress += 5;

                    intent.putExtra("progress",progress);
                    sendBroadcast(intent);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        mThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startDownLoad();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 返回一个Binder对象
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.interrupt();
    }

}
