package com.github.brijoe.broadcast;

import android.content.Context;

/**
 * 采用系统 MediaPlayer 实现的 音频播放器，支持播放 音频文件资源
 */
class PlayEngineMediaImpl extends PlayEngineBase<String> {

    private PlayMediaHelper playMediaHelper = PlayMediaHelper.INS;

    private static PlayEngineMediaImpl instance = new PlayEngineMediaImpl();

    private PlayEngineMediaImpl() {
    }

    public static PlayEngineMediaImpl get() {
        return instance;
    }

    @Override
    public void init(Context context) {
        playMediaHelper.init(context);
    }


    @Override
    protected void playInternal(PlayData<String> playData, PlayEngineCallback playEngineCallback) {
        switch (playData.getDataType()) {
            case DATA_TYPE_ASSERTS_FILE:
                playMediaHelper.playAssetFile(playData.getData(), playEngineCallback);
                break;
            case DATA_TYPE_FILE:
                playMediaHelper.playDiskFile(playData.getData(), playEngineCallback);
                break;
        }
    }

    @Override
    public boolean isPlaying() {
        return playMediaHelper.isPlaying();
    }

    @Override
    public void stopInternal() {
        playMediaHelper.stop();
    }

    @Override
    public void release() {
        playMediaHelper.release();
    }
}
