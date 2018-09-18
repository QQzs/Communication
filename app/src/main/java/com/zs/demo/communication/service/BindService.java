package com.zs.demo.communication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by zhangshuai on 16/7/14.
 */
public class BindService extends Service {

    private MsgBinder mBinder;
    /**
     * 进度条的最大值
     */
    public static final int MAX_PROGRESS = 100;
    /**
     * 进度条的进度值
     */
    private int progress = 0;

    /**
     * 增加get()方法，供Activity调用
     * @return 下载进度
     */
    public int getProgress() {
        return progress;
    }

    private ProgressListener mListener;

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
                    if (mListener != null){
                        mListener.setProgress(progress);
                    }
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
    public void onCreate() {
        mBinder = new MsgBinder();
        super.onCreate();
    }

    /**
     * 返回一个Binder对象
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Binder 扩展
     */
    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         * @return
         */
        public BindService getService(){
            return BindService.this;
        }
    }

    public interface ProgressListener{
        void setProgress(int progress);
    }

    public void setProgressListener(ProgressListener listener){
        mListener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.interrupt();
    }
}
