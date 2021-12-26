package com.github.brijoe.broadcast;

import android.content.Context;

import java.util.concurrent.CountDownLatch;

abstract class PlayEngineBase<T> {

    private class LatchWithCode implements PlayEngineCallback {

        private CountDownLatch countDownLatch = new CountDownLatch(1);

        private PlayResult result;

        public LatchWithCode() {
        }

        public PlayResult await() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }

        public void countDown(PlayResult playResult) {
            result = playResult;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }

        @Override
        public void onPlayError(String errMsg) {
            countDown(new PlayResult(PlayStatus.PLAY_ERROR, errMsg));

        }

        @Override
        public void onPlayFinish() {
            countDown(new PlayResult(PlayStatus.PLAY_SUCCESS));
        }
    }

    /**
     * 初始化方法 由 引擎自己实现
     *
     * @param context
     */
    public abstract void init(Context context);


    private LatchWithCode latchWithCode;

    /**
     * 播报 音频任务的方法， 阻塞式调用
     *
     * @param playData
     */
    public final PlayResult playWithEngine(PlayData<T> playData) {
        latchWithCode = new LatchWithCode();
        playInternal(playData, latchWithCode);
        return latchWithCode.await();
    }


    /**
     * 引擎需要实现的方法
     *
     * @param playData
     * @param listener
     */
    protected abstract void playInternal(PlayData<T> playData, PlayEngineCallback listener);

    /**
     * 是否正在播放
     *
     * @return true/false
     */
    public abstract boolean isPlaying();


    /**
     * 中断播放任务的实现
     */
    public final void stopEnginePlay() {
        stopInternal();
        if (latchWithCode != null) {
            latchWithCode.countDown(new PlayResult(PlayStatus.PLAY_INTERRUPT));
        }
    }

    /**
     * 停止播放音频任务
     */
    protected abstract void stopInternal();

    /**
     * 释放引擎资源的方法
     */
    public abstract void release();


}
