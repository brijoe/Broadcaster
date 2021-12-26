package com.github.brijoe.broadcast.example;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.brijoe.broadcast.BroadcastManager;
import com.github.brijoe.broadcast.BroadcastPlayListener;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 播放assets 中的文件
     *
     * @param view
     */
    public void playAssetsFile(View view) {
        BroadcastManager.INS.playAssetsFile(true, "test.wav", new BroadcastPlayListener() {
            @Override
            public void onPlayStart() {
                Log.e("BroadcastManager", "onPlayStart");
            }

            @Override
            public void onPlayInterrupt() {
                Log.e("BroadcastManager", "onPlayInterrupt");
            }

            @Override
            public void onPlayError(String errMsg) {
                Log.e("BroadcastManager", "onPlayError");
            }

            @Override
            public void onPlaySuccess() {
                Log.e("BroadcastManager", "onPlaySuccess");
            }
        });
    }

    /**
     * 从本地文件路径中播放
     */
    public void playDiskFile(View view) {
        BroadcastManager.INS.playDiskFile(true, getExternalCacheDir().getPath() + "/test.wav", null);
    }

    /**
     * 播放TTS
     */
    public void playTTS(View view) {
        BroadcastManager.INS.playTTS(true, "这是一段测试文本", null);
    }

    /**
     * 播放 assets 文件 + TTS
     */
    public void playAssetsFileAndTTS(View view) {
        BroadcastManager.INS.playAssetsFileAndTTS(false, "test.wav", "这是一段很长的,很长的,很长的,很长的,很长的,很长的,很长的,很长的测试文本", new BroadcastPlayListener() {
            @Override
            public void onPlayStart() {
                Log.e("BroadcastManager", "onPlayStart");
            }

            @Override
            public void onPlayInterrupt() {
                Log.e("BroadcastManager", "onPlayInterrupt");
            }

            @Override
            public void onPlayError(String errMsg) {
                Log.e("BroadcastManager", "onPlayError");
            }

            @Override
            public void onPlaySuccess() {
                Log.e("BroadcastManager", "onPlaySuccess");
            }
        });
    }

    /**
     * 播报磁盘文件+ TTS
     */
    public void playDiskFileAndTTS(View view) {
        BroadcastManager.INS.playDiskFileAndTTS(false, getExternalCacheDir().getPath() + "/test.wav", "这是一段测试文本", null);
    }

    public void playDiskFileAndDiskFile(View view) {
        BroadcastManager.INS.playDiskFileAndDiskFile(false, getExternalCacheDir().getPath() + "/test.wav", getExternalCacheDir().getPath() + "/test.wav", null);
    }

}