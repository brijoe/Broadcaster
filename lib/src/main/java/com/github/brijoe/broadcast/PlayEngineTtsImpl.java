package com.github.brijoe.broadcast;

import android.content.Context;

/**
 * 文字转语音引擎
 */
class PlayEngineTtsImpl extends PlayEngineBase<String> {


    PlayTtsHelper playTtsHelper = PlayTtsHelper.INS;

    private static PlayEngineTtsImpl instance = new PlayEngineTtsImpl();

    private PlayEngineTtsImpl() {

    }

    public static PlayEngineTtsImpl get() {
        return instance;
    }

    @Override
    public void init(Context context) {
        playTtsHelper.init(context);
    }

    @Override
    protected void playInternal(PlayData<String> playData, PlayEngineCallback listener) {
        playTtsHelper.play(playData.getData(), listener);
    }

    @Override
    public boolean isPlaying() {
        return playTtsHelper.isPlaying();
    }

    @Override
    public void stopInternal() {
        playTtsHelper.stop();
    }

    @Override
    public void release() {
        playTtsHelper.release();
    }

}
