package com.github.brijoe.broadcast.example;

import android.app.Application;


import com.github.brijoe.broadcast.BroadcastManager;

import java.io.IOException;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BroadcastManager.INS.init(this);
        copyResource();
    }

    private void copyResource() {
        new Thread(() -> {
            String dest = getExternalCacheDir().getPath() + "/test.wav";
            try {
                AssetUtils.copy(App.this, "test.wav", dest);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
