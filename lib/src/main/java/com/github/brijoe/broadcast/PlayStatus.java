package com.github.brijoe.broadcast;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@IntDef({PlayStatus.PLAY_DEFAULT,
        PlayStatus.PLAY_START,
        PlayStatus.PLAY_ERROR,
        PlayStatus.PLAY_INTERRUPT,
        PlayStatus.PLAY_SUCCESS})
@interface PlayStatus {
    int PLAY_DEFAULT = -1;
    int PLAY_START = 1;
    int PLAY_ERROR = 1 << 1;
    int PLAY_INTERRUPT = 1 << 2;
    int PLAY_SUCCESS = 1 << 3;
}
