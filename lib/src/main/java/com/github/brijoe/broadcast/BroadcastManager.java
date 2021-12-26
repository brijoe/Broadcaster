package com.github.brijoe.broadcast;

import android.content.Context;

public enum BroadcastManager {

    INS;

    private BroadcastService service = new BroadcastService();

    public void init(Context context) {
        service.init(context);
    }

    public void playAssetsFile(boolean isUrgency,
                               String assetPath,
                               BroadcastPlayListener listener) {
        PlayData<String> playData = new PlayData(DataType.DATA_TYPE_ASSERTS_FILE, assetPath);
        service.play(new PlayTask(isUrgency, listener, playData));
    }

    public void playDiskFile(boolean isUrgency,
                             String filePath,
                             BroadcastPlayListener listener) {
        PlayData<String> playData = new PlayData(DataType.DATA_TYPE_FILE, filePath);
        service.play(new PlayTask(isUrgency, listener, playData));

    }

    public void playTTS(boolean isUrgency,
                        String voiceContent,
                        BroadcastPlayListener listener) {
        PlayData<String> playData = new PlayData(DataType.DATA_TYPE_TXT, voiceContent);
        service.play(new PlayTask(isUrgency, listener, playData));

    }

    public void playAssetsFileAndTTS(boolean isUrgency,
                                     String preFilePath,
                                     String voiceContent,
                                     BroadcastPlayListener listener) {
        PlayData<String> playData1 = new PlayData(DataType.DATA_TYPE_ASSERTS_FILE, preFilePath);
        PlayData<String> playData2 = new PlayData(DataType.DATA_TYPE_TXT, voiceContent);
        service.play(new PlayTask(isUrgency, listener, playData1, playData2));

    }

    public void playDiskFileAndTTS(boolean isUrgency, String preFilePath,
                                   String voiceContent,
                                   BroadcastPlayListener listener) {
        PlayData<String> playData1 = new PlayData(DataType.DATA_TYPE_FILE, preFilePath);
        PlayData<String> playData2 = new PlayData(DataType.DATA_TYPE_TXT, voiceContent);
        service.play(new PlayTask(isUrgency, listener, playData1, playData2));
    }

    public void playDiskFileAndDiskFile(boolean isUrgency,
                                        String preFilePath,
                                        String contentFilePath,
                                        BroadcastPlayListener listener) {
        PlayData<String> playData1 = new PlayData(DataType.DATA_TYPE_FILE, preFilePath);
        PlayData<String> playData2 = new PlayData(DataType.DATA_TYPE_FILE, contentFilePath);
        service.play(new PlayTask(isUrgency, listener, playData1, playData2));

    }

    public boolean isBroadCasting() {
        return service.isPlaying();
    }
}
