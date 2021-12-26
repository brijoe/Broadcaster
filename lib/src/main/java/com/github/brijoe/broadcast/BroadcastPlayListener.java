package com.github.brijoe.broadcast;

/**
 * 业务方必须实现此类
 *
 * @author Bridgeliang
 */
public interface BroadcastPlayListener {
    /**
     * 播放任务开始
     */

    void onPlayStart();

    /**
     * 播放任务被打断
     */
    void onPlayInterrupt();

    /**
     * 播放出现错误
     *
     * @param errMsg
     */
    void onPlayError(String errMsg);

    /**
     * 播放成功
     */
    void onPlaySuccess();

}
