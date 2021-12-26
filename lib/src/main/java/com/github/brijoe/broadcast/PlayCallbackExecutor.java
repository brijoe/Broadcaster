package com.github.brijoe.broadcast;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * 回调方法调度器
 */
 class PlayCallbackExecutor implements Executor {

    private Handler mEventHandler;

    public PlayCallbackExecutor() {
        Looper looper = Looper.myLooper();
        if (looper != null) {
            mEventHandler = new Handler(looper);
        }
    }

    @Override
    public void execute(Runnable command) {
        if (mEventHandler != null) {
            mEventHandler.post(command);
        }
    }

}
