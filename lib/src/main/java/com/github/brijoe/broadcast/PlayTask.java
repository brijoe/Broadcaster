package com.github.brijoe.broadcast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * 封装播报数据
 */
class PlayTask {
    private boolean urgency;
    private List<PlayData> dataList;

    private BroadcastPlayListener listener;

    public PlayTask(boolean urgency,
                    @Nullable BroadcastPlayListener listener, @NonNull PlayData... playData) {
        this.urgency = urgency;
        this.listener = listener;
        this.dataList = Arrays.asList(playData);
    }

    public List<PlayData> getDataList() {
        return dataList;
    }

    public void setDataList(List<PlayData> dataList) {
        this.dataList = dataList;
    }

    public BroadcastPlayListener getListener() {
        return listener;
    }

    public void setListener(BroadcastPlayListener listener) {
        this.listener = listener;
    }

    public boolean isUrgency() {
        return urgency;
    }

    public void setUrgency(boolean urgency) {
        this.urgency = urgency;
    }

    @Override
    public String toString() {
        return "PlayTask{" +
                "urgency=" + urgency +
                ", dataList=" + dataList +
                ", listener=" + listener +
                '}';
    }
}
