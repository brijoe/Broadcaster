package com.github.brijoe.broadcast;

import android.content.Context;

class BroadcastService {

    PlayDispatcher playDispatcher = new PlayDispatcher(this);
    PlayExecutor playExecutor = new PlayExecutor(this);
    PlayCallbackExecutor playCallbackExecutor =new PlayCallbackExecutor();

    public void init(Context context) {
        playExecutor.start(context);
        playDispatcher.start();
    }

    public void play(PlayTask playTask) {
        playDispatcher.addPlayTask(playTask);
    }


    public boolean isPlaying() {
        return playExecutor.isPlaying();
    }

}
