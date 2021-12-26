package com.github.brijoe.broadcast;


interface PlayEngineCallback {

    void onPlayError(String errMsg);

    void onPlayFinish();
}
