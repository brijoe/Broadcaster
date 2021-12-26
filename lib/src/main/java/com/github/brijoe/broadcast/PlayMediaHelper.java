package com.github.brijoe.broadcast;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

enum PlayMediaHelper {

    INS;

    private MediaPlayer mPlayer = null;

    private Context context;

    private class InternalListener implements MediaPlayer.OnCompletionListener,
            MediaPlayer.OnErrorListener {

        private PlayEngineCallback listener;

        public InternalListener(PlayEngineCallback listener) {
            this.listener = listener;
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            DevLog.d("MediaHelper", "onCompletion");
            if (listener != null) {
                listener.onPlayFinish();
            }
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            DevLog.d("MediaHelper", "onError");
            if (listener != null) {
                listener.onPlayError("MediaPlayer 内部错误");
            }
            return true;
        }
    }

    public void init(Context context) {
        this.context = context;
        mPlayer = new MediaPlayer();
    }

    public void playAssetFile(String assetPath, PlayEngineCallback listener) {
        playInternal(assetPath, true, listener);
    }


    public void playDiskFile(String filePath, PlayEngineCallback listener) {
        playInternal(filePath, false, listener);
    }

    private void playInternal(String filePath, boolean fromAsset, PlayEngineCallback listener) {
        try {
            mPlayer.reset();
            InternalListener internalListener = new InternalListener(listener);
            mPlayer.setOnCompletionListener(internalListener);
            mPlayer.setOnErrorListener(internalListener);
            if (fromAsset) {
                AssetFileDescriptor afd = context.getAssets().openFd(filePath);
                mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
                        afd.getDeclaredLength());
            } else {
                mPlayer.setDataSource(filePath);
            }
            mPlayer.prepare();
            mPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isPlaying() {
        if (mPlayer != null) {
            try {
                return mPlayer.isPlaying();
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }


    public void stop() {
        try {
            if (mPlayer != null) {
                mPlayer.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void release() {
        try {
            if (mPlayer != null) {
                mPlayer.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

