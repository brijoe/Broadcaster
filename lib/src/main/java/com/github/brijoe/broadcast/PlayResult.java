package com.github.brijoe.broadcast;

class PlayResult {

    @PlayStatus
    private int code;
    private String msg;

    public PlayResult(@PlayStatus int code) {
        this.code = code;
    }

    public PlayResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "PlayResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }


}
