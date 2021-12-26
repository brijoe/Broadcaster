package com.github.brijoe.broadcast;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 播报任务执行器
 */
class PlayExecutor extends HandlerThread {


    private Map<DataType, PlayEngineBase> engineMap = new ConcurrentHashMap<>();

    private final int WHAT_INTERRUPT = 0x01;

    private ExecutorHandler executorHandler;

    private BroadcastService service;

    private volatile PlayTask curTask = null;

    private class ExecutorHandler extends Handler {
        public ExecutorHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case WHAT_INTERRUPT:
                    stopCurPlayInternal();
                    break;

            }
        }
    }

    private static final List<PlayEngineBase> engines = new CopyOnWriteArrayList<>();

    static {
        engines.add(PlayEngineTtsImpl.get());
        engines.add(PlayEngineMediaImpl.get());
    }

    public PlayExecutor(BroadcastService service) {
        super("PlayExecutor");
        this.service = service;
        engineMap.put(DataType.DATA_TYPE_TXT, engines.get(0));
        engineMap.put(DataType.DATA_TYPE_FILE, engines.get(1));
        engineMap.put(DataType.DATA_TYPE_ASSERTS_FILE, engines.get(1));
    }

    public void start(Context context) {
        for (PlayEngineBase engine : engines) {
            engine.init(context);
        }
        super.start();
        executorHandler = new ExecutorHandler(getLooper());
    }


    // 运行在 Executor 线程  stop 会导致play 方法返回
    public void stopCurPlay() {
        executorHandler.obtainMessage(WHAT_INTERRUPT).sendToTarget();
    }

    //返回是否正在播放
    public boolean isPlaying() {
        for (PlayEngineBase engine : engineMap.values()) {
            if (engine.isPlaying()) {
                return true;
            }
        }
        return false;
    }

    private void stopCurPlayInternal() {
        if (curTask != null) {
            for (PlayData playData : curTask.getDataList()) {
                DevLog.d("PlayExecutor", "开始中断任务 " + playData);
                engineMap.get(playData.getDataType()).stopEnginePlay();
            }
        }
    }

    /**
     * 阻塞式调用
     *
     * @param playTask
     */

    //运行在 Dispatcher 线程 在执行完成 返回
    public void play(@NonNull PlayTask playTask) {
        curTask = playTask;
        List<PlayData> dataList = playTask.getDataList();
        BroadcastPlayListener listener = playTask.getListener();
        //调用开始
        handleCallback(listener, new PlayResult(PlayStatus.PLAY_START));
        PlayResult result = null;
        for (PlayData playData : dataList) {
            DevLog.d("PlayExecutor", "开始播放任务 " + playData);
            result = engineMap.get(playData.getDataType()).playWithEngine(playData);
            //如果遇到中断或者播放错误 停止播放任务退出
            if (((result.getCode() & PlayStatus.PLAY_INTERRUPT) != 0)
                    || ((result.getCode() & PlayStatus.PLAY_ERROR) != 0)) {
                break;
            }
        }
        handleCallback(listener, result);
    }

    private void handleCallback(BroadcastPlayListener listener,
                                PlayResult playResult) {
        if (listener == null || playResult == null) {
            return;
        }
        service.playCallbackExecutor.execute(() -> {
            switch (playResult.getCode()) {
                case PlayStatus.PLAY_START:
                    listener.onPlayStart();
                    break;
                case PlayStatus.PLAY_ERROR:
                    listener.onPlayError(playResult.getMsg());
                    break;
                case PlayStatus.PLAY_INTERRUPT:
                    listener.onPlayInterrupt();
                    break;
                case PlayStatus.PLAY_SUCCESS:
                    listener.onPlaySuccess();
                    break;
            }
        });


    }

}


