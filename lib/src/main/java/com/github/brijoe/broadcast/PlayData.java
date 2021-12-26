package com.github.brijoe.broadcast;

 class PlayData<T> {

    private DataType dataType;

    private T data;

    public PlayData(DataType dataType, T data) {
        this.dataType = dataType;
        this.data = data;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

     @Override
     public String toString() {
         return "PlayData{" +
                 "dataType=" + dataType +
                 ", data=" + data +
                 '}';
     }
 }
