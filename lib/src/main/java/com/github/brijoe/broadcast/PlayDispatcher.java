package com.github.brijoe.broadcast;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 播报任务调度器，从队列中取出任务执行，当任务执行完才能继续去取下一个任务
 */
class PlayDispatcher extends Thread {

    private BlockingQueue<PlayTask> tasks = new LinkedBlockingDeque<>();

    private BroadcastService service;

    public PlayDispatcher(BroadcastService service) {
        super("PlayDispatcher");
        this.service = service;
    }

    public void start() {
        super.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                PlayTask playTask = tasks.take();
                DevLog.d("Dispatcher", "取到一个任务，并开始执行 " + playTask);
                //执行并等待完成
                service.playExecutor.play(playTask);
                DevLog.d("Dispatcher", "任务执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlayTask(PlayTask playTask) {
        if (playTask.isUrgency()) {
            service.playExecutor.stopCurPlay();
        }
        tasks.add(playTask);
    }


}
