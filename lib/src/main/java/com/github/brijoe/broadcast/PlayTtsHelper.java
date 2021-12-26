package com.github.brijoe.broadcast;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.HashMap;
import java.util.Locale;

enum PlayTtsHelper {

    INS;
    TextToSpeech mSpeech;

    public void init(Context context) {
        mSpeech = new TextToSpeech(context, new TTSInitListener());
    }

    private class TTSPlayListener extends UtteranceProgressListener {

        private PlayEngineCallback listener;

        public TTSPlayListener(PlayEngineCallback listener) {
            this.listener = listener;
        }


        @Override
        public void onStart(String utteranceId) {
        }

        @Override
        public void onDone(String utteranceId) {
            if (listener != null) {
                listener.onPlayFinish();
            }
        }

        @Override
        public void onError(String utteranceId) {
            if (listener != null) {
                listener.onPlayError("TextToSpeech error");
            }
        }
    }


    private class TTSInitListener implements TextToSpeech.OnInitListener {
        @Override
        public void onInit(int status) {
            if (mSpeech != null && status == TextToSpeech.SUCCESS) {
                int isSupportChinese = mSpeech.isLanguageAvailable(Locale.CHINESE);//是否支持中文
                if (isSupportChinese == TextToSpeech.LANG_AVAILABLE) {
                    mSpeech.setLanguage(Locale.CHINESE);//设置语言
                    mSpeech.setSpeechRate(1.0f);//设置语
                    mSpeech.setPitch(1.0f);//设置音量
                    mSpeech.getDefaultEngine();//默认引擎
                }
            } else {
                //初始化TextToSpeech引擎失败
                DevLog.d("TTsHelper", "TTS 引擎初始化");
            }
        }
    }


    public void play(String tts, PlayEngineCallback listener) {
        long utteranceId = System.currentTimeMillis();
        HashMap<String, String> ttsOptions = new HashMap<>();
        ttsOptions.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                String.valueOf(utteranceId));//utterance，这个参数随便写，用于监听播报完成的回调中
        ttsOptions.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, String.valueOf(1));//音量
        ttsOptions.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                String.valueOf(AudioManager.STREAM_MUSIC));//播放类型
        mSpeech.setOnUtteranceProgressListener(new TTSPlayListener(listener));
        //调用立刻返回
        int ret = mSpeech.speak(tts, TextToSpeech.QUEUE_FLUSH, ttsOptions);
        DevLog.d("TTsHelper", "ret=" + ret);
    }

    public boolean isPlaying() {
        return mSpeech != null && mSpeech.isSpeaking();
    }

    public void stop() {
        if (mSpeech != null) {
            mSpeech.stop();
        }
    }


    public void release() {
        if (mSpeech != null) {
            mSpeech.shutdown();
        }

    }
}



